package jchess;

import java.util.ArrayList;

/**
 *
 */
public class Hexagon {

    public final int q;
    public final int r;

    public Hexagon(int q, int r) {
        this.q = q;
        this.r = r;
    }

    static public Hexagon add(Hexagon a, Hexagon b) {
        return new Hexagon(a.q + b.q, a.r + b.r);
    }

    public enum Directions {
        RIGHT(new Hexagon(1, 0)),
        TOP_RIGHT(new Hexagon(1, -1)),
        TOP_LEFT(new Hexagon(0, -1)),
        LEFT(new Hexagon(-1, 0)),
        DOWN_LEFT(new Hexagon(-1, 1)),
        DOWN_RIGHT(new Hexagon(0, 1));

        Hexagon hexagon;

        Directions(Hexagon h) {
            this.hexagon = h;
        }
    }

    public Hexagon neighbour(Directions d) {
        return add(this, d.hexagon);
    }

    public enum Diagonals {
        DIAGONAL_TOP_RIGHT(new Hexagon(2, -1)),
        DIAGONAL_TOP_STRAIGHT(new Hexagon(1, -2)),
        DIAGONAL_TOP_LEFT(new Hexagon(-1, -1)),
        DIAGONAL_DOWN_LEFT(new Hexagon(-2, 1)),
        DIAGONAL_DOWN_STRAIGHT(new Hexagon(-1, 2)),
        DIAGONAL_DOWN_RIGHT(new Hexagon(1, 1));

        Hexagon hexagon;

        Diagonals(Hexagon hexagon) {
            this.hexagon = hexagon;
        }
    }

    public Hexagon diagonalNeighbour(Diagonals d) {
        return add(this, d.hexagon);
    }

}

