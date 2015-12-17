package de.mki.chessboard.model;

import jchess.client.models.Figure;

import static de.mki.chessboard.controller.GraphicsController.loadImage;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public abstract class Chessboard<T extends Field> extends JPanel {

    Image image;
    int width;  //correlates with image width
    int height; //correlates with image height
    Map<String, T> fields;
    List<Figure> figures;

    public Chessboard() {
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Map getFields() {
        return fields;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * generates fields and puts figures on the board
     *
     * @param figures
     */
    public abstract void setupBoard(List<Figure> figures);

    /**
     * initialize field objects of chessboard and put them into a map "fields"
     * @return Map with fields
     */
    public abstract Map generateFields();

    /**
     * get a field object (e.g. Hexagon) by its pixel coordinates
     */
    public abstract T getField(int x, int y);

    /**
     * get a field by its chess notation
     */
    protected abstract Field getFieldByNotation(String position);

    /**
     * get the clicked field by x and y pixel coordinates
     *
     * @return clicked field in chess notation
     */
    public String getClickedField(int x, int y) {
        return getField(x,y).getNotation();
    }

    /**
     * push changes to chessboard and repaint it
     * @param figures
     */
    public void updateChessboard(List<Figure> figures) {
        this.figures = figures;
        this.repaint();
    }

    /**
     * Graphics
     */

    /**
     * mainly part of the old project (code) and is needed for rendering graphics
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawBoard(g2d);
        drawFigures(g2d);
    }

    /**
     * also part of the old project (code) and is needed for rendering graphics
     * @param g
     */
    @Override
    public void update(Graphics g) {
        repaint();
    }

    /**
     * draw image of the chessboard
     * @param g2d
     */
    private void drawBoard(Graphics2D g2d) {
        g2d.drawImage(this.image, 0, 0, null);
    }

    /**
     * draw images of the figures onto the board
     * @param g2d
     */
    private void drawFigures(Graphics2D g2d) {

        for (Figure figure : figures) {
            Image figureImage = loadImage(figure.getPictureId());
            String position = figure.getPositionObject().getNotation();
            Field field = getFieldByNotation(position);
            g2d.drawImage(figureImage, field.getX(), field.getY(), null);
            }

    }
}
