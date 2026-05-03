package com.asgardmod.asgardmod.world;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.biome.*;

public class ModBiomeData {

    private static BiomeGenerationSettings emptyGenSettings() {
        return BiomeGenerationSettings.EMPTY;
    }

    private static MobSpawnSettings noMobSpawns() {
        return MobSpawnSettings.EMPTY;
    }

    // -------------------------
    // GOD'S DOMAIN BIOMES
    // -------------------------

    public static Biome celestialMeadow() {
        BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder()
                .fogColor(0xC8E8FF)
                .waterColor(0x4FC3F7)
                .waterFogColor(0x0288D1)
                .skyColor(0xE3F2FD)
                .grassColorOverride(0xB3E5FC)
                .foliageColorOverride(0x81D4FA)
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.END_ROD, 0.0003f))
                .build();
        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(0.85f)
                .downfall(0.0f)
                .specialEffects(effects)
                .mobSpawnSettings(noMobSpawns())
                .generationSettings(emptyGenSettings())
                .build();
    }

    public static Biome cloudPlateau() {
        BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder()
                .fogColor(0xFFFFFF)
                .waterColor(0xB3E5FC)
                .waterFogColor(0x4FC3F7)
                .skyColor(0xE1F5FE)
                .grassColorOverride(0xCCF5FF)
                .foliageColorOverride(0xB2EBF2)
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.CLOUD, 0.0002f))
                .build();
        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(0.9f)
                .downfall(0.0f)
                .specialEffects(effects)
                .mobSpawnSettings(noMobSpawns())
                .generationSettings(emptyGenSettings())
                .build();
    }

    public static Biome divineCrystalForest() {
        BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder()
                .fogColor(0xA8D8FF)
                .waterColor(0x29B6F6)
                .waterFogColor(0x0277BD)
                .skyColor(0xDCEEFF)
                .grassColorOverride(0x90CAF9)
                .foliageColorOverride(0x64B5F6)
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.ENCHANT, 0.0004f))
                .build();
        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(0.7f)
                .downfall(0.0f)
                .specialEffects(effects)
                .mobSpawnSettings(noMobSpawns())
                .generationSettings(emptyGenSettings())
                .build();
    }

    public static Biome sacredHills() {
        BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder()
                .fogColor(0xFFF9E6)
                .waterColor(0x81D4FA)
                .waterFogColor(0x4FC3F7)
                .skyColor(0xFFFDE7)
                .grassColorOverride(0xB2DFDB)
                .foliageColorOverride(0x80CBC4)
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.END_ROD, 0.0002f))
                .build();
        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(0.85f)
                .downfall(0.05f)
                .specialEffects(effects)
                .mobSpawnSettings(noMobSpawns())
                .generationSettings(emptyGenSettings())
                .build();
    }

    // -------------------------
    // ASGARD BIOMES
    // -------------------------

    public static Biome jadePeaks() {
        BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder()
                .fogColor(0xB2DFDB)
                .waterColor(0x26A69A)
                .waterFogColor(0x00796B)
                .skyColor(0xE0F2F1)
                .grassColorOverride(0x80CBC4)
                .foliageColorOverride(0x4DB6AC)
                .build();
        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.3f)
                .downfall(0.4f)
                .specialEffects(effects)
                .mobSpawnSettings(noMobSpawns())
                .generationSettings(emptyGenSettings())
                .build();
    }

    public static Biome goldenForest() {
        BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder()
                .fogColor(0xFFF8E1)
                .waterColor(0xFFD54F)
                .waterFogColor(0xFFB300)
                .skyColor(0xFFFDE7)
                .grassColorOverride(0xDCE775)
                .foliageColorOverride(0xFFD740)
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.FALLING_SPORE_BLOSSOM, 0.0002f))
                .build();
        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.7f)
                .downfall(0.3f)
                .specialEffects(effects)
                .mobSpawnSettings(noMobSpawns())
                .generationSettings(emptyGenSettings())
                .build();
    }

    public static Biome asgardOcean() {
        BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder()
                .fogColor(0x80CBC4)
                .waterColor(0x00796B)
                .waterFogColor(0x004D40)
                .skyColor(0xB2EBF2)
                .build();
        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.5f)
                .downfall(0.5f)
                .specialEffects(effects)
                .mobSpawnSettings(noMobSpawns())
                .generationSettings(emptyGenSettings())
                .build();
    }

    public static Biome asgardPlains() {
        BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder()
                .fogColor(0xDCEDC8)
                .waterColor(0x66BB6A)
                .waterFogColor(0x388E3C)
                .skyColor(0xF1F8E9)
                .grassColorOverride(0xAED581)
                .foliageColorOverride(0x9CCC65)
                .build();
        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.8f)
                .downfall(0.2f)
                .specialEffects(effects)
                .mobSpawnSettings(noMobSpawns())
                .generationSettings(emptyGenSettings())
                .build();
    }

    public static Biome swordPlains() {
        BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder()
                .fogColor(0xCFD8DC)
                .waterColor(0x455A64)
                .waterFogColor(0x263238)
                .skyColor(0xECEFF1)
                .grassColorOverride(0x90A4AE)
                .foliageColorOverride(0x78909C)
                .build();
        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(0.5f)
                .downfall(0.1f)
                .specialEffects(effects)
                .mobSpawnSettings(noMobSpawns())
                .generationSettings(emptyGenSettings())
                .build();
    }

    public static Biome jadeRiverKarst() {
        BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder()
                .fogColor(0xA5D6A7)
                .waterColor(0x2E7D32)
                .waterFogColor(0x1B5E20)
                .skyColor(0xC8E6C9)
                .grassColorOverride(0x66BB6A)
                .foliageColorOverride(0x43A047)
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.DRIPPING_WATER, 0.0003f))
                .build();
        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.9f)
                .downfall(0.9f)
                .specialEffects(effects)
                .mobSpawnSettings(noMobSpawns())
                .generationSettings(emptyGenSettings())
                .build();
    }

    public static Biome heavenPillarForest() {
        BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder()
                .fogColor(0xB2EBF2)
                .waterColor(0x0097A7)
                .waterFogColor(0x006064)
                .skyColor(0xE0F7FA)
                .grassColorOverride(0x4DB6AC)
                .foliageColorOverride(0x26A69A)
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.CLOUD, 0.0003f))
                .build();
        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.7f)
                .downfall(0.8f)
                .specialEffects(effects)
                .mobSpawnSettings(noMobSpawns())
                .generationSettings(emptyGenSettings())
                .build();
    }

    public static Biome skyPiercer() {
        BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder()
                .fogColor(0xFFD54F)
                .waterColor(0xFFB300)
                .waterFogColor(0xFF8F00)
                .skyColor(0xFFF8E1)
                .grassColorOverride(0xFFCC02)
                .foliageColorOverride(0xFFB300)
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.END_ROD, 0.0006f))
                .build();
        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(0.7f)
                .downfall(0.1f)
                .specialEffects(effects)
                .mobSpawnSettings(noMobSpawns())
                .generationSettings(emptyGenSettings())
                .build();
    }
}
