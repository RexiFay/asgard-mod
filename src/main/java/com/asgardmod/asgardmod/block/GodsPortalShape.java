package com.asgardmod.asgardmod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Validates a Celestial Marble frame for the Gods Domain portal.
 *
 * Minimum interior: 2 wide x 3 tall  (outer frame = 4 wide x 5 tall)
 * Maximum interior: 21 wide x 21 tall (same cap as nether portal)
 * Both X-axis and Z-axis orientations are supported.
 *
 * The interior may be ANY size within those bounds as long as:
 *  - every cell inside is air or an existing portal block
 *  - the entire perimeter is Celestial Marble
 */
public class GodsPortalShape {

    public static final int MIN_WIDTH  = 2;  // interior
    public static final int MIN_HEIGHT = 3;  // interior
    public static final int MAX_WIDTH  = 21; // interior (nether cap)
    public static final int MAX_HEIGHT = 21; // interior

    private final LevelAccessor level;
    private final Direction.Axis axis;
    private final Direction rightDir;

    private BlockPos bottomLeft; // bottom-left interior corner
    private int width;           // interior width
    private int height;          // interior height

    public GodsPortalShape(LevelAccessor level, BlockPos pos, Direction.Axis axis) {
        this.level    = level;
        this.axis     = axis;
        this.rightDir = axis == Direction.Axis.X ? Direction.EAST : Direction.SOUTH;
        this.bottomLeft = findBottomLeft(pos);
        if (bottomLeft != null) {
            this.width  = measureWidth();
            this.height = measureHeight();
        }
    }

    // ── Public factory ────────────────────────────────────────────────────

    public static GodsPortalShape findAnyValidShape(LevelAccessor level, BlockPos pos) {
        GodsPortalShape x = new GodsPortalShape(level, pos, Direction.Axis.X);
        if (x.isValid()) return x;
        GodsPortalShape z = new GodsPortalShape(level, pos, Direction.Axis.Z);
        if (z.isValid()) return z;
        return null;
    }

    // ── Validity ──────────────────────────────────────────────────────────

    public boolean isValid() {
        if (bottomLeft == null) return false;
        if (width  < MIN_WIDTH  || width  > MAX_WIDTH)  return false;
        if (height < MIN_HEIGHT || height > MAX_HEIGHT) return false;
        return hasValidFrame() && interiorIsClean();
    }

    /** All perimeter blocks must be Celestial Marble. */
    private boolean hasValidFrame() {
        // Bottom and top rows (includes corners)
        for (int w = -1; w <= width; w++) {
            if (!isFrame(bottomLeft.relative(rightDir, w).below()))         return false;
            if (!isFrame(bottomLeft.relative(rightDir, w).above(height)))   return false;
        }
        // Left and right columns (interior height only)
        for (int h = 0; h < height; h++) {
            if (!isFrame(bottomLeft.relative(rightDir, -1).above(h)))       return false;
            if (!isFrame(bottomLeft.relative(rightDir, width).above(h)))    return false;
        }
        return true;
    }

    /** Every interior cell must be air or an existing portal block. */
    private boolean interiorIsClean() {
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                if (!isAirOrPortal(bottomLeft.relative(rightDir, w).above(h))) return false;
            }
        }
        return true;
    }

    private boolean isFrame(BlockPos pos) {
        return level.getBlockState(pos).getBlock() == ModBlocks.CELESTIAL_MARBLE.get();
    }

    private boolean isAirOrPortal(BlockPos pos) {
        BlockState s = level.getBlockState(pos);
        return s.isAir() || s.getBlock() == ModBlocks.GODS_DOMAIN_PORTAL.get();
    }

    // ── Measurement helpers ────────────────────────────────────────────────

    /**
     * From the clicked position, walk down to the floor then walk left
     * until hitting a frame block. Returns the bottom-left interior corner.
     */
    private BlockPos findBottomLeft(BlockPos pos) {
        // Walk down to the floor (air/portal above frame)
        BlockPos cur = pos;
        while (cur.getY() > level.getMinBuildHeight() && isAirOrPortal(cur.below())) {
            cur = cur.below();
        }
        // Walk left until we hit the left wall
        int steps = 0;
        while (isAirOrPortal(cur) && steps <= MAX_WIDTH + 1) {
            BlockPos leftNeighbor = cur.relative(rightDir.getOpposite());
            if (isFrame(leftNeighbor)) break; // left wall found
            cur = leftNeighbor;
            steps++;
        }
        // If we never found a left wall, invalid
        if (!isFrame(cur.relative(rightDir.getOpposite()))) return null;
        return cur;
    }

    /** Count air/portal cells going right from bottomLeft. */
    private int measureWidth() {
        int w = 0;
        BlockPos cur = bottomLeft;
        while (w <= MAX_WIDTH && isAirOrPortal(cur)) {
            cur = cur.relative(rightDir);
            w++;
        }
        return w;
    }

    /** Count air/portal cells going up from bottomLeft. */
    private int measureHeight() {
        int h = 0;
        BlockPos cur = bottomLeft;
        while (h <= MAX_HEIGHT && isAirOrPortal(cur)) {
            cur = cur.above();
            h++;
        }
        return h;
    }

    // ── Fill / Clear ──────────────────────────────────────────────────────

    public void createPortalBlocks() {
        BlockState portal = ModBlocks.GODS_DOMAIN_PORTAL.get().defaultBlockState();
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                level.setBlock(bottomLeft.relative(rightDir, w).above(h), portal, 18);
            }
        }
    }

    public static void clearPortalBlocks(LevelAccessor level, BlockPos anyPortalPos) {
        clearConnected(level, anyPortalPos, 0);
    }

    private static void clearConnected(LevelAccessor level, BlockPos pos, int depth) {
        if (depth > 500) return; // safety cap for huge portals
        if (level.getBlockState(pos).getBlock() != ModBlocks.GODS_DOMAIN_PORTAL.get()) return;
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 18);
        for (Direction dir : new Direction[]{Direction.UP, Direction.DOWN,
                Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST}) {
            clearConnected(level, pos.relative(dir), depth + 1);
        }
    }

    public Direction.Axis getAxis()   { return axis; }
    public BlockPos getBottomLeft()   { return bottomLeft; }
    public int getWidth()             { return width; }
    public int getHeight()            { return height; }
}
