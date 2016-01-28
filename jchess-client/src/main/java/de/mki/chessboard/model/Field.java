package de.mki.chessboard.model;

/**
 * {@author Kevin Lamshoeft}
 * Abstract Class which represents a field of a {@link Chessboard}.
 */
public abstract class Field {

    /**
     * x and y define the center of a field in pixel.
     */
    int x;
    int y;
    String notation;

    /**
     * get chess notation of the field (uppercase)
     *
     * @return
     */
    public String getNotation() {
        return notation;
    }

    /**
     * get x coordinate
     * @return int x
     */
    public int getX() {
        return x;
    }

    /**
     * get y coordinate
     * @return int y
     */
    public int getY() {
        return y;
    }

    /**
     * set x coordinate
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * set y coordinate
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

}
