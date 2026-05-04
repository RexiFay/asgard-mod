package com.asgardmod.asgardmod.world;

import com.asgardmod.asgardmod.world.feature.ModPlacedFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.AquaticPlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModBiomeData {

    // ── Mob helpers ───────────────────────────────────────────────────────

    private static MobSpawnSettings noMobSpawns() {
        return MobSpawnSettings.EMPTY;
    }

    private static MobSpawnSettings livelyMobs() {
        MobSpawnSettings.Builder b = new MobSpawnSettings.Builder();
        b.addSpawn(MobCategory.CREATURE,  new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 8, 2, 5));
        b.addSpawn(MobCategory.CREATURE,  new MobSpawnSettings.SpawnerData(EntityType.RABBIT,  6, 2, 4));
        b.addSpawn(MobCategory.CREATURE,  new MobSpawnSettings.SpawnerData(EntityType.PARROT,  3, 1, 2));
        b.addSpawn(MobCategory.CREATURE,  new MobSpawnSettings.SpawnerData(EntityType.ALLAY,   4, 1, 3));
        return b.build();
    }

    private static MobSpawnSettings skyMobs() {
        MobSpawnSettings.Builder b = new MobSpawnSettings.Builder();
        b.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.ALLAY,  6, 1, 4));
        b.addSpawn(MobCategory.AMBIENT,  new MobSpawnSettings.SpawnerData(EntityType.BAT,    4, 2, 4));
        b.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PARROT, 4, 1, 2));
        return b.build();
    }

    private static MobSpawnSettings forestMobs() {
        MobSpawnSettings.Builder b = new MobSpawnSettings.Builder();
        b.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX,     4, 1, 2));
        b.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT,  5, 2, 4));
        b.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PARROT,  3, 1, 2));
        b.addSpawn(MobCategory.AMBIENT,  new MobSpawnSettings.SpawnerData(EntityType.BAT,     4, 1, 3));
        return b.build();
    }

    private static MobSpawnSettings riverMobs() {
        MobSpawnSettings.Builder b = new MobSpawnSettings.Builder();
        b.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.SALMON,   5, 1, 4));
        b.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.TROPICAL_FISH, 3, 1, 3));
        b.addSpawn(MobCategory.CREATURE,      new MobSpawnSettings.SpawnerData(EntityType.FROG,     4, 1, 3));
        return b.build();
    }

    private static MobSpawnSettings plainsMobs() {
        MobSpawnSettings.Builder b = new MobSpawnSettings.Builder();
        b.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.HORSE,  3, 1, 3));
        b.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.DONKEY, 2, 1, 2));
        b.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SHEEP,  6, 2, 4));
        b.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.COW,    5, 2, 4));
        b.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PIG,    4, 1, 4));
        return b.build();
    }

    // ── Generation helper: adds vanilla vegetation from a HolderGetter ───

    /**
     * Shared base generation: underground ores + springs so caves aren't completely bare.
     * Individual biomes then add surface vegetation on top of this.
     */
    private static void addBaseUndergroundFeatures(BiomeGenerationSettings.Builder gen,
                                                    HolderGetter<PlacedFeature> pf) {
        gen.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES,
                pf.getOrThrow(net.minecraft.data.worldgen.placement.OrePlacements.ORE_DIRT));
        gen.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES,
                pf.getOrThrow(net.minecraft.data.worldgen.placement.OrePlacements.ORE_GRAVEL));
    }

    // ── GOD'S DOMAIN BIOMES ───────────────────────────────────────────────

    public static Biome celestialMeadow(BootstapContext<net.minecraft.world.level.biome.Biome> ctx) {
        HolderGetter<PlacedFeature> pf = ctx.lookup(Registries.PLACED_FEATURE);
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(pf,
                ctx.lookup(Registries.CONFIGURED_CARVER));
        addBaseUndergroundFeatures(gen, pf);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.PATCH_GRASS_JUNGLE));
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.FLOWER_DEFAULT));
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.FLOWER_MEADOW));
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.PATCH_TALL_GRASS_2));
        gen.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES,
                pf.getOrThrow(ModPlacedFeatures.SKY_VILLAGE_PLACED));
        gen.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES,
                pf.getOrThrow(ModPlacedFeatures.CONVERGENCE_ARRAY_PLACED));
        BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder()
                .fogColor(0xC8E8FF).waterColor(0x4FC3F7).waterFogColor(0x0288D1)
                .skyColor(0xE3F2FD).grassColorOverride(0xB3E5FC).foliageColorOverride(0x81D4FA)
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.END_ROD, 0.0003f)).build();
        return new Biome.BiomeBuilder().hasPrecipitation(false).temperature(0.85f).downfall(0.0f)
                .specialEffects(effects).mobSpawnSettings(livelyMobs()).generationSettings(gen.build()).build();
    }

    public static Biome cloudPlateau(BootstapContext<net.minecraft.world.level.biome.Biome> ctx) {
        HolderGetter<PlacedFeature> pf = ctx.lookup(Registries.PLACED_FEATURE);
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(pf,
                ctx.lookup(Registries.CONFIGURED_CARVER));
        addBaseUndergroundFeatures(gen, pf);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.PATCH_GRASS_NORMAL));
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.FLOWER_DEFAULT));
        gen.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES,
                pf.getOrThrow(ModPlacedFeatures.SKY_VILLAGE_PLACED));
        BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder()
                .fogColor(0xFFFFFF).waterColor(0xB3E5FC).waterFogColor(0x4FC3F7)
                .skyColor(0xE1F5FE).grassColorOverride(0xCCF5FF).foliageColorOverride(0xB2EBF2)
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.CLOUD, 0.0002f)).build();
        return new Biome.BiomeBuilder().hasPrecipitation(false).temperature(0.9f).downfall(0.0f)
                .specialEffects(effects).mobSpawnSettings(skyMobs()).generationSettings(gen.build()).build();
    }

    public static Biome divineCrystalForest(BootstapContext<net.minecraft.world.level.biome.Biome> ctx) {
        HolderGetter<PlacedFeature> pf = ctx.lookup(Registries.PLACED_FEATURE);
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(pf,
                ctx.lookup(Registries.CONFIGURED_CARVER));
        addBaseUndergroundFeatures(gen, pf);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.TREES_BIRCH));
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.PATCH_GRASS_JUNGLE));
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.PATCH_TALL_GRASS_2));
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.FLOWER_DEFAULT));
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.VINES));
        gen.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES,
                pf.getOrThrow(ModPlacedFeatures.CONVERGENCE_ARRAY_PLACED));
        BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder()
                .fogColor(0xA8D8FF).waterColor(0x29B6F6).waterFogColor(0x0277BD)
                .skyColor(0xDCEEFF).grassColorOverride(0x90CAF9).foliageColorOverride(0x64B5F6)
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.ENCHANT, 0.0004f)).build();
        return new Biome.BiomeBuilder().hasPrecipitation(false).temperature(0.7f).downfall(0.0f)
                .specialEffects(effects).mobSpawnSettings(forestMobs()).generationSettings(gen.build()).build();
    }

    public static Biome sacredHills(BootstapContext<net.minecraft.world.level.biome.Biome> ctx) {
        HolderGetter<PlacedFeature> pf = ctx.lookup(Registries.PLACED_FEATURE);
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(pf,
                ctx.lookup(Registries.CONFIGURED_CARVER));
        addBaseUndergroundFeatures(gen, pf);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.TREES_PLAINS));
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.FLOWER_PLAINS));
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.PATCH_GRASS_NORMAL));
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.PATCH_SUGAR_CANE));
        gen.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES,
                pf.getOrThrow(ModPlacedFeatures.CONVERGENCE_ARRAY_PLACED));
        BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder()
                .fogColor(0xFFF9E6).waterColor(0x81D4FA).waterFogColor(0x4FC3F7)
                .skyColor(0xFFFDE7).grassColorOverride(0xB2DFDB).foliageColorOverride(0x80CBC4)
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.END_ROD, 0.0002f)).build();
        return new Biome.BiomeBuilder().hasPrecipitation(false).temperature(0.85f).downfall(0.05f)
                .specialEffects(effects).mobSpawnSettings(livelyMobs()).generationSettings(gen.build()).build();
    }

    // ── ASGARD BIOMES ─────────────────────────────────────────────────────

    public static Biome jadePeaks(BootstapContext<net.minecraft.world.level.biome.Biome> ctx) {
        HolderGetter<PlacedFeature> pf = ctx.lookup(Registries.PLACED_FEATURE);
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(pf,
                ctx.lookup(Registries.CONFIGURED_CARVER));
        addBaseUndergroundFeatures(gen, pf);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.TREES_SNOWY));
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.PATCH_GRASS_NORMAL));
        gen.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES,
                pf.getOrThrow(ModPlacedFeatures.JADE_SPIKE_PLACED));
        BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder()
                .fogColor(0xB2DFDB).waterColor(0x26A69A).waterFogColor(0x00796B)
                .skyColor(0xE0F2F1).grassColorOverride(0x80CBC4).foliageColorOverride(0x4DB6AC).build();
        return new Biome.BiomeBuilder().hasPrecipitation(true).temperature(0.3f).downfall(0.4f)
                .specialEffects(effects).mobSpawnSettings(forestMobs()).generationSettings(gen.build()).build();
    }

    public static Biome goldenForest(BootstapContext<net.minecraft.world.level.biome.Biome> ctx) {
        HolderGetter<PlacedFeature> pf = ctx.lookup(Registries.PLACED_FEATURE);
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(pf,
                ctx.lookup(Registries.CONFIGURED_CARVER));
        addBaseUndergroundFeatures(gen, pf);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.TREES_BIRCH_AND_OAK));
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.TREES_BIRCH));
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.PATCH_GRASS_FOREST));
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.FLOWER_DEFAULT));
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.PATCH_TALL_GRASS));
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.BROWN_MUSHROOM_NORMAL));
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.RED_MUSHROOM_NORMAL));
        BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder()
                .fogColor(0xFFF8E1).waterColor(0xFFD54F).waterFogColor(0xFFB300)
                .skyColor(0xFFFDE7).grassColorOverride(0xDCE775).foliageColorOverride(0xFFD740)
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.FALLING_SPORE_BLOSSOM, 0.0002f)).build();
        return new Biome.BiomeBuilder().hasPrecipitation(true).temperature(0.7f).downfall(0.3f)
                .specialEffects(effects).mobSpawnSettings(forestMobs()).generationSettings(gen.build()).build();
    }

    public static Biome asgardOcean(BootstapContext<net.minecraft.world.level.biome.Biome> ctx) {
        HolderGetter<PlacedFeature> pf = ctx.lookup(Registries.PLACED_FEATURE);
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(pf,
                ctx.lookup(Registries.CONFIGURED_CARVER));
        addBaseUndergroundFeatures(gen, pf);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(AquaticPlacements.KELP_COLD));
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(AquaticPlacements.SEAGRASS_DEEP));
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(AquaticPlacements.SEA_PICKLE));
        BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder()
                .fogColor(0x80CBC4).waterColor(0x00796B).waterFogColor(0x004D40)
                .skyColor(0xB2EBF2).build();
        return new Biome.BiomeBuilder().hasPrecipitation(true).temperature(0.5f).downfall(0.5f)
                .specialEffects(effects).mobSpawnSettings(riverMobs()).generationSettings(gen.build()).build();
    }

    public static Biome asgardPlains(BootstapContext<net.minecraft.world.level.biome.Biome> ctx) {
        HolderGetter<PlacedFeature> pf = ctx.lookup(Registries.PLACED_FEATURE);
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(pf,
                ctx.lookup(Registries.CONFIGURED_CARVER));
        addBaseUndergroundFeatures(gen, pf);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.TREES_PLAINS));
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.PATCH_GRASS_NORMAL));
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.FLOWER_PLAINS));
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.PATCH_SUGAR_CANE));
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.PATCH_SUNFLOWER));
        BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder()
                .fogColor(0xDCEDC8).waterColor(0x66BB6A).waterFogColor(0x388E3C)
                .skyColor(0xF1F8E9).grassColorOverride(0xAED581).foliageColorOverride(0x9CCC65).build();
        return new Biome.BiomeBuilder().hasPrecipitation(true).temperature(0.8f).downfall(0.2f)
                .specialEffects(effects).mobSpawnSettings(plainsMobs()).generationSettings(gen.build()).build();
    }

    public static Biome swordPlains(BootstapContext<net.minecraft.world.level.biome.Biome> ctx) {
        HolderGetter<PlacedFeature> pf = ctx.lookup(Registries.PLACED_FEATURE);
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(pf,
                ctx.lookup(Registries.CONFIGURED_CARVER));
        addBaseUndergroundFeatures(gen, pf);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.PATCH_DEAD_BUSH));
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.PATCH_GRASS_NORMAL));
        gen.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES,
                pf.getOrThrow(ModPlacedFeatures.SWORD_STATUE_PLACED));
        BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder()
                .fogColor(0xCFD8DC).waterColor(0x455A64).waterFogColor(0x263238)
                .skyColor(0xECEFF1).grassColorOverride(0x90A4AE).foliageColorOverride(0x78909C).build();
        return new Biome.BiomeBuilder().hasPrecipitation(false).temperature(0.5f).downfall(0.1f)
                .specialEffects(effects).mobSpawnSettings(noMobSpawns()).generationSettings(gen.build()).build();
    }

    public static Biome jadeRiverKarst(BootstapContext<net.minecraft.world.level.biome.Biome> ctx) {
        HolderGetter<PlacedFeature> pf = ctx.lookup(Registries.PLACED_FEATURE);
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(pf,
                ctx.lookup(Registries.CONFIGURED_CARVER));
        addBaseUndergroundFeatures(gen, pf);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.TREES_JUNGLE));
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.PATCH_GRASS_JUNGLE));
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.PATCH_TALL_GRASS_2));
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.PATCH_SUGAR_CANE));
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.VINES));
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(AquaticPlacements.SEAGRASS_SWAMP));
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.FLOWER_SWAMP));
        gen.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES,
                pf.getOrThrow(ModPlacedFeatures.JADE_SPIKE_PLACED));
        BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder()
                .fogColor(0xA5D6A7).waterColor(0x2E7D32).waterFogColor(0x1B5E20)
                .skyColor(0xC8E6C9).grassColorOverride(0x66BB6A).foliageColorOverride(0x43A047)
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.DRIPPING_WATER, 0.0003f)).build();
        return new Biome.BiomeBuilder().hasPrecipitation(true).temperature(0.9f).downfall(0.9f)
                .specialEffects(effects).mobSpawnSettings(riverMobs()).generationSettings(gen.build()).build();
    }

    public static Biome heavenPillarForest(BootstapContext<net.minecraft.world.level.biome.Biome> ctx) {
        HolderGetter<PlacedFeature> pf = ctx.lookup(Registries.PLACED_FEATURE);
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(pf,
                ctx.lookup(Registries.CONFIGURED_CARVER));
        addBaseUndergroundFeatures(gen, pf);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.TREES_TAIGA));
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.TREES_OLD_GROWTH_SPRUCE_TAIGA));
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.PATCH_GRASS_TAIGA_2));
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.PATCH_GRASS_TAIGA));   // replaces PATCH_TAIGA_GRASS
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.PATCH_LARGE_FERN));
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.MUSHROOM_ISLAND_VEGETATION));
        BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder()
                .fogColor(0xB2EBF2).waterColor(0x0097A7).waterFogColor(0x006064)
                .skyColor(0xE0F7FA).grassColorOverride(0x4DB6AC).foliageColorOverride(0x26A69A)
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.CLOUD, 0.0003f)).build();
        return new Biome.BiomeBuilder().hasPrecipitation(true).temperature(0.7f).downfall(0.8f)
                .specialEffects(effects).mobSpawnSettings(forestMobs()).generationSettings(gen.build()).build();
    }

    public static Biome skyPiercer(BootstapContext<net.minecraft.world.level.biome.Biome> ctx) {
        HolderGetter<PlacedFeature> pf = ctx.lookup(Registries.PLACED_FEATURE);
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(pf,
                ctx.lookup(Registries.CONFIGURED_CARVER));
        addBaseUndergroundFeatures(gen, pf);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pf.getOrThrow(VegetationPlacements.PATCH_DEAD_BUSH));
        gen.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES,
                pf.getOrThrow(ModPlacedFeatures.CONVERGENCE_ARRAY_PLACED));
        gen.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES,
                pf.getOrThrow(ModPlacedFeatures.SWORD_STATUE_PLACED));
        BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder()
                .fogColor(0xFFD54F).waterColor(0xFFB300).waterFogColor(0xFF8F00)
                .skyColor(0xFFF8E1).grassColorOverride(0xFFCC02).foliageColorOverride(0xFFB300)
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.END_ROD, 0.0006f)).build();
        return new Biome.BiomeBuilder().hasPrecipitation(false).temperature(0.7f).downfall(0.1f)
                .specialEffects(effects).mobSpawnSettings(noMobSpawns()).generationSettings(gen.build()).build();
    }
}
