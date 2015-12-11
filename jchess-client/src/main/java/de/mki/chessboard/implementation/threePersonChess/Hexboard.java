package de.mki.chessboard.implementation.threePersonChess;

import de.mki.chessboard.model.Chessboard;
import jchess.client.models.Figure;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Hexboard extends Chessboard {

    int hexWidth;
    int hexHeight;
    Point hexSize;
    Point origin;
    Layout layout;
    Map<String, Hexagon> fields;

    public Hexboard(List<Figure> figures) {
        super(figures);
        fields = this.generateFields();
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


    public Map generateFields() {
        /* Generate Fields */
        Map fields = new HashMap<>();
        // generate right half from x=0 to x=7
        for (int x=0, maxY=12; x<=7; x++, maxY-- ) {
            for (int y=0; y<=maxY; y++) {
                Hexagon field = new Hexagon(x, y);
                fields.put(field.getNotation(), field);
            }
        }
        //generate left half from x=-1 to x=-5
        for(int x=-1, startY=1; x>=-5; x--,startY++ ) {
            for (int y = startY; y <= 12; y++) {
                Hexagon field = new Hexagon(x, y);
                fields.put(field.getNotation(), field);
            }
        }
        return fields;
    }

    @Override
    public Hexagon getField(int x, int y) {
        return this.layout.pixelToHexagon(new Point(x, y));
    }

    @Override
    protected Hexagon getFieldByNotation(String position) {
        return fields.get(position);
    }
}
