package de.mki.chessboard.model;

public abstract class Field {

    /**
     * x and y define the center of a field in pixel.
     */
    int x;
    int y;
    String notation;

    public String getNotation() {
        return notation;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

}
