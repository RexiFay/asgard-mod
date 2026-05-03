package com.asgardmod.asgardmod.event;

import com.asgardmod.asgardmod.AsgardMod;
import com.asgardmod.asgardmod.block.ModBlocks;
import com.asgardmod.asgardmod.dimension.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AsgardMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {

    @SubscribeEvent
    public static void onPlayerRightClick(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        BlockPos pos = event.getPos();

        if (!(player instanceof ServerPlayer serverPlayer)) return;

        var block = level.getBlockState(pos).getBlock();

        if (block == ModBlocks.GODS_DOMAIN_PORTAL.get()) {
            if (level.dimension() == ModDimensions.GODS_DOMAIN) {
                travelToLevel(serverPlayer, Level.OVERWORLD, pos);
                player.displayClientMessage(Component.literal("§bReturning to the mortal realm..."), true);
            } else {
                travelToLevel(serverPlayer, ModDimensions.GODS_DOMAIN, pos);
                player.displayClientMessage(Component.literal("§bEntering GOD's Domain..."), true);
            }
            event.setCanceled(true);
        }

        if (block == ModBlocks.ASGARD_PORTAL.get()) {
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
        player.teleportTo(target, origin.getX() + 0.5, origin.getY() + 1.0, origin.getZ() + 0.5,
                player.getYRot(), player.getXRot());
    }
}
