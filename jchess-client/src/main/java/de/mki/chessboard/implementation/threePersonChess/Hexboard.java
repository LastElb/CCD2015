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
        Map fields = new HashMap<>();
        // generate right half from x=0 to x=7
        for (int q = 0, maxR = 12; q <= 7; q++, maxR--) {
            for (int r = 0; r <= maxR; r++) {
                insertHexagonInMap(q, r);
            }
        }
        //generate left half from x=-1 to x=-5
        for (int q = -1, startR = 1; q >= -5; q--, startR++) {
            for (int r = startR; r <= 12; r++) {
                insertHexagonInMap(q, r);
            }
        }
        return fields;
    }

    private void insertHexagonInMap(int q, int r) {
        Hexagon field = new Hexagon(q, r);
        Point center = layout.hexagonToPixel(field);
        field.setX(center.x);
        field.setY(center.y);
        fields.put(field.getNotation(), field);
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
