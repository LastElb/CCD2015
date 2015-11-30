package de.mki.chessboard.model;

import de.mki.chessboard.implementation.threePersonChess.Hexagon;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
 */
public abstract class Chessboard<T extends Field> extends JPanel {

    /* height and width of the image */
    int width;
    int height;
    List<T> fields;
    Image image;


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

    public void generateFields(){

    }
    public void drawBoard() {

    }

    public T getClickedField(int x, int y) {
        return null;
    }

    /**
     * Graphics
     */
    @Override
    public void paintComponent(Graphics g) {
        System.out.println("someone used paint Component. He used Graphics g=" + g.toString());
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(image, 0, 0, null);
    }

    @Override
    public void update(Graphics g) {
        repaint();
    }
}
