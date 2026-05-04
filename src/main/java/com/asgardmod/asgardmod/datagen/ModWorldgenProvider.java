package com.asgardmod.asgardmod.datagen;

import com.asgardmod.asgardmod.block.ModBlocks;
import com.asgardmod.asgardmod.dimension.ModDimensions;
import com.asgardmod.asgardmod.world.ModBiomeData;
import com.asgardmod.asgardmod.world.ModBiomes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModWorldgenProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
        .add(Registries.BIOME,          ModWorldgenProvider::bootstrapBiomes)
        .add(Registries.NOISE_SETTINGS, ModWorldgenProvider::bootstrapNoise)
        .add(Registries.LEVEL_STEM,     ModDimensions::bootstrapStem);

    public ModWorldgenProvider(PackOutput output,
                               CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider, BUILDER, Set.of("asgardmod"));
    }

    // ── Biome bootstrap ──────────────────────────────────────────────────

    private static void bootstrapBiomes(BootstapContext<Biome> ctx) {
        // GOD'S DOMAIN
        ctx.register(ModBiomes.CELESTIAL_MEADOW,      ModBiomeData.celestialMeadow(ctx));
        ctx.register(ModBiomes.CLOUD_PLATEAU,         ModBiomeData.cloudPlateau(ctx));
        ctx.register(ModBiomes.DIVINE_CRYSTAL_FOREST, ModBiomeData.divineCrystalForest(ctx));
        ctx.register(ModBiomes.SACRED_HILLS,          ModBiomeData.sacredHills(ctx));
        // ASGARD
        ctx.register(ModBiomes.JADE_PEAKS,            ModBiomeData.jadePeaks(ctx));
        ctx.register(ModBiomes.GOLDEN_FOREST,         ModBiomeData.goldenForest(ctx));
        ctx.register(ModBiomes.ASGARD_OCEAN,          ModBiomeData.asgardOcean(ctx));
        ctx.register(ModBiomes.ASGARD_PLAINS,         ModBiomeData.asgardPlains(ctx));
        ctx.register(ModBiomes.SWORD_PLAINS,          ModBiomeData.swordPlains(ctx));
        ctx.register(ModBiomes.JADE_RIVER_KARST,      ModBiomeData.jadeRiverKarst(ctx));
        ctx.register(ModBiomes.HEAVEN_PILLAR_FOREST,  ModBiomeData.heavenPillarForest(ctx));
        ctx.register(ModBiomes.SKY_PIERCER,           ModBiomeData.skyPiercer(ctx));
    }

    // ── Noise bootstrap ──────────────────────────────────────────────────

    private static NoiseRouter flatRouter() {
        DensityFunction zero = DensityFunctions.zero();
        return new NoiseRouter(
            zero, zero, zero, zero, zero, zero, zero, zero,
            zero, zero, zero, zero, zero, zero, zero
        );
    }

    private static void bootstrapNoise(BootstapContext<NoiseGeneratorSettings> ctx) {

        NoiseRouter router = flatRouter();

        // GOD'S DOMAIN surface rules
        SurfaceRules.RuleSource godsDomainSurface = SurfaceRules.sequence(
            SurfaceRules.ifTrue(
                SurfaceRules.abovePreliminarySurface(),
                SurfaceRules.sequence(
                    SurfaceRules.ifTrue(
                        SurfaceRules.ON_FLOOR,
                        SurfaceRules.state(ModBlocks.DIVINE_GRASS.get().defaultBlockState())
                    ),
                    SurfaceRules.ifTrue(
                        SurfaceRules.UNDER_FLOOR,
                        SurfaceRules.state(ModBlocks.DIVINE_STONE.get().defaultBlockState())
                    )
                )
            ),
            SurfaceRules.state(ModBlocks.DIVINE_STONE.get().defaultBlockState())
        );

        ctx.register(ModDimensions.GODS_DOMAIN_NOISE, new NoiseGeneratorSettings(
            new NoiseSettings(0, 256, 1, 2),
            ModBlocks.DIVINE_STONE.get().defaultBlockState(),
            Blocks.WATER.defaultBlockState(),
            router,
            godsDomainSurface,
            List.of(),
            63,
            true,
            false,
            false,
            false
        ));

        // ASGARD surface rules
        SurfaceRules.RuleSource asgardSurface = SurfaceRules.sequence(
            SurfaceRules.ifTrue(
                SurfaceRules.abovePreliminarySurface(),
                SurfaceRules.sequence(
                    SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(ModBiomes.JADE_PEAKS, ModBiomes.SKY_PIERCER),
                        SurfaceRules.ifTrue(
                            SurfaceRules.ON_FLOOR,
                            SurfaceRules.state(ModBlocks.JADE_BLOCK.get().defaultBlockState())
                        )
                    ),
                    SurfaceRules.ifTrue(
                        SurfaceRules.ON_FLOOR,
                        SurfaceRules.state(ModBlocks.ASGARD_GRASS.get().defaultBlockState())
                    ),
                    SurfaceRules.ifTrue(
                        SurfaceRules.UNDER_FLOOR,
                        SurfaceRules.state(ModBlocks.ASGARD_DIRT.get().defaultBlockState())
                    )
                )
            ),
            SurfaceRules.state(ModBlocks.JADE_STONE.get().defaultBlockState())
        );

        ctx.register(ModDimensions.ASGARD_NOISE, new NoiseGeneratorSettings(
            new NoiseSettings(-64, 384, 1, 2),
            ModBlocks.JADE_STONE.get().defaultBlockState(),
            Blocks.WATER.defaultBlockState(),
            router,
            asgardSurface,
            List.of(),
            40,
            false,
            true,
            true,
            false
        ));
    }
}
