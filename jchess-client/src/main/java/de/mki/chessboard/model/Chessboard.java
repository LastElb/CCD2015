package de.mki.chessboard.model;

import de.mki.chessboard.implementation.threePersonChess.Hexagon;

import java.awt.*;
import java.util.List;

/**
 *
 */
public abstract class Chessboard<T extends Field> {

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

}
