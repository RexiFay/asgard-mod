package com.asgardmod.asgardmod.block;

import com.asgardmod.asgardmod.dimension.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GodsPortalBlock extends Block {

    private static final int TELEPORT_DELAY = 80;
    private static final String TAG_PORTAL_TICKS = "GodsPortalTicks";

    public GodsPortalBlock(BlockBehaviour.Properties props) {
        super(props);
    }

    // ── No collision ──────────────────────────────────────────────────────

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level,
                                        BlockPos pos, CollisionContext ctx) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level,
                               BlockPos pos, CollisionContext ctx) {
        return Block.box(0, 0, 0, 16, 16, 16);
    }

    // ── Walk-through teleport ─────────────────────────────────────────────

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!(level instanceof ServerLevel serverLevel)) return;
        if (!(entity instanceof ServerPlayer player)) return;
        if (entity.isPassenger() || entity.isVehicle()) return;

        // isOnPortalCooldown() / getPortalCooldown() are both accessible in 1.20.1
        if (player.isOnPortalCooldown()) return;

        // Track our own per-player tick counter in persistent data
        CompoundTag data = player.getPersistentData();
        int ticks = data.getInt(TAG_PORTAL_TICKS) + 1;
        data.putInt(TAG_PORTAL_TICKS, ticks);

        if (ticks < TELEPORT_DELAY) return;

        // Reset counter and apply portal cooldown so they can't instantly re-enter
        data.putInt(TAG_PORTAL_TICKS, 0);
        player.setPortalCooldown();   // sets cooldown to 300 ticks (1.20.1 correct name)

        ResourceKey<Level> currentDim = level.dimension();
        ResourceKey<Level> target;

        if (currentDim == ModDimensions.GODS_DOMAIN) {
            target = Level.OVERWORLD;
            player.displayClientMessage(
                net.minecraft.network.chat.Component.literal("\u00a7bReturning to the mortal realm..."), true);
        } else {
            target = ModDimensions.GODS_DOMAIN;
            player.displayClientMessage(
                net.minecraft.network.chat.Component.literal("\u00a76\u00a7lEntering GOD's Domain..."), true);
        }

        ServerLevel targetLevel = player.getServer().getLevel(target);
        if (targetLevel == null) return;

        double scale = getCoordScale(serverLevel, targetLevel);
        BlockPos destSearch = new BlockPos(
            (int)(pos.getX() * scale), pos.getY(), (int)(pos.getZ() * scale));
        BlockPos dest = findNearestPortal(targetLevel, destSearch, 128);
        if (dest == null) dest = destSearch;

        player.teleportTo(targetLevel,
            dest.getX() + 0.5, dest.getY() + 1.0, dest.getZ() + 0.5,
            player.getYRot(), player.getXRot());
    }

    // Reset counter when player leaves the portal block
    @Override
    public void updateEntityAfterFallOn(net.minecraft.world.level.BlockGetter level, Entity entity) {
        super.updateEntityAfterFallOn(level, entity);
    }

    // Clear tick counter if entity is no longer inside (checked each tick via level tick,
    // but we also clear it when the cooldown fires so it stays clean)

    // ── Ambient particles (gold + blue) ───────────────────────────────────

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource rand) {
        if (rand.nextInt(100) == 0) {
            level.playLocalSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS,
                0.5f, rand.nextFloat() * 0.4f + 0.8f, false);
        }
        for (int i = 0; i < 4; i++) {
            double x = pos.getX() + rand.nextDouble();
            double y = pos.getY() + rand.nextDouble();
            double z = pos.getZ() + rand.nextDouble();
            if (rand.nextBoolean()) {
                level.addParticle(net.minecraft.core.particles.ParticleTypes.PORTAL,
                    x, y, z,
                    (rand.nextDouble() - 0.5) * 0.5,
                    (rand.nextDouble() - 0.5) * 0.5,
                    (rand.nextDouble() - 0.5) * 0.5);
            } else {
                level.addParticle(net.minecraft.core.particles.ParticleTypes.DRIPPING_LAVA,
                    x, y, z, 0, -0.05, 0);
            }
        }
    }

    // ── Frame-break detection ─────────────────────────────────────────────

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos,
                                Block block, BlockPos fromPos, boolean isMoving) {
        if (!level.isClientSide) {
            boolean hasSupport = false;
            for (Direction dir : Direction.values()) {
                BlockState neighbor = level.getBlockState(pos.relative(dir));
                if (neighbor.getBlock() == ModBlocks.CELESTIAL_MARBLE.get()
                    || neighbor.getBlock() == ModBlocks.GODS_DOMAIN_PORTAL.get()) {
                    hasSupport = true;
                    break;
                }
            }
            if (!hasSupport) {
                level.destroyBlock(pos, false);
            }
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────

    private static double getCoordScale(ServerLevel from, ServerLevel to) {
        return from.dimensionType().coordinateScale() / to.dimensionType().coordinateScale();
    }

    private static BlockPos findNearestPortal(ServerLevel level, BlockPos origin, int radius) {
        int bestDist = Integer.MAX_VALUE;
        BlockPos best = null;
        for (BlockPos.MutableBlockPos p : BlockPos.spiralAround(origin, radius,
                Direction.EAST, Direction.SOUTH)) {
            if (level.getBlockState(p).getBlock() == ModBlocks.GODS_DOMAIN_PORTAL.get()) {
                int dist = (int) p.distSqr(origin);
                if (dist < bestDist) {
                    bestDist = dist;
                    best = p.immutable();
                }
            }
        }
        return best;
    }
}
