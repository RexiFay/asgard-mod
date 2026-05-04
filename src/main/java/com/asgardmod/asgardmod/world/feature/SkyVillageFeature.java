package com.asgardmod.asgardmod.world.feature;

import com.asgardmod.asgardmod.block.ModBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SkyVillageFeature extends Feature<NoneFeatureConfiguration> {

    public SkyVillageFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        WorldGenLevel level = ctx.level();
        BlockPos origin = ctx.origin();
        RandomSource random = ctx.random();

        // Place the cloud island base first
        buildCloudIsland(level, origin, random);

        // Then build 2-5 houses on top
        int houseCount = 2 + random.nextInt(4);
        int radius = 10;
        for (int i = 0; i < houseCount; i++) {
            int ox = random.nextInt(radius * 2) - radius;
            int oz = random.nextInt(radius * 2) - radius;
            BlockPos houseOrigin = origin.offset(ox, 2, oz);
            buildHouse(level, houseOrigin, random);
        }

        // Central decorative pillar/well
        buildCentralWell(level, origin.above(2), random);

        return true;
    }

    // ── Cloud Island ──────────────────────────────────────────────────────

    private void buildCloudIsland(WorldGenLevel level, BlockPos center, RandomSource random) {
        int cloudRadius = 14;
        BlockState cloud = Blocks.WHITE_WOOL.defaultBlockState();
        BlockState glass = Blocks.WHITE_STAINED_GLASS.defaultBlockState();
        BlockState marble = ModBlocks.CELESTIAL_MARBLE.get().defaultBlockState();

        for (int dx = -cloudRadius; dx <= cloudRadius; dx++) {
            for (int dz = -cloudRadius; dz <= cloudRadius; dz++) {
                double distSq = dx * dx + dz * dz;
                double maxR = cloudRadius - Math.abs(dx) * 0.2 - Math.abs(dz) * 0.2;
                if (distSq > maxR * maxR) continue;

                double dist = Math.sqrt(distSq);
                double edgeFactor = dist / cloudRadius;

                // Bottom layer: glass for a translucent underside
                if (random.nextDouble() > edgeFactor * 0.6) {
                    level.setBlock(center.offset(dx, -1, dz), glass, 2);
                }
                // Main cloud layer
                level.setBlock(center.offset(dx, 0, dz), cloud, 2);
                // Second layer, slightly narrower for a puffed-up look
                if (distSq < (cloudRadius - 3) * (cloudRadius - 3)) {
                    level.setBlock(center.offset(dx, 1, dz), cloud, 2);
                }
                // Marble rim around the edge at cloud level
                if (dist >= cloudRadius - 2.5 && dist <= cloudRadius) {
                    level.setBlock(center.offset(dx, 0, dz), marble, 2);
                }
            }
        }
    }

    // ── House ─────────────────────────────────────────────────────────────

    private void buildHouse(WorldGenLevel level, BlockPos base, RandomSource random) {
        int w = 3 + random.nextInt(3); // 3-5 wide
        int d = 3 + random.nextInt(3); // 3-5 deep
        int h = 3 + random.nextInt(2); // 3-4 tall

        BlockState wall   = ModBlocks.CELESTIAL_MARBLE.get().defaultBlockState();
        BlockState floor  = ModBlocks.DIVINE_CLOUD.get() != null
                ? ModBlocks.DIVINE_CLOUD.get().defaultBlockState()
                : Blocks.QUARTZ_BLOCK.defaultBlockState();
        BlockState roof   = Blocks.QUARTZ_SLAB.defaultBlockState();
        BlockState glass  = Blocks.LIGHT_BLUE_STAINED_GLASS_PANE.defaultBlockState();
        BlockState door   = Blocks.OAK_DOOR.defaultBlockState();
        BlockState air    = Blocks.AIR.defaultBlockState();
        BlockState lantern = Blocks.LANTERN.defaultBlockState();

        // Floor
        for (int x = 0; x <= w; x++)
            for (int z = 0; z <= d; z++)
                level.setBlock(base.offset(x, 0, z), floor, 2);

        // Walls
        for (int y = 1; y <= h; y++) {
            for (int x = 0; x <= w; x++) {
                level.setBlock(base.offset(x, y, 0), wall, 2);
                level.setBlock(base.offset(x, y, d), wall, 2);
            }
            for (int z = 1; z < d; z++) {
                level.setBlock(base.offset(0, y, z), wall, 2);
                level.setBlock(base.offset(w, y, z), wall, 2);
            }
        }

        // Windows on middle Y
        int midY = 2;
        level.setBlock(base.offset(w / 2, midY, 0), glass, 2);
        level.setBlock(base.offset(w / 2, midY, d), glass, 2);
        level.setBlock(base.offset(0, midY, d / 2), glass, 2);
        level.setBlock(base.offset(w, midY, d / 2), glass, 2);

        // Door opening on front wall (z=0)
        level.setBlock(base.offset(w / 2, 1, 0), air, 2);
        level.setBlock(base.offset(w / 2, 2, 0), air, 2);

        // Ceiling
        for (int x = 0; x <= w; x++)
            for (int z = 0; z <= d; z++)
                level.setBlock(base.offset(x, h + 1, z), roof, 2);

        // Hanging lantern inside
        level.setBlock(base.offset(w / 2, h, d / 2), lantern, 2);

        // Interior — clear air
        for (int y = 1; y <= h; y++)
            for (int x = 1; x < w; x++)
                for (int z = 1; z < d; z++)
                    level.setBlock(base.offset(x, y, z), air, 2);
    }

    // ── Central Well / Fountain ───────────────────────────────────────────

    private void buildCentralWell(WorldGenLevel level, BlockPos center, RandomSource random) {
        BlockState marble  = ModBlocks.CELESTIAL_MARBLE.get().defaultBlockState();
        BlockState water   = Blocks.WATER.defaultBlockState();
        BlockState lantern = Blocks.SEA_LANTERN.defaultBlockState();
        BlockState fence   = Blocks.QUARTZ_SLAB.defaultBlockState();

        // 3x3 well wall
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                if (Math.abs(dx) == 1 || Math.abs(dz) == 1) {
                    level.setBlock(center.offset(dx, 0, dz), marble, 2);
                    level.setBlock(center.offset(dx, 1, dz), marble, 2);
                } else {
                    level.setBlock(center.offset(dx, -1, dz), lantern, 2);
                    level.setBlock(center.offset(dx, 0, dz), water, 2);
                }
            }
        }
        // Cap corners
        for (int dx = -1; dx <= 1; dx += 2)
            for (int dz = -1; dz <= 1; dz += 2)
                level.setBlock(center.offset(dx, 2, dz), fence, 2);
    }
}
