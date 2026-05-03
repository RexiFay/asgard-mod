package com.asgardmod.asgardmod.block;

import com.asgardmod.asgardmod.dimension.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Validates a 4-wide x 5-tall celestial marble frame and fills/clears
 * the interior with GodsPortalBlock, mirroring NetherPortalBlock's shape logic.
 *
 * Interior: 2 wide x 3 tall (corner blocks are frame, not air).
 * Orientation: X-axis or Z-axis aligned.
 */
public class GodsPortalShape {

    // Frame dimensions (interior)
    public static final int INTERIOR_WIDTH  = 2;
    public static final int INTERIOR_HEIGHT = 3;

    private final LevelAccessor level;
    private final Direction.Axis axis;
    private final Direction rightDir;

    // Bottom-left interior corner
    private BlockPos bottomLeft;
    private int width;
    private int height;

    public GodsPortalShape(LevelAccessor level, BlockPos pos, Direction.Axis axis) {
        this.level    = level;
        this.axis     = axis;
        this.rightDir = axis == Direction.Axis.X ? Direction.EAST : Direction.SOUTH;
        this.bottomLeft = findBottomLeft(pos);
        if (bottomLeft != null) {
            this.width  = findWidth();
            this.height = findHeight();
        }
    }

    // ── Public factory ────────────────────────────────────────────────────

    /** Returns a valid shape (either axis) for the given position, or null. */
    public static GodsPortalShape findAnyValidShape(LevelAccessor level, BlockPos pos) {
        GodsPortalShape xShape = new GodsPortalShape(level, pos, Direction.Axis.X);
        if (xShape.isValid()) return xShape;
        GodsPortalShape zShape = new GodsPortalShape(level, pos, Direction.Axis.Z);
        if (zShape.isValid()) return zShape;
        return null;
    }

    // ── Validity ──────────────────────────────────────────────────────────

    public boolean isValid() {
        return bottomLeft != null && width == INTERIOR_WIDTH && height == INTERIOR_HEIGHT
                && hasValidFrame();
    }

    private boolean hasValidFrame() {
        // Bottom row and top row
        for (int w = -1; w <= INTERIOR_WIDTH; w++) {
            BlockPos bottom = bottomLeft.relative(rightDir, w).below();
            BlockPos top    = bottomLeft.relative(rightDir, w).above(INTERIOR_HEIGHT);
            if (!isFrame(bottom) || !isFrame(top)) return false;
        }
        // Left and right columns
        for (int h = 0; h < INTERIOR_HEIGHT; h++) {
            BlockPos left  = bottomLeft.relative(rightDir, -1).above(h);
            BlockPos right = bottomLeft.relative(rightDir, INTERIOR_WIDTH).above(h);
            if (!isFrame(left) || !isFrame(right)) return false;
        }
        return true;
    }

    private boolean isFrame(BlockPos pos) {
        return level.getBlockState(pos).getBlock() == ModBlocks.CELESTIAL_MARBLE.get();
    }

    private boolean isAirOrPortal(BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return state.isAir() || state.getBlock() == ModBlocks.GODS_DOMAIN_PORTAL.get();
    }

    // ── Search helpers ────────────────────────────────────────────────────

    private BlockPos findBottomLeft(BlockPos pos) {
        // Walk down to find floor
        BlockPos cur = pos;
        while (cur.getY() > level.getMinBuildHeight() && isAirOrPortal(cur.below())) {
            cur = cur.below();
        }
        // Walk left to find left frame wall
        while (isAirOrPortal(cur) && !isFrame(cur.relative(rightDir.getOpposite()))) {
            cur = cur.relative(rightDir.getOpposite());
            if (cur.relative(rightDir.getOpposite(), INTERIOR_WIDTH).distSqr(pos) > 225) return null;
        }
        return cur;
    }

    private int findWidth() {
        int w = 0;
        BlockPos cur = bottomLeft;
        while (w <= INTERIOR_WIDTH + 1 && isAirOrPortal(cur)) {
            cur = cur.relative(rightDir);
            w++;
        }
        return w;
    }

    private int findHeight() {
        int h = 0;
        BlockPos cur = bottomLeft;
        while (h <= INTERIOR_HEIGHT + 1 && isAirOrPortal(cur)) {
            cur = cur.above();
            h++;
        }
        return h;
    }

    // ── Fill / Clear ──────────────────────────────────────────────────────

    public void createPortalBlocks() {
        BlockState portalState = ModBlocks.GODS_DOMAIN_PORTAL.get().defaultBlockState();
        for (int h = 0; h < INTERIOR_HEIGHT; h++) {
            for (int w = 0; w < INTERIOR_WIDTH; w++) {
                level.setBlock(bottomLeft.relative(rightDir, w).above(h), portalState, 18);
            }
        }
    }

    public static void clearPortalBlocks(LevelAccessor level, BlockPos anyPortalPos) {
        // Flood-fill to remove all connected portal blocks
        clearConnected(level, anyPortalPos, 0);
    }

    private static void clearConnected(LevelAccessor level, BlockPos pos, int depth) {
        if (depth > 20) return;
        if (level.getBlockState(pos).getBlock() != ModBlocks.GODS_DOMAIN_PORTAL.get()) return;
        level.setBlock(pos, net.minecraft.world.level.block.Blocks.AIR.defaultBlockState(), 18);
        for (Direction dir : Direction.values()) {
            clearConnected(level, pos.relative(dir), depth + 1);
        }
    }

    public Direction.Axis getAxis() { return axis; }
    public BlockPos getBottomLeft() { return bottomLeft; }
}
