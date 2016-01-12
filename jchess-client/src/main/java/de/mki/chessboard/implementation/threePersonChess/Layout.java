package de.mki.chessboard.implementation.threePersonChess;

import java.awt.*;

/**
 * Class with represents the layout of a hexagonal chessboard.
 */
public class Layout {

    Orientation orientation;
    double sizeX;
    double sizeY;
    Point origin;
    public static final Orientation pointy = new Orientation(Math.sqrt(3.0), Math.sqrt(3.0) / 2.0, 0.0, 3.0 / 2.0, Math.sqrt(3.0) / 3.0, -1.0 / 3.0, 0.0, 2.0 / 3.0, 0.5);
    public static final Orientation flat = new Orientation(3.0 / 2.0, 0.0, Math.sqrt(3.0) / 2.0, Math.sqrt(3.0), 2.0 / 3.0, 0.0, -1.0 / 3.0, Math.sqrt(3.0) / 3.0, 0.0);

    public Layout(Orientation orientation, double sizeX, double sizeY, Point origin)
    {
        this.orientation = orientation;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.origin = origin;
    }


    public Point hexagonToPixel(Hexagon h) {
        Orientation M = this.orientation;
        double sizeX = this.sizeX;
        double sizeY = this.sizeY;
        Point origin = this.origin;
        double x = (M.f0 * h.q + M.f1 * h.r) * sizeX;
        double y = (M.f2 * h.q + M.f3 * h.r) * sizeY;
        return new Point((int) x + origin.x, (int) y + origin.y);
    }


    public Hexagon pixelToHexagon(Point p)
    {
        Orientation M = this.orientation;
        Point origin = this.origin;
        double sizeX = this.sizeX;
        double sizeY = this.sizeY;

        double diffX = (p.x - origin.x) / sizeX;
        double diffY = (p.y - origin.y) / sizeY;
        double q = M.b0 * diffX + M.b1 * diffY;
        double r = M.b2 * diffX + M.b3 * diffY;
        double s = -q -r;
        return roundToNextHexagon(q,r,s);
    }

    private Hexagon roundToNextHexagon(double q_, double r_, double s_) {

        int q = (int)(Math.round(q_));
        int r = (int)(Math.round(r_));
        int s = (int)(Math.round(s_));

        double q_diff = Math.abs(q - q_);
        double r_diff = Math.abs(r - r_);
        double s_diff = Math.abs(s - s_);

        if (q_diff > r_diff && q_diff > s_diff)
        {
            q = -r - s;
        }
        else
        if (r_diff > s_diff)
        {
            r = -q - s;
        }
        else
        {
            s = -q - r;
        }
        return new Hexagon(q,r);
    }
}
