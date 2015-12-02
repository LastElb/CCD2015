package de.mki.chessboard.implementation.threePersonChess;

import de.mki.chessboard.model.Chessboard;

import java.awt.*;
import java.util.ArrayList;

public class Hexboard extends Chessboard {

    int hexWidth;
    int hexHeight;
    Point hexSize;
    Point origin;
    Layout layout;
    public ArrayList<Hexagon> fields = new ArrayList<Hexagon>();

    public Hexboard() {
        this.generateFields();
        this.repaint();

    }

    public int getHexWidth() {
        return hexWidth;
    }

    public int getHexHeight() {
        return hexHeight;
    }

    public Point getHexSize() {
        return hexSize;
    }

    public Point getOrigin() {
        return origin;
    }

    public Layout getChessboardLayout() {
        return layout;
    }

    public void setHexWidth(int hexWidth) {
        this.hexWidth = hexWidth;
    }

    public void setHexHeight(int hexHeight) {
        this.hexHeight = hexHeight;
    }

    public void setHexSize(Point hexSize) {
        this.hexSize = hexSize;
    }

    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    @Override
    public void generateFields() {
        /* Generate Fields */
        // generate right half from x=0 to x=7
        for (int x=0, maxY=12; x<=7; x++, maxY-- ) {
            for (int y=0; y<=maxY; y++) {
                this.fields.add(new Hexagon(x, y));
            }
        }
        //generate left half from x=-1 to x=-5
        for(int x=-1, startY=1; x>=-5; x--,startY++ ) {
            for(int y=startY; y<=12; y++)
                fields.add(new Hexagon(x,y));
        }
    }

    @Override
    public Hexagon getClickedField(int x, int y) {
        return this.layout.pixelToHexagon(new Point(x, y));
    }
}
