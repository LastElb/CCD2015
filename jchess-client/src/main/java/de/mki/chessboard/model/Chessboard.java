package de.mki.chessboard.model;

import jchess.client.models.Figure;

import static de.mki.chessboard.controller.GraphicsController.loadImage;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
 */
public abstract class Chessboard<T extends Field> extends JPanel {

    Image image;
    int width;  //correlates with image width
    int height; //correlates with image height
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

    public abstract T getField(int x, int y);

    public String getClickedField(int x, int y) {
        return getField(x,y).getNotation();
    }

    public void updateChessboard(List<Figure> figures) {
        this.figures = figures;
        this.repaint();
    }

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

    private void drawBoard(Graphics2D g2d) {
        g2d.drawImage(this.image, 0, 0, null);
    }

    private void drawFigures(Graphics2D g2d) {

        for (Figure figure : figures
                ) {
            Image figureImage = loadImage(figure.getPictureId());
            String position = figure.getPositionObject().getNotation();
            Field field = getFieldByNotation(position);

            /** TODO: implement X and Y in Hexagon, so image can be drawn
             * g2d.drawImage(figureImage, field.getX(), field.getY(), null);
             */

        }
    }

    protected abstract Field getFieldByNotation(String position);
}
