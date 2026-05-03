package com.asgardmod.asgardmod.world.feature;

import com.asgardmod.asgardmod.block.ModBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SwordStatueFeature extends Feature<NoneFeatureConfiguration> {

    public SwordStatueFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        WorldGenLevel level = ctx.level();
        BlockPos origin = ctx.origin();
        RandomSource random = ctx.random();

        int height = 15 + random.nextInt(16); // 15-30 blocks tall
        int tiltX = random.nextInt(3) - 1;    // slight lean
        int tiltZ = random.nextInt(3) - 1;

        // Hilt base (stone brick, 3 blocks wide)
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                for (int dy = 0; dy <= 2; dy++) {
                    level.setBlock(origin.offset(dx, dy, dz),
                            Blocks.STONE_BRICKS.defaultBlockState(), 2);
                }
            }
        }

        // Cross-guard (3 blocks wide at height 3)
        for (int dx = -2; dx <= 2; dx++) {
            level.setBlock(origin.offset(dx, 3, 0),
                    ModBlocks.ANCIENT_IRON.get().defaultBlockState(), 2);
        }
        for (int dz = -2; dz <= 2; dz++) {
            level.setBlock(origin.offset(0, 3, dz),
                    ModBlocks.ANCIENT_IRON.get().defaultBlockState(), 2);
        }

        // Blade — tapers from 1-wide to tip
        for (int dy = 4; dy <= height; dy++) {
            int lean = (dy - 4) / 5;
            BlockPos bladePos = origin.offset(tiltX * lean, dy, tiltZ * lean);
            level.setBlock(bladePos, ModBlocks.ANCIENT_IRON.get().defaultBlockState(), 2);

            // Taper: add side blocks in lower third of blade
            if (dy < 4 + height / 3) {
                level.setBlock(bladePos.east(), ModBlocks.ANCIENT_IRON.get().defaultBlockState(), 2);
                level.setBlock(bladePos.west(), ModBlocks.ANCIENT_IRON.get().defaultBlockState(), 2);
            }
        }

        return true;
    }
}
