package com.asgardmod.asgardmod.world.feature;

import com.asgardmod.asgardmod.AsgardMod;
import com.asgardmod.asgardmod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

public class ModConfiguredFeatures {
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES =
            DeferredRegister.create(Registries.CONFIGURED_FEATURE, AsgardMod.MOD_ID);

    public static final ResourceKey<ConfiguredFeature<?, ?>> SWORD_STATUE_KEY =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    new ResourceLocation(AsgardMod.MOD_ID, "sword_statue"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> JADE_SPIKE_KEY =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    new ResourceLocation(AsgardMod.MOD_ID, "jade_spike"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONVERGENCE_ARRAY_KEY =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    new ResourceLocation(AsgardMod.MOD_ID, "convergence_array"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> SKY_VILLAGE_KEY =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    new ResourceLocation(AsgardMod.MOD_ID, "sky_village"));

    // --- Custom Trees ---
    public static final ResourceKey<ConfiguredFeature<?, ?>> DIVINE_TREE_KEY =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    new ResourceLocation(AsgardMod.MOD_ID, "divine_tree"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLD_TREE_KEY =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    new ResourceLocation(AsgardMod.MOD_ID, "gold_tree"));

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> ctx) {
        ctx.register(SWORD_STATUE_KEY,
                new ConfiguredFeature<>(ModFeatures.SWORD_STATUE.get(), NoneFeatureConfiguration.INSTANCE));
        ctx.register(JADE_SPIKE_KEY,
                new ConfiguredFeature<>(ModFeatures.JADE_SPIKE.get(), NoneFeatureConfiguration.INSTANCE));
        ctx.register(CONVERGENCE_ARRAY_KEY,
                new ConfiguredFeature<>(ModFeatures.CONVERGENCE_ARRAY.get(), NoneFeatureConfiguration.INSTANCE));
        ctx.register(SKY_VILLAGE_KEY,
                new ConfiguredFeature<>(ModFeatures.SKY_VILLAGE.get(), NoneFeatureConfiguration.INSTANCE));

        // Divine tree: uses divine_wood_log trunk + divine_leaves canopy
        ctx.register(DIVINE_TREE_KEY, new ConfiguredFeature<>(Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(ModBlocks.DIVINE_WOOD_LOG.get()),
                        new StraightTrunkPlacer(5, 2, 0),
                        BlockStateProvider.simple(ModBlocks.DIVINE_LEAVES.get()),
                        new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1)
                ).build()));

        // Gold tree: uses gold_log trunk + gold_leaves canopy, branching style
        ctx.register(GOLD_TREE_KEY, new ConfiguredFeature<>(Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(ModBlocks.GOLD_LOG.get()),
                        new ForkingTrunkPlacer(5, 2),
                        BlockStateProvider.simple(ModBlocks.GOLD_LEAVES.get()),
                        new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(1, 0, 2)
                ).build()));
    }

    public static void register(IEventBus eventBus) {
        CONFIGURED_FEATURES.register(eventBus);
    }
}
