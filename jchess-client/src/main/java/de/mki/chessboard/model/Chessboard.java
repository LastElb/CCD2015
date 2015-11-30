package de.mki.chessboard.model;

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

    public void generateFields(){

    }
    public void drawBoard() {

    }

}
