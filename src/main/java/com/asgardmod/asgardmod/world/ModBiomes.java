package com.asgardmod.asgardmod.world;

import com.asgardmod.asgardmod.AsgardMod;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class ModBiomes {

    // ── Deferred register (needed so Forge knows biomes exist) ─────────────
    public static final DeferredRegister<Biome> BIOMES =
        DeferredRegister.create(ForgeRegistries.BIOMES, AsgardMod.MOD_ID);

    // ── GOD'S DOMAIN biome keys ────────────────────────────────────────────
    public static final ResourceKey<Biome> CELESTIAL_MEADOW     = key("celestial_meadow");
    public static final ResourceKey<Biome> CLOUD_PLATEAU        = key("cloud_plateau");
    public static final ResourceKey<Biome> DIVINE_CRYSTAL_FOREST = key("divine_crystal_forest");
    public static final ResourceKey<Biome> SACRED_HILLS         = key("sacred_hills");

    // ── ASGARD biome keys ─────────────────────────────────────────────────
    public static final ResourceKey<Biome> JADE_PEAKS           = key("jade_peaks");
    public static final ResourceKey<Biome> GOLDEN_FOREST        = key("golden_forest");
    public static final ResourceKey<Biome> ASGARD_OCEAN         = key("asgard_ocean");
    public static final ResourceKey<Biome> ASGARD_PLAINS        = key("asgard_plains");
    public static final ResourceKey<Biome> SWORD_PLAINS         = key("sword_plains");
    public static final ResourceKey<Biome> JADE_RIVER_KARST     = key("jade_river_karst");
    public static final ResourceKey<Biome> HEAVEN_PILLAR_FOREST = key("heaven_pillar_forest");
    public static final ResourceKey<Biome> SKY_PIERCER          = key("sky_piercer");

    private static ResourceKey<Biome> key(String name) {
        return ResourceKey.create(Registries.BIOME,
            new ResourceLocation(AsgardMod.MOD_ID, name));
    }

    private static Climate.Parameter r(float a, float b) {
        return Climate.Parameter.span(a, b);
    }

    /** temp, humidity, continentalness, erosion, weirdness — depth fixed at 0, offset 0 */
    private static Climate.ParameterPoint params(
            Climate.Parameter temp, Climate.Parameter hum,
            Climate.Parameter cont, Climate.Parameter ero,
            Climate.Parameter weird) {
        return new Climate.ParameterPoint(temp, hum, cont, ero,
            Climate.Parameter.point(0f), weird, 0f);
    }

    // ── GOD'S DOMAIN pairs ─────────────────────────────────────────────────
    public static List<Pair<Climate.ParameterPoint, Holder<Biome>>>
    godsDomainPairs(HolderGetter<Biome> getter) {
        return List.of(
            Pair.of(params(r(-0.2f,0.2f), r(-0.1f,0.3f), r(0.1f,0.9f),  r(-0.5f,0.5f),  r(-0.2f,0.2f)),
                getter.getOrThrow(CELESTIAL_MEADOW)),
            Pair.of(params(r(0.0f,0.4f),  r(-0.3f,0.1f), r(0.5f,1.0f),  r(-1.0f,-0.5f), r(-0.3f,0.3f)),
                getter.getOrThrow(CLOUD_PLATEAU)),
            Pair.of(params(r(-0.3f,0.1f), r(0.2f,0.6f),  r(0.0f,0.6f),  r(-0.3f,0.3f),  r(0.2f,0.8f)),
                getter.getOrThrow(DIVINE_CRYSTAL_FOREST)),
            Pair.of(params(r(0.1f,0.5f),  r(-0.2f,0.2f), r(0.2f,0.8f),  r(-0.2f,0.4f),  r(-0.5f,0.0f)),
                getter.getOrThrow(SACRED_HILLS))
        );
    }

    // ── ASGARD pairs ───────────────────────────────────────────────────────
    public static List<Pair<Climate.ParameterPoint, Holder<Biome>>>
    asgardPairs(HolderGetter<Biome> getter) {
        return List.of(
            Pair.of(params(r(-0.3f,0.1f), r(0.0f,0.5f),  r(0.7f,1.0f),   r(-1.0f,-0.6f), r(-0.3f,0.3f)),
                getter.getOrThrow(JADE_PEAKS)),
            Pair.of(params(r(0.2f,0.6f),  r(0.1f,0.5f),  r(0.2f,0.7f),   r(-0.4f,0.1f),  r(-0.2f,0.4f)),
                getter.getOrThrow(GOLDEN_FOREST)),
            Pair.of(params(r(0.0f,0.4f),  r(-0.2f,0.3f), r(-1.0f,-0.2f), r(-0.2f,0.5f),  r(-0.3f,0.3f)),
                getter.getOrThrow(ASGARD_OCEAN)),
            Pair.of(params(r(0.3f,0.7f),  r(-0.3f,0.2f), r(0.1f,0.5f),   r(0.1f,0.7f),   r(-0.5f,0.0f)),
                getter.getOrThrow(ASGARD_PLAINS)),
            Pair.of(params(r(0.1f,0.5f),  r(-0.5f,0.0f), r(0.0f,0.5f),   r(0.3f,0.9f),   r(0.3f,0.9f)),
                getter.getOrThrow(SWORD_PLAINS)),
            Pair.of(params(r(0.5f,0.9f),  r(0.4f,0.9f),  r(0.0f,0.6f),   r(-0.5f,0.2f),  r(0.4f,1.0f)),
                getter.getOrThrow(JADE_RIVER_KARST)),
            Pair.of(params(r(0.4f,0.8f),  r(0.5f,1.0f),  r(0.3f,0.7f),   r(-0.7f,-0.2f), r(-0.9f,-0.4f)),
                getter.getOrThrow(HEAVEN_PILLAR_FOREST)),
            // Sky Piercer — ultra-tight window ensures ~1 occurrence per world
            Pair.of(params(r(0.6f,0.8f),  r(-0.1f,0.2f), r(0.88f,1.0f),  r(-1.0f,-0.85f),r(0.85f,1.0f)),
                getter.getOrThrow(SKY_PIERCER))
        );
    }

    public static void register(IEventBus eventBus) {
        BIOMES.register(eventBus);
    }
}
