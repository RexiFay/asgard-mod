package com.asgardmod.asgardmod.world.feature;

import com.asgardmod.asgardmod.AsgardMod;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(ForgeRegistries.FEATURES, AsgardMod.MOD_ID);

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SWORD_STATUE =
            FEATURES.register("sword_statue", () -> new SwordStatueFeature(NoneFeatureConfiguration.CODEC));

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> JADE_SPIKE =
            FEATURES.register("jade_spike", () -> new JadeSpikeFeature(NoneFeatureConfiguration.CODEC));

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> CONVERGENCE_ARRAY =
            FEATURES.register("convergence_array", () -> new ConvergenceArrayFeature(NoneFeatureConfiguration.CODEC));

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SKY_VILLAGE =
            FEATURES.register("sky_village", () -> new SkyVillageFeature(NoneFeatureConfiguration.CODEC));

    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }
}
