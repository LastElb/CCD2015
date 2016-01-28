package de.mki.chessboard.implementation.threePersonChess;

import de.mki.chessboard.model.Chessboard;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @author Kevin Lamshoeft
 *         General hexagonal Chessboard which is used in 3-Person-Chess.
 */
public class Hexboard extends Chessboard {

    Point hexSize;
    Point origin;
    Layout layout;
    Map<String, Hexagon> fields;

    /**
     * default constructor
     */
    public Hexboard() {
        //better use subclasses and setupBoard()
    }

    /**
     * Set figures, generate (hexagonal) fields and draw the chessboard
     * (including fields and figures)
     * mandatory function for working chessboard
     * @param figures List of figure objects
     */
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

    /**
     * Creates a Map and generates hexagonal fields with axial coordinates
     * @return Map of fields (Hexagon)
     */
    @Override
    public Map generateFields() {
        Map tempFields = new HashMap<>();
        // generate right half from x=0 to x=7
        for (int q = 0, maxR = 12; q <= 7; q++, maxR--) {
            for (int r = 0; r <= maxR; r++) {
                tempFields = insertHexagonInMap(q, r, tempFields);
            }
        }
        //generate left half from x=-1 to x=-5
        for (int q = -1, startR = 1; q >= -5; q--, startR++) {
            for (int r = startR; r <= 12; r++) {
                tempFields = insertHexagonInMap(q, r, tempFields);
            }
        }
        return tempFields;
    }

    /**
     * initialize hexagon, calculate the (pixel) center and put it into the map
     * axial coordinates q and r
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

    /**
     * Get a field (hexagon) by its pixel coordinates
     * @param x
     * @param y
     * @return Hexagon (field)
     */
    @Override
    public Hexagon getField(int x, int y) {
        return this.layout.pixelToHexagon(new Point(x, y));
    }

    /**
     * Get a field by its chess notation
     * @param position String (Chess Notation in Uppercase)
     * @return Hexagon (field)
     */
    @Override
    public Hexagon getFieldByNotation(String position) {
        return fields.get(position);
    }

    /**
     * Highlight possible moves
     * @param fieldsToHighlight List of Strings, containing Field notation e.g. A1
     */
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

    /**
     * Clear highlighted fields of possible moves
     */
    @Override
    public void clearHighlightedFields() {
        this.setPossibleMoves(new ArrayList<Hexagon>());
        this.repaint();
    }

}
