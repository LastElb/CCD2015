package de.mki.chessboard.model;

/**
 *
 */
public abstract class Field {

    /**
     * x and y define the center of a field in pixel.
     */
    int x;
    int y;

    public abstract String getNotation();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
