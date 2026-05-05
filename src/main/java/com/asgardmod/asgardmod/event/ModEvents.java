package com.asgardmod.asgardmod.event;

import com.asgardmod.asgardmod.AsgardMod;
import com.asgardmod.asgardmod.block.GodsPortalShape;
import com.asgardmod.asgardmod.block.ModBlocks;
import com.asgardmod.asgardmod.dimension.ModDimensions;
import com.asgardmod.asgardmod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.PlayerEvent;

@Mod.EventBusSubscriber(modid = AsgardMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {

    /**
     * Right-clicking a CELESTIAL_MARBLE block with the GODS_DOMAIN_KEY
     * validates the 4x5 frame and fills the interior with portal blocks.
     * The key is NOT consumed.
     */
    @SubscribeEvent
    public static void onPlayerRightClick(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level   = event.getLevel();
        BlockPos pos  = event.getPos();
        InteractionHand hand = event.getHand();

        if (level.isClientSide) return;
        if (hand != InteractionHand.MAIN_HAND) return;

        ItemStack held = player.getItemInHand(hand);
        boolean holdingKey = held.getItem() == ModItems.GODS_DOMAIN_KEY.get();

        // ── Ignite Gods Domain portal ──────────────────────────────────────
        if (holdingKey && level.getBlockState(pos).getBlock() == ModBlocks.CELESTIAL_MARBLE.get()) {
            GodsPortalShape shape = GodsPortalShape.findAnyValidShape(level, pos);
            if (shape != null && shape.isValid()) {
                shape.createPortalBlocks();
                level.playSound(null, pos, SoundEvents.RESPAWN_ANCHOR_SET_SPAWN,
                    SoundSource.BLOCKS, 1.0f, 1.2f);
                player.displayClientMessage(
                    Component.literal("§6§lThe portal to GOD's Domain awakens!"), true);
                event.setCanceled(true);
            } else {
                player.displayClientMessage(
                    Component.literal("§cThe frame is incomplete. Build a 4x5 Celestial Marble frame."), true);
                event.setCanceled(true);
            }
        }

        // ── Asgard portal (unchanged right-click logic) ────────────────────
        if (level.getBlockState(pos).getBlock() == ModBlocks.ASGARD_PORTAL.get()) {
            if (!(player instanceof ServerPlayer serverPlayer)) return;
            if (level.dimension() == ModDimensions.ASGARD) {
                travelToLevel(serverPlayer, Level.OVERWORLD, pos);
                player.displayClientMessage(Component.literal("§6Descending from Asgard..."), true);
            } else {
                travelToLevel(serverPlayer, ModDimensions.ASGARD, pos);
                player.displayClientMessage(Component.literal("§6Ascending to Asgard..."), true);
            }
            event.setCanceled(true);
        }
    }

    private static void travelToLevel(ServerPlayer player, ResourceKey<Level> dimension, BlockPos origin) {
        net.minecraft.server.MinecraftServer server = player.getServer();
        if (server == null) return;
        ServerLevel target = server.getLevel(dimension);
        if (target == null) return;
        player.teleportTo(target,
            origin.getX() + 0.5, origin.getY() + 1.0, origin.getZ() + 0.5,
            player.getYRot(), player.getXRot());
    }
    
    /**
     * Give portal-crafting starter materials to a player the very first time they join.
     * Uses persistent NBT tag "asgardmod_received_starter" to ensure one-time delivery.
     */
        @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;

        CompoundTag persistentData = player.getPersistentData();
        String TAG = "asgardmod_received_starter";
        if (persistentData.getBoolean(TAG)) return;
        persistentData.putBoolean(TAG, true);

        // --- Asgard Portal materials ---
        // Recipe: 4x netherite_ingot, 2x emerald, 1x amethyst_block -> 4 portals
        player.getInventory().add(new ItemStack(Items.NETHERITE_INGOT, 4));
        player.getInventory().add(new ItemStack(Items.EMERALD, 2));
        player.getInventory().add(new ItemStack(Items.AMETHYST_BLOCK, 1));

        // --- God's Domain Portal materials ---
        // Recipe: 4x diamond, 2x gold_ingot, 1x amethyst_shard -> 4 portals
        player.getInventory().add(new ItemStack(Items.DIAMOND, 4));
        player.getInventory().add(new ItemStack(Items.GOLD_INGOT, 2));
        player.getInventory().add(new ItemStack(Items.AMETHYST_SHARD, 1));

        player.displayClientMessage(
            Component.literal("§6[Asgard Mod] §fYou received portal-crafting starter materials!"), true);
    }
}
