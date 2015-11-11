package jchess;

import javax.swing.*;
import java.util.HashMap;

/**
 *
 */
public class Hexboard extends JPanel {

    public int width = 1482;
    public int height = 1314;
    public int hexWidth = 115;
    public int hexHeight = 132;
    public int horizontalSize;
    public int diagonalSize;


    public HashMap map;

    public Hexboard(int width, int height, int hexWidth, int hexHeight, int horizontalSize, int diagonalSize) {
        this.width = width;
        this.height = height;
        this.hexWidth = hexWidth;
        this.hexHeight = hexHeight;
        this.horizontalSize = horizontalSize;
        this.diagonalSize = diagonalSize;

        this.map = createMap(horizontalSize, diagonalSize);
    }

    public HashMap createMap(int horizontalSize, int diagonalSize) {
        for (int q = -5; q <= 7; q++) {
            for (int r = 0; r <= 12; r++) {
                this.map.put("" + q + r, new Hexagon(q, r));
            }
        }
    }
}
