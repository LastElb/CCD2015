package de.mki.chessboard.model;

import jchess.client.models.Figure;

import static de.mki.chessboard.controller.GraphicsController.loadImage;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Class which represents the Chessboard. Consists of Field objects like Hexagons or Squares.
 * Contains fields as map and a list of figures. Also implements logic to draw itself.
 *
 * @param <T> Field
 */
public abstract class Chessboard<T extends Field> extends JPanel {

    transient Image image;
    int widthOfChessboard;  //correlates with image widthOfChessboard
    int heightOfChessboard; //correlates with image heightOfChessboard
    transient Map<String, T> fields;
    transient List<Figure> figures;
    private boolean chessboardBlocked;
    Point pixelCorrection;  // correction of position where figures are drawn in pixels
    Point getPixelCorrectionMoves; // correction of position where possible moves are drawn in pixels
    transient List<Field> possibleMoves = new ArrayList<>();

    public Chessboard() {
        figures = new ArrayList<>();
        fields = new LinkedHashMap<>();
    }

    @Override
    public int getWidth() {
        return widthOfChessboard;
    }

    @Override
    public int getHeight() {
        return heightOfChessboard;
    }

    public Map getFields() {
        return fields;
    }

    public void setWidth(int width) {
        this.widthOfChessboard = width;
    }

    public void setHeight(int height) {
        this.heightOfChessboard = height;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public List<Figure> getFigures() {
        return figures;
    }

    public void setFigures(List<Figure> figures) {
        this.figures = figures;
    }

    public void setPixelCorrection(Point pixelCorrection) {
        this.pixelCorrection = pixelCorrection;
    }

    public void setGetPixelCorrectionMoves(Point getPixelCorrectionMoves) {
        this.getPixelCorrectionMoves = getPixelCorrectionMoves;
    }

    public void setPossibleMoves(List<Field> possibleMoves) {
        this.possibleMoves = possibleMoves;
    }

    /**
     * generates fields and puts figures on the board
     * @param figures List of figure objects
     */
    public abstract void setupBoard(List<Figure> figures);

    /**
     * initialize field objects of chessboard and put them into a map "fields"
     * @return Map with fields
     */
    public abstract Map generateFields();

    /**
     * get a field object (e.g. Hexagon) by its pixel coordinates
     * @return Field (e.g. Hexagon)
     */
    public abstract T getField(int x, int y);

    /**
     * get a field by its chess notation
     * «
     * @return Field Object
     */
    public abstract Field getFieldByNotation(String position);

    /**
     * get the clicked field by x and y pixel coordinates
     *
     * @return clicked field in chess notation (as String)
     */
    public String getClickedField(int x, int y) {
        return getField(x,y).getNotation();
    }

    /**
     * Check whether the chessboard is blocked for any actions
     * @return boolean
     */
    public boolean isBlocked() {
        return chessboardBlocked;
    }

    /**
     * Block chessboard for actions
     */
    public void block() {
        this.chessboardBlocked = true;
    }

    /**
     * Unblock Chessboard for actions
     */
    public void unblock() {
        this.chessboardBlocked = false;
    }

    /**
     * push changes to chessboard and repaint it
     * @param figures List of figure objects
     */
    public void updateChessboard(List<Figure> figures) {
        this.figures = figures;
        this.repaint();
    }

    /**
     * Mark the given fields with a red dot
     * @param fieldsToHighlight List of Strings, containing Field notation e.g. A1
     */
    public abstract void highlightFieldsByNotation(List<String> fieldsToHighlight);

    /**
     * Redraw the chessboard without marks for possible movements
     */
    public abstract void clearHighlightedFields();

    /**
     * Graphics
     */

    /**
     * mainly part of the old project (code) and is needed for rendering graphics
     * @param g Graphics
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawBoard(g2d);
        drawFigures(g2d);
        drawPossibleMoves(g2d);
    }

    /**
     * also part of the old project (code) and is needed for rendering graphics
     * @param g Graphics
     */
    @Override
    public void update(Graphics g) {
        repaint();
    }

    /**
     * draw image of the chessboard
     * @param g2d Graphics2D
     */
    private void drawBoard(Graphics2D g2d) {
        g2d.drawImage(this.image, 0, 0, null);
    }

    /**
     * draw images of the figures onto the board
     * @param g2d Graphics2D
     */
    private void drawFigures(Graphics2D g2d) {
        for (Figure tempFigure : this.figures) {
            if(!tempFigure.getRemoved()) {
                Image figureImage = loadImage(tempFigure.getPictureId() + ".png");
                String position = tempFigure.getPositionObject().getNotation();
                Field field = getFieldByNotation(position.toUpperCase());
                int x = field.getX() - this.pixelCorrection.x;
                int y = field.getY() - this.pixelCorrection.y;
                g2d.drawImage(figureImage, x, y, null);
            }
        }
    }

    /**
     * draw possible moves on chessboard
     * @param g2d Graphics2D
     */
    private void drawPossibleMoves(Graphics2D g2d) {
        Image moveImage = loadImage("able_square.png");
        for (Field tempField : this.possibleMoves) {
            int x = tempField.getX() - this.getPixelCorrectionMoves.x;
            int y = tempField.getY() - this.getPixelCorrectionMoves.y;
            g2d.drawImage(moveImage, x, y, null);
        }
    }
}
