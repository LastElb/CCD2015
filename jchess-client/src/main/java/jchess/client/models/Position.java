package jchess.client.models;

/**
 * Model of the Position
 * @author Malte
 */
public class Position {
    String notation;

    /**
     * Get the position in chess notation e.g. A4
     * @return notation, always uppercase
     */
    public String getNotation() {
        return notation.toUpperCase();
    }

    /**
     * Set the position in chess notation. Notation always gets converted to uppercase.
     * @param notation
     */
    public void setNotation(String notation) {
        this.notation = notation.toUpperCase();
    }
}
