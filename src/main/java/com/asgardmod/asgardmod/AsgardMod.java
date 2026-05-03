package com.asgardmod.asgardmod;

import com.asgardmod.asgardmod.block.ModBlocks;
import com.asgardmod.asgardmod.item.ModItems;
import com.asgardmod.asgardmod.world.ModBiomes;
import com.asgardmod.asgardmod.world.feature.ModConfiguredFeatures;
import com.asgardmod.asgardmod.world.feature.ModFeatures;
import com.asgardmod.asgardmod.world.feature.ModPlacedFeatures;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(AsgardMod.MOD_ID)
public class AsgardMod {
    public static final String MOD_ID = "asgardmod";
    public static final Logger LOGGER = LogManager.getLogger();

    public AsgardMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModBiomes.register(modEventBus);
        ModFeatures.register(modEventBus);
        ModConfiguredFeatures.register(modEventBus);
        ModPlacedFeatures.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }
}
