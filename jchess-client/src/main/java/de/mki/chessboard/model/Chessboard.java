package de.mki.chessboard.model;

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
    // TODO: List<figures> figures;


    public Chessboard() {

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

    public abstract void generateFields();

    public abstract T getClickedField(int x, int y);

    /**
     * Graphics
     */
    @Override
    public void paintComponent(Graphics g) {
        drawBoard(g);
        drawFigures(g);
    }

    @Override
    public void update(Graphics g) {
        repaint();
    }

    private void drawBoard(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(image, 0, 0, null);
    }

    private void drawFigures(Graphics g) {
        //TODO: draw figures on board
    }
}
