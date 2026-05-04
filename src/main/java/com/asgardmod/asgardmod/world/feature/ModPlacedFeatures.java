package com.asgardmod.asgardmod.world.feature;

import com.asgardmod.asgardmod.AsgardMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

import java.util.List;

public class ModPlacedFeatures {
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registries.PLACED_FEATURE, AsgardMod.MOD_ID);

    public static final ResourceKey<PlacedFeature> SWORD_STATUE_PLACED =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    new ResourceLocation(AsgardMod.MOD_ID, "sword_statue_placed"));

    public static final ResourceKey<PlacedFeature> JADE_SPIKE_PLACED =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    new ResourceLocation(AsgardMod.MOD_ID, "jade_spike_placed"));

    public static final ResourceKey<PlacedFeature> CONVERGENCE_ARRAY_PLACED =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    new ResourceLocation(AsgardMod.MOD_ID, "convergence_array_placed"));

    public static final ResourceKey<PlacedFeature> SKY_VILLAGE_PLACED =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    new ResourceLocation(AsgardMod.MOD_ID, "sky_village_placed"));

    public static void bootstrap(BootstapContext<PlacedFeature> ctx) {
        var configuredFeatures = ctx.lookup(Registries.CONFIGURED_FEATURE);

        ctx.register(SWORD_STATUE_PLACED, new PlacedFeature(
                configuredFeatures.getOrThrow(ModConfiguredFeatures.SWORD_STATUE_KEY),
                List.of(CountPlacement.of(2), InSquarePlacement.spread(),
                        HeightmapPlacement.onHeightmap(net.minecraft.world.level.levelgen.Heightmap.Types.WORLD_SURFACE_WG),
                        BiomeFilter.biome())));

        ctx.register(JADE_SPIKE_PLACED, new PlacedFeature(
                configuredFeatures.getOrThrow(ModConfiguredFeatures.JADE_SPIKE_KEY),
                List.of(CountPlacement.of(3), InSquarePlacement.spread(),
                        HeightmapPlacement.onHeightmap(net.minecraft.world.level.levelgen.Heightmap.Types.WORLD_SURFACE_WG),
                        BiomeFilter.biome())));

        ctx.register(CONVERGENCE_ARRAY_PLACED, new PlacedFeature(
                configuredFeatures.getOrThrow(ModConfiguredFeatures.CONVERGENCE_ARRAY_KEY),
                List.of(RarityFilter.onAverageOnceEvery(400), InSquarePlacement.spread(),
                        HeightmapPlacement.onHeightmap(net.minecraft.world.level.levelgen.Heightmap.Types.WORLD_SURFACE_WG),
                        BiomeFilter.biome())));

        // Sky villages: rare, float high in the sky (Y 140-200)
        ctx.register(SKY_VILLAGE_PLACED, new PlacedFeature(
                configuredFeatures.getOrThrow(ModConfiguredFeatures.SKY_VILLAGE_KEY),
                List.of(
                        RarityFilter.onAverageOnceEvery(180),
                        InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(
                                net.minecraft.world.level.levelgen.VerticalAnchor.absolute(140),
                                net.minecraft.world.level.levelgen.VerticalAnchor.absolute(200)),
                        BiomeFilter.biome())));
    }

    public static void register(IEventBus eventBus) {
        PLACED_FEATURES.register(eventBus);
    }
}
