package de.mki.chessboard.implementation.threePersonChess;

import de.mki.chessboard.model.Chessboard;
import de.mki.chessboard.model.Field;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Hexboard extends Chessboard {

    int hexWidth;
    int hexHeight;
    Point hexSize;
    Point origin;
    Layout layout;
    ArrayList<Hexagon> fields;

    public Hexboard(List list) {
        super(list);
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


    public ArrayList<Hexagon> generateFields() {
        /* Generate Fields */
        ArrayList<Hexagon> fields = new ArrayList<Hexagon>();
        // generate right half from x=0 to x=7
        for (int x=0, maxY=12; x<=7; x++, maxY-- ) {
            for (int y=0; y<=maxY; y++) {
                fields.add(new Hexagon(x, y));
            }
        }
        //generate left half from x=-1 to x=-5
        for(int x=-1, startY=1; x>=-5; x--,startY++ ) {
            for(int y=startY; y<=12; y++)
                fields.add(new Hexagon(x,y));
        }
        return fields;
    }

    @Override
    public Hexagon getClickedField(int x, int y) {
        return this.layout.pixelToHexagon(new Point(x, y));
    }

    @Override
    protected Hexagon getFieldByNotation(String position) {
        // TODO: implement getting the right hexagon by notation
        return new Hexagon(0, 0);
    }
}
