package de.mki.chessboard.model;

import de.mki.chessboard.implementation.threePersonChess.Hexagon;

import java.util.List;

/**
 *
 */
public abstract class Chessboard<T extends Field> {

    int width;
    int height;
    List<T> fields;


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

    public void generateFields(){

    }
    public void drawBoard() {

    }

    public T getClickedField(int x, int y) {
        return null;
    }

}
