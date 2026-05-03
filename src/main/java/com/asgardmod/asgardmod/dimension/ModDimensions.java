package com.asgardmod.asgardmod.dimension;

import com.asgardmod.asgardmod.AsgardMod;
import com.asgardmod.asgardmod.world.ModBiomes;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public class ModDimensions {

    // ── Level Stem keys ────────────────────────────────────────────────────
    public static final ResourceKey<LevelStem> GODS_DOMAIN_KEY =
        ResourceKey.create(Registries.LEVEL_STEM,
            new ResourceLocation(AsgardMod.MOD_ID, "gods_domain"));

    public static final ResourceKey<LevelStem> ASGARD_KEY =
        ResourceKey.create(Registries.LEVEL_STEM,
            new ResourceLocation(AsgardMod.MOD_ID, "asgard"));

    // ── Dimension Type keys ────────────────────────────────────────────────
    public static final ResourceKey<DimensionType> GODS_DOMAIN_TYPE =
        ResourceKey.create(Registries.DIMENSION_TYPE,
            new ResourceLocation(AsgardMod.MOD_ID, "gods_domain"));

    public static final ResourceKey<DimensionType> ASGARD_TYPE =
        ResourceKey.create(Registries.DIMENSION_TYPE,
            new ResourceLocation(AsgardMod.MOD_ID, "asgard"));

    // ── Noise Settings keys ────────────────────────────────────────────────
    public static final ResourceKey<NoiseGeneratorSettings> GODS_DOMAIN_NOISE =
        ResourceKey.create(Registries.NOISE_SETTINGS,
            new ResourceLocation(AsgardMod.MOD_ID, "gods_domain"));

    public static final ResourceKey<NoiseGeneratorSettings> ASGARD_NOISE =
        ResourceKey.create(Registries.NOISE_SETTINGS,
            new ResourceLocation(AsgardMod.MOD_ID, "asgard"));

    // ── Bootstrap: called by ModWorldgenProvider to register LevelStems ────
    public static void bootstrapStem(BootstapContext<LevelStem> ctx) {
        HolderGetter<Biome> biomes = ctx.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimTypes = ctx.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseSettings = ctx.lookup(Registries.NOISE_SETTINGS);

        // ── GOD's Domain ───────────────────────────────────────────────────
        ctx.register(GODS_DOMAIN_KEY, new LevelStem(
            dimTypes.getOrThrow(GODS_DOMAIN_TYPE),
            new NoiseBasedChunkGenerator(
                MultiNoiseBiomeSource.createFromList(
                    new MultiNoiseBiomeSourceParameterList<>(ModBiomes.godsDomainPairs(biomes))
                ),
                noiseSettings.getOrThrow(GODS_DOMAIN_NOISE)
            )
        ));

        // ── Asgard ─────────────────────────────────────────────────────────
        ctx.register(ASGARD_KEY, new LevelStem(
            dimTypes.getOrThrow(ASGARD_TYPE),
            new NoiseBasedChunkGenerator(
                MultiNoiseBiomeSource.createFromList(
                    new MultiNoiseBiomeSourceParameterList<>(ModBiomes.asgardPairs(biomes))
                ),
                noiseSettings.getOrThrow(ASGARD_NOISE)
            )
        ));
    }
}
