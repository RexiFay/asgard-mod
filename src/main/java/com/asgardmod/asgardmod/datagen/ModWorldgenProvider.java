package com.asgardmod.asgardmod.datagen;

import com.asgardmod.asgardmod.block.ModBlocks;
import com.asgardmod.asgardmod.dimension.ModDimensions;
import com.asgardmod.asgardmod.world.ModBiomes;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModWorldgenProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
        .add(Registries.NOISE_SETTINGS, ModWorldgenProvider::bootstrapNoise)
        .add(Registries.LEVEL_STEM,     ModDimensions::bootstrapStem);

    public ModWorldgenProvider(PackOutput output,
                               CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider, BUILDER, Set.of("asgardmod"));
    }

    private static void bootstrapNoise(BootstapContext<NoiseGeneratorSettings> ctx) {

        // Borrow the noise router from vanilla overworld settings.
        // NoiseRouterData.overworld() is protected; the safe public way
        // is to look up the already-registered overworld NoiseGeneratorSettings
        // and reuse its NoiseRouter directly.
        HolderGetter<NoiseGeneratorSettings> noiseSettingsGetter =
            ctx.lookup(Registries.NOISE_SETTINGS);
        NoiseRouter borrowedRouter = noiseSettingsGetter
            .getOrThrow(NoiseGeneratorSettings.OVERWORLD)
            .value()
            .noiseRouter();

        // ── GOD'S DOMAIN surface rules ───────────────────────────────────
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
            borrowedRouter,
            godsDomainSurface,
            List.of(),   // spawnTarget
            63,          // seaLevel
            true,        // disableMobGeneration — peaceful
            false,       // aquifersEnabled
            false,       // oreVeinsEnabled
            false        // legacyRandomSource
        ));

        // ── ASGARD surface rules ────────────────────────────────────────
        SurfaceRules.RuleSource asgardSurface = SurfaceRules.sequence(
            SurfaceRules.ifTrue(
                SurfaceRules.abovePreliminarySurface(),
                SurfaceRules.sequence(
                    // Jade Peaks + Sky Piercer: jade block on surface
                    SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(ModBiomes.JADE_PEAKS, ModBiomes.SKY_PIERCER),
                        SurfaceRules.ifTrue(
                            SurfaceRules.ON_FLOOR,
                            SurfaceRules.state(ModBlocks.JADE_BLOCK.get().defaultBlockState())
                        )
                    ),
                    // All other biomes: asgard grass on top
                    SurfaceRules.ifTrue(
                        SurfaceRules.ON_FLOOR,
                        SurfaceRules.state(ModBlocks.ASGARD_GRASS.get().defaultBlockState())
                    ),
                    // Sub-surface: asgard dirt
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
            borrowedRouter,
            asgardSurface,
            List.of(),
            40,    // seaLevel — deep oceans
            false, // disableMobGeneration
            true,  // aquifersEnabled
            true,  // oreVeinsEnabled
            false  // legacyRandomSource
        ));
    }
}
