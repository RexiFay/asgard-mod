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

public class ConvergenceArrayFeature extends Feature<NoneFeatureConfiguration> {

    public ConvergenceArrayFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    private enum ArrayType { SPIRITUAL, SPATIAL, CULTIVATION, ARTIFACT_REFINING, FORMATION }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        WorldGenLevel level = ctx.level();
        BlockPos origin = ctx.origin();
        RandomSource random = ctx.random();

        ArrayType type = ArrayType.values()[random.nextInt(ArrayType.values().length)];

        switch (type) {
            case SPIRITUAL -> placeSpiral(level, origin, random);
            case SPATIAL -> placeSpatialGrid(level, origin, random);
            case CULTIVATION -> placeCultivationRings(level, origin);
            case ARTIFACT_REFINING -> placeArtifactStar(level, origin);
            case FORMATION -> placeFormationHex(level, origin);
        }
        return true;
    }

    // Fibonacci spiral of convergence nodes
    private void placeSpiral(WorldGenLevel level, BlockPos origin, RandomSource random) {
        double angle = 0;
        double radius = 0;
        for (int i = 0; i < 34; i++) {
            angle += 137.5;
            radius = Math.sqrt(i) * 2.5;
            int x = (int) (Math.cos(Math.toRadians(angle)) * radius);
            int z = (int) (Math.sin(Math.toRadians(angle)) * radius);
            BlockPos pos = origin.offset(x, 0, z);
            level.setBlock(pos, ModBlocks.CONVERGENCE_NODE.get().defaultBlockState(), 2);
            if (i % 5 == 0) {
                level.setBlock(pos.above(), ModBlocks.JADE_PILLAR.get().defaultBlockState(), 2);
            }
        }
    }

    // 9x9 grid with jade pillars at intersections
    private void placeSpatialGrid(WorldGenLevel level, BlockPos origin, RandomSource random) {
        for (int x = -12; x <= 12; x += 3) {
            for (int z = -12; z <= 12; z += 3) {
                level.setBlock(origin.offset(x, 0, z),
                        ModBlocks.CONVERGENCE_NODE.get().defaultBlockState(), 2);
                if (x % 6 == 0 && z % 6 == 0) {
                    for (int y = 1; y <= 4; y++) {
                        level.setBlock(origin.offset(x, y, z),
                                ModBlocks.JADE_PILLAR.get().defaultBlockState(), 2);
                    }
                }
            }
        }
    }

    // Concentric rings
    private void placeCultivationRings(WorldGenLevel level, BlockPos origin) {
        int[] radii = {4, 8, 12, 16};
        for (int r : radii) {
            for (int deg = 0; deg < 360; deg += 5) {
                int x = (int) (Math.cos(Math.toRadians(deg)) * r);
                int z = (int) (Math.sin(Math.toRadians(deg)) * r);
                level.setBlock(origin.offset(x, 0, z),
                        ModBlocks.CONVERGENCE_NODE.get().defaultBlockState(), 2);
            }
        }
        // Center node
        level.setBlock(origin, ModBlocks.SKY_PIERCER_CORE.get().defaultBlockState(), 2);
    }

    // 8-point star
    private void placeArtifactStar(WorldGenLevel level, BlockPos origin) {
        int[] angles = {0, 45, 90, 135, 180, 225, 270, 315};
        for (int angle : angles) {
            for (int r = 1; r <= 14; r++) {
                int x = (int) (Math.cos(Math.toRadians(angle)) * r);
                int z = (int) (Math.sin(Math.toRadians(angle)) * r);
                level.setBlock(origin.offset(x, 0, z),
                        r % 3 == 0
                                ? ModBlocks.SKY_PIERCER_CORE.get().defaultBlockState()
                                : ModBlocks.CONVERGENCE_NODE.get().defaultBlockState(), 2);
            }
        }
    }

    // Hexagonal formation
    private void placeFormationHex(WorldGenLevel level, BlockPos origin) {
        int[] hexAngles = {0, 60, 120, 180, 240, 300};
        int[] radii = {6, 12};
        for (int r : radii) {
            for (int angle : hexAngles) {
                int x = (int) (Math.cos(Math.toRadians(angle)) * r);
                int z = (int) (Math.sin(Math.toRadians(angle)) * r);
                BlockPos corner = origin.offset(x, 0, z);
                level.setBlock(corner, ModBlocks.SKY_PIERCER_CORE.get().defaultBlockState(), 2);
                // Connect corners with nodes
                int nextAngle = (angle + 60) % 360;
                int nx = (int) (Math.cos(Math.toRadians(nextAngle)) * r);
                int nz = (int) (Math.sin(Math.toRadians(nextAngle)) * r);
                for (int step = 1; step <= 4; step++) {
                    int lx = x + (nx - x) * step / 5;
                    int lz = z + (nz - z) * step / 5;
                    level.setBlock(origin.offset(lx, 0, lz),
                            ModBlocks.CONVERGENCE_NODE.get().defaultBlockState(), 2);
                }
            }
        }
        level.setBlock(origin, ModBlocks.SKY_PIERCER_CORE.get().defaultBlockState(), 2);
    }
}
