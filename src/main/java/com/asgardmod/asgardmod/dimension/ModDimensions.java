package com.asgardmod.asgardmod.dimension;

import com.asgardmod.asgardmod.AsgardMod;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class ModDimensions {
    public static final ResourceKey<Level> GODS_DOMAIN =
            ResourceKey.create(net.minecraft.core.registries.Registries.DIMENSION,
                    new ResourceLocation(AsgardMod.MOD_ID, "gods_domain"));

    public static final ResourceKey<Level> ASGARD =
            ResourceKey.create(net.minecraft.core.registries.Registries.DIMENSION,
                    new ResourceLocation(AsgardMod.MOD_ID, "asgard"));
}
