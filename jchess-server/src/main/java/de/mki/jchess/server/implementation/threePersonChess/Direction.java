package de.mki.jchess.server.implementation.threePersonChess;

/**
 * Created by Igor on 13.11.2015.
 */
public enum Direction {
    DIAGONALTOP,
    TOPRIGHT,
    DIAGONALTOPRIGHT,
    RIGHT,
    DIAGONALBOTTOMRIGHT,
    BOTTOMRIGHT,
    DIAGONALBOTTOM,
    BOTTOMLEFT,
    DIAGONALBOTTOMLEFT,
    LEFT,
    DIAGONALTOPLEFT,
    TOPLEFT;

    private Direction opposite;

    static {
        RIGHT.opposite = LEFT;
        LEFT.opposite = RIGHT;
        DIAGONALTOP.opposite = DIAGONALBOTTOM;
        DIAGONALBOTTOM.opposite = DIAGONALTOP;
        TOPRIGHT.opposite = BOTTOMLEFT;
        BOTTOMLEFT.opposite = TOPRIGHT;
        DIAGONALTOPRIGHT.opposite = DIAGONALBOTTOMLEFT;
        DIAGONALBOTTOMLEFT.opposite = DIAGONALTOPRIGHT;
        DIAGONALBOTTOMRIGHT.opposite = DIAGONALTOPLEFT;
        DIAGONALTOPLEFT.opposite = DIAGONALBOTTOMRIGHT;
        BOTTOMRIGHT.opposite = TOPLEFT;
        TOPLEFT.opposite = BOTTOMRIGHT;
    }

    public Direction getOppositeDirection() {
        return opposite;
    }
}
