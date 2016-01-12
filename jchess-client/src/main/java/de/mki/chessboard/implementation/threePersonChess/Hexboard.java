package de.mki.chessboard.implementation.threePersonChess;

import de.mki.chessboard.model.Chessboard;
import de.mki.chessboard.model.Field;
import jchess.client.models.Figure;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Hexboard extends Chessboard {

    Point hexSize;
    Point origin;
    Layout layout;
    Map<String, Hexagon> fields;

    public Hexboard() {
    }

    @Override
    public void setupBoard(List figures) {
        this.setFigures(figures);
        this.fields = generateFields();
        this.repaint();
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
    public Map generateFields() {
        Map fields = new HashMap<>();
        // generate right half from x=0 to x=7
        for (int q = 0, maxR = 12; q <= 7; q++, maxR--) {
            for (int r = 0; r <= maxR; r++) {
                fields = insertHexagonInMap(q, r, fields);
            }
        }
        //generate left half from x=-1 to x=-5
        for (int q = -1, startR = 1; q >= -5; q--, startR++) {
            for (int r = startR; r <= 12; r++) {
                fields = insertHexagonInMap(q, r, fields);
            }
        }
        return fields;
    }

    /**
     * initialize hexagon, calculate the (pixel) center and put it into the map
     *
     * @param q
     * @param r
     * @param fields
     * @return Map of fields
     */
    private Map insertHexagonInMap(int q, int r, Map fields) {
        Hexagon field = new Hexagon(q, r);
        Point center = this.layout.hexagonToPixel(field);
        field.setX(center.x);
        field.setY(center.y);
        fields.put(field.getNotation(), field);
        return fields;
    }

    @Override
    public Hexagon getField(int x, int y) {
        return this.layout.pixelToHexagon(new Point(x, y));
    }

    @Override
    public Hexagon getFieldByNotation(String position) {
        return fields.get(position);
    }

    @Override
    public void highlightFieldsByNotation(List fieldsToHighlight) {
        List<Hexagon> possibleMoves = new ArrayList<Hexagon>();
        for (Object position : fieldsToHighlight) {
            Hexagon field = getFieldByNotation(position.toString().toUpperCase());
            possibleMoves.add(field);
        }
        this.setPossibleMoves(possibleMoves);
        this.repaint();
    }

    @Override
    public void clearHighlightedFields() {
        this.setPossibleMoves(new ArrayList<Hexagon>());
        this.repaint();
    }

}
