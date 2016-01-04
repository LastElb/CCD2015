package de.mki.jchess.server.implementation.threePersonChess;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * The direction neighbours of a {@link Hexagon} can be located.
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
    private List<Direction> freeForDiagonalMove;

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

        DIAGONALTOP.freeForDiagonalMove = Arrays.asList(TOPLEFT, TOPRIGHT);
        DIAGONALBOTTOM.freeForDiagonalMove = Arrays.asList(BOTTOMRIGHT, BOTTOMLEFT);
        DIAGONALTOPRIGHT.freeForDiagonalMove = Arrays.asList(TOPRIGHT, RIGHT);
        DIAGONALBOTTOMRIGHT.freeForDiagonalMove = Arrays.asList(RIGHT, BOTTOMRIGHT);
        DIAGONALBOTTOMLEFT.freeForDiagonalMove = Arrays.asList(BOTTOMLEFT, LEFT);
        DIAGONALTOPLEFT.freeForDiagonalMove = Arrays.asList(LEFT, TOPLEFT);
    }

    /**
     * Returns the opposite {@link Direction}.
     * @return Returns the opposite direction as {@link Direction}.
     */
    public Direction getOppositeDirection() {
        return opposite;
    }

    /**
     * Returns the required free {@link Direction}s (where the actual free {@link Hexagon}s are located).
     * @return Returns an {@link Optional} of null or a {@link List} of {@link Direction}.
     */
    public Optional<List<Direction>> getNecessaryFreeDirectionsForDiagonal() {
        return Optional.ofNullable(freeForDiagonalMove);
    }

}
