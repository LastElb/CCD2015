package de.mki.chessboard.model;

import jchess.client.models.Figure;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
 */
public abstract class Chessboard<T extends Field> extends JPanel {

    Image image;
    int width;  //correlates with image width
    int height; //correlates with image width
    List<T> fields;
    List<Figure> figures;


    public Chessboard(List<Figure> figures) {
        this.figures = figures;
        this.fields = generateFields();
        this.repaint();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<T> getFields() {
        return fields;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setFields(List<T> fields) {
        this.fields = fields;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public abstract List<T> generateFields();

    public abstract T getClickedField(int x, int y);

    /**
     * Graphics
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawBoard(g2d);
        drawFigures(g2d);
    }

    @Override
    public void update(Graphics g) {
        repaint();
    }

    private void drawBoard(Graphics g2d) {
        g2d.drawImage(this.image, 0, 0, null);
    }

    private void drawFigures(Graphics g) {
        //TODO: draw figures on board
    }
}
