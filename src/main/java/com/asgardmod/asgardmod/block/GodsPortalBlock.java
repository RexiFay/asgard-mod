package com.asgardmod.asgardmod.block;

import com.asgardmod.asgardmod.dimension.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GodsPortalBlock extends Block {

    /** Ticks an entity must stand inside before teleporting (nether = 80, we use 80) */
    private static final int TELEPORT_DELAY = 80;

    public GodsPortalBlock(BlockBehaviour.Properties props) {
        super(props);
    }

    // ── No collision — entities walk through ─────────────────────────────

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level,
                                        BlockPos pos, CollisionContext ctx) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level,
                               BlockPos pos, CollisionContext ctx) {
        // Thin visual slab (like nether portal)
        return Block.box(0, 0, 0, 16, 16, 16);
    }

    // ── Teleport on entity contact ────────────────────────────────────────

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!(level instanceof ServerLevel serverLevel)) return;
        if (!(entity instanceof ServerPlayer player)) return;
        if (entity.isPassenger() || entity.isVehicle()) return;

        // Use vanilla portal cooldown mechanism
        if (player.isOnPortalCooldown()) return;

        player.setInPortal();

        if (player.portalTime < TELEPORT_DELAY) return;

        ResourceKey<Level> currentDim = level.dimension();
        ResourceKey<Level> target;

        if (currentDim == ModDimensions.GODS_DOMAIN) {
            target = Level.OVERWORLD;
            player.displayClientMessage(
                net.minecraft.network.chat.Component.literal("§bReturning to the mortal realm..."), true);
        } else {
            target = ModDimensions.GODS_DOMAIN;
            player.displayClientMessage(
                net.minecraft.network.chat.Component.literal("§6§lEntering GOD's Domain..."), true);
        }

        ServerLevel targetLevel = player.getServer().getLevel(target);
        if (targetLevel == null) return;

        // Try to find a portal in the target dim near scaled coords
        double scale = getCoordScale(serverLevel, targetLevel);
        BlockPos destSearch = new BlockPos(
            (int)(pos.getX() * scale), pos.getY(), (int)(pos.getZ() * scale));
        BlockPos dest = findNearestPortal(targetLevel, destSearch, 128);

        if (dest == null) dest = destSearch; // fallback to scaled pos

        player.resetPortalCooldown();
        player.teleportTo(targetLevel,
            dest.getX() + 0.5, dest.getY() + 1.0, dest.getZ() + 0.5,
            player.getYRot(), player.getXRot());
    }

    // ── Ambient particles (gold + blue swirl) ─────────────────────────────

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
            // Alternate gold and blue portal particles
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

    // ── Breaking the frame destroys portal blocks ─────────────────────────

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos,
                                Block block, BlockPos fromPos, boolean isMoving) {
        if (!level.isClientSide) {
            // If the frame was broken, destroy this portal block
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
        double fromScale = from.dimensionType().coordinateScale();
        double toScale   = to.dimensionType().coordinateScale();
        return fromScale / toScale;
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
