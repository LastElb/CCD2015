package jchess;

import java.util.ArrayList;

/**
 *
 */
public class Hexagon {

    public final int q;
    public final int r;
    public final int s;

    public Hexagon(int q, int r) {
        this.q = q;
        this.r = r;
        this.s = -q - r;
    }

    static public Hexagon add(Hexagon a, Hexagon b)
    {
        return new Hexagon(a.q + b.q, a.r + b.r);
    }
    
    static public ArrayList<Hexagon> directions = new ArrayList<Hexagon>() {{
        add(new Hexagon(1, 0));     // right / east
        add(new Hexagon(1, -1));    // top right / northeast
        add(new Hexagon(0, -1));    // top left / northwest
        add(new Hexagon(-1, 0));    // left / west
        add(new Hexagon(-1, 1));    // down left / southwest
        add(new Hexagon(0, 1));     // down right / southeast
    }};

    static public Hexagon direction(int direction) {
        return Hexagon.directions.get(direction);
    }


    static public Hexagon neighbor(Hexagon Hexagon, int direction) {
        return Hexagon.add(Hexagon, Hexagon.direction(direction));
    }

    static public ArrayList<Hexagon> diagonals = new ArrayList<Hexagon>() {{
        add(new Hexagon(2, -1));
        add(new Hexagon(1, -2));
        add(new Hexagon(-1, -1));
        add(new Hexagon(-2, 1));
        add(new Hexagon(-1, 2));
        add(new Hexagon(1, 1));
    }};

    static public Hexagon diagonalNeighbor(Hexagon Hexagon, int direction) {
        return Hexagon.add(Hexagon, Hexagon.diagonals.get(direction));
    }
}
