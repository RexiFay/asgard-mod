package com.asgardmod.asgardmod.world.feature;

import com.asgardmod.asgardmod.block.ModBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class JadeSpikeFeature extends Feature<NoneFeatureConfiguration> {

    public JadeSpikeFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        WorldGenLevel level = ctx.level();
        BlockPos origin = ctx.origin();
        RandomSource random = ctx.random();

        int height = 8 + random.nextInt(20); // 8-28 blocks tall
        int baseRadius = 3 + random.nextInt(3);

        for (int dy = 0; dy <= height; dy++) {
            // Radius shrinks as we go up — creates taper
            double progress = (double) dy / height;
            int radius = (int) Math.max(0, baseRadius * (1.0 - progress * progress));

            for (int dx = -radius; dx <= radius; dx++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    if (dx * dx + dz * dz <= radius * radius) {
                        BlockPos pos = origin.offset(dx, dy, dz);
                        // Occasionally place gold_stone for subtle gold accent
                        if (random.nextInt(12) == 0) {
                            level.setBlock(pos, ModBlocks.GOLD_STONE.get().defaultBlockState(), 2);
                        } else {
                            level.setBlock(pos, ModBlocks.JADE_BLOCK.get().defaultBlockState(), 2);
                        }
                    }
                }
            }
        }
        return true;
    }
}
