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
 */
public class GodsPortalShape {

    public static final int MIN_WIDTH  = 2;
    public static final int MIN_HEIGHT = 3;
    public static final int MAX_WIDTH  = 21;
    public static final int MAX_HEIGHT = 21;

    private final LevelAccessor level;
    private final Direction.Axis axis;
    private final Direction rightDir;

    private BlockPos bottomLeft;
    private int width;
    private int height;

    public GodsPortalShape(LevelAccessor level, BlockPos startPos, Direction.Axis axis) {
        this.level    = level;
        this.axis     = axis;
        this.rightDir = axis == Direction.Axis.X ? Direction.EAST : Direction.SOUTH;

        BlockPos searchStart = findInteriorStart(startPos);
        if (searchStart != null) {
            this.bottomLeft = findBottomLeft(searchStart);
        }
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

    // ── Interior start resolution ─────────────────────────────────────────

    private BlockPos findInteriorStart(BlockPos pos) {
        if (isAirOrPortal(pos)) return pos;
        for (Direction dir : Direction.values()) {
            BlockPos neighbor = pos.relative(dir);
            if (isAirOrPortal(neighbor)) return neighbor;
        }
        return null;
    }

    // ── Validity ──────────────────────────────────────────────────────────

    public boolean isValid() {
        if (bottomLeft == null) return false;
        if (width  < MIN_WIDTH  || width  > MAX_WIDTH)  return false;
        if (height < MIN_HEIGHT || height > MAX_HEIGHT) return false;
        return hasValidFrame() && interiorIsClean();
    }

    private boolean hasValidFrame() {
        for (int w = -1; w <= width; w++) {
            if (!isFrame(bottomLeft.relative(rightDir, w).below()))       return false;
            if (!isFrame(bottomLeft.relative(rightDir, w).above(height))) return false;
        }
        for (int h = 0; h < height; h++) {
            if (!isFrame(bottomLeft.relative(rightDir, -1).above(h)))     return false;
            if (!isFrame(bottomLeft.relative(rightDir, width).above(h)))  return false;
        }
        return true;
    }

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

    private BlockPos findBottomLeft(BlockPos pos) {
        BlockPos cur = pos;
        int downSteps = 0;
        while (downSteps <= MAX_HEIGHT && isAirOrPortal(cur.below())) {
            cur = cur.below();
            downSteps++;
        }
        int leftSteps = 0;
        while (leftSteps <= MAX_WIDTH && isAirOrPortal(cur.relative(rightDir.getOpposite()))) {
            cur = cur.relative(rightDir.getOpposite());
            leftSteps++;
        }
        if (!isFrame(cur.relative(rightDir.getOpposite()))) return null;
        if (!isFrame(cur.below())) return null;
        return cur;
    }

    private int measureWidth() {
        int w = 0;
        BlockPos cur = bottomLeft;
        while (w <= MAX_WIDTH && isAirOrPortal(cur)) {
            cur = cur.relative(rightDir);
            w++;
        }
        return w;
    }

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
        setFrameActivated(true);
    }

    public static void clearPortalBlocks(LevelAccessor level, BlockPos anyPortalPos) {
        clearConnected(level, anyPortalPos, 0);
        // After clearing portal blocks, deactivate all adjacent marble frame blocks
        deactivateNearbyFrame(level, anyPortalPos);
    }

    private static void clearConnected(LevelAccessor level, BlockPos pos, int depth) {
        if (depth > 500) return;
        if (level.getBlockState(pos).getBlock() != ModBlocks.GODS_DOMAIN_PORTAL.get()) return;
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 18);
        for (Direction dir : Direction.values()) {
            clearConnected(level, pos.relative(dir), depth + 1);
        }
    }

    /**
     * Walks outward from the portal pos and deactivates any CelestialMarble
     * frame blocks it finds nearby (up to 25 blocks away).
     */
    private static void deactivateNearbyFrame(LevelAccessor level, BlockPos origin) {
        int range = 25;
        for (BlockPos p : BlockPos.betweenClosed(
                origin.offset(-range, -range, -range),
                origin.offset(range, range, range))) {
            BlockState s = level.getBlockState(p);
            if (s.getBlock() == ModBlocks.CELESTIAL_MARBLE.get()
                    && s.getValue(CelestialMarbleBlock.ACTIVATED)) {
                level.setBlock(p, s.setValue(CelestialMarbleBlock.ACTIVATED, false), 3);
            }
        }
    }

    /**
     * Activates or deactivates all CelestialMarble blocks that form the frame
     * of this portal shape.
     */
    private void setFrameActivated(boolean activated) {
        // Bottom and top rows (includes corners)
        for (int w = -1; w <= width; w++) {
            setMarbleActivated(bottomLeft.relative(rightDir, w).below(), activated);
            setMarbleActivated(bottomLeft.relative(rightDir, w).above(height), activated);
        }
        // Left and right columns
        for (int h = 0; h < height; h++) {
            setMarbleActivated(bottomLeft.relative(rightDir, -1).above(h), activated);
            setMarbleActivated(bottomLeft.relative(rightDir, width).above(h), activated);
        }
    }

    private void setMarbleActivated(BlockPos pos, boolean activated) {
        BlockState s = level.getBlockState(pos);
        if (s.getBlock() == ModBlocks.CELESTIAL_MARBLE.get()) {
            level.setBlock(pos, s.setValue(CelestialMarbleBlock.ACTIVATED, activated), 3);
        }
    }

    public Direction.Axis getAxis()   { return axis; }
    public BlockPos getBottomLeft()   { return bottomLeft; }
    public int getWidth()             { return width; }
    public int getHeight()            { return height; }
}
