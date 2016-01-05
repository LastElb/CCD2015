package jchess.client.models;

/**
 * Created by Malte on 02.12.2015.
 */
public class Position {
    String notation;

    public String getNotation() {
        return notation.toUpperCase();
    }

    public void setNotation(String notation) {
        this.notation = notation.toUpperCase();
    }
}
