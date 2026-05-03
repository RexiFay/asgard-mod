package com.asgardmod.asgardmod.world;

import com.asgardmod.asgardmod.AsgardMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBiomes {
    public static final DeferredRegister<Biome> BIOMES =
            DeferredRegister.create(ForgeRegistries.BIOMES, AsgardMod.MOD_ID);

    // GOD'S DOMAIN BIOMES
    public static final RegistryObject<Biome> CELESTIAL_MEADOW =
            BIOMES.register("celestial_meadow", ModBiomeData::celestialMeadow);
    public static final RegistryObject<Biome> CLOUD_PLATEAU =
            BIOMES.register("cloud_plateau", ModBiomeData::cloudPlateau);
    public static final RegistryObject<Biome> DIVINE_CRYSTAL_FOREST =
            BIOMES.register("divine_crystal_forest", ModBiomeData::divineCrystalForest);
    public static final RegistryObject<Biome> SACRED_HILLS =
            BIOMES.register("sacred_hills", ModBiomeData::sacredHills);

    // ASGARD BIOMES
    public static final RegistryObject<Biome> JADE_PEAKS =
            BIOMES.register("jade_peaks", ModBiomeData::jadePeaks);
    public static final RegistryObject<Biome> GOLDEN_FOREST =
            BIOMES.register("golden_forest", ModBiomeData::goldenForest);
    public static final RegistryObject<Biome> ASGARD_OCEAN =
            BIOMES.register("asgard_ocean", ModBiomeData::asgardOcean);
    public static final RegistryObject<Biome> ASGARD_PLAINS =
            BIOMES.register("asgard_plains", ModBiomeData::asgardPlains);
    public static final RegistryObject<Biome> SWORD_PLAINS =
            BIOMES.register("sword_plains", ModBiomeData::swordPlains);
    public static final RegistryObject<Biome> JADE_RIVER_KARST =
            BIOMES.register("jade_river_karst", ModBiomeData::jadeRiverKarst);
    public static final RegistryObject<Biome> HEAVEN_PILLAR_FOREST =
            BIOMES.register("heaven_pillar_forest", ModBiomeData::heavenPillarForest);
    public static final RegistryObject<Biome> SKY_PIERCER =
            BIOMES.register("sky_piercer", ModBiomeData::skyPiercer);

    public static void register(IEventBus eventBus) {
        BIOMES.register(eventBus);
    }

    public static ResourceKey<Biome> key(String name) {
        return ResourceKey.create(Registries.BIOME, new ResourceLocation(AsgardMod.MOD_ID, name));
    }
}
