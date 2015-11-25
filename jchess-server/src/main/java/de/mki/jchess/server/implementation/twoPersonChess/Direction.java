package de.mki.jchess.server.implementation.twoPersonChess;

/**
 * Created by Igor on 11.11.2015.
 */
public enum Direction {
    TOP,
    TOPRIGHT,
    RIGHT,
    BOTTOMRIGHT,
    BOTTOM,
    BOTTOMLEFT,
    LEFT,
    TOPLEFT;

    private Direction opposite;

    static {
        TOP.opposite = BOTTOM;
        BOTTOM.opposite = TOP;
        TOPRIGHT.opposite = BOTTOMLEFT;
        BOTTOMLEFT.opposite = TOPRIGHT;
        RIGHT.opposite = LEFT;
        LEFT.opposite = RIGHT;
        BOTTOMRIGHT.opposite = TOPLEFT;
        TOPLEFT.opposite = BOTTOMRIGHT;
    }

    public Direction getOppositeDirection() {
        return opposite;
    }
}
