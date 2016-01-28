package de.mki.chessboard.implementation.threePersonChess;

import de.mki.chessboard.model.Field;

/**
 * Class to to implement Hexagons as a form of {@link Field}
 */
public class Hexagon extends Field {

    final int q;
    final int r;

    /**
     * default constructor with axial coordinates
     *
     * @param q
     * @param r
     */
    public Hexagon(int q, int r) {
        this.q = q;
        this.r = r;
    }

    /**
     * get q coordinate
     * @return q
     */
    public int getQ() {
        return q;
    }

    /**
     * get r coordinate
     * @return r
     */
    public int getR() {
        return r;
    }

    /**
     * Get the chess notation of a hexagon
     * @return chess notation as String (Uppercase)
     */
    @Override
    public String getNotation() {

        String notation = "";

        switch (this.r) {
            case 0:
                notation = notation.concat("A");
                notation = notation.concat(String.valueOf(this.q + 1));
                break;

            case 1:
                notation = notation.concat("B");
                notation = notation.concat(String.valueOf(this.q + 2));
                break;

            case 2:
                notation = notation.concat("C");
                notation = notation.concat(String.valueOf(this.q + 3));
                break;

            case 3:
                notation = notation.concat("D");
                notation = notation.concat(String.valueOf(this.q + 4));
                break;

            case 4:
                notation = notation.concat("E");
                notation = notation.concat(String.valueOf(this.q + 5));
                break;

            case 5:
                notation = notation.concat("F");
                notation = notation.concat(String.valueOf(this.q + 6));
                break;

            case 6:
                notation = notation.concat("G");
                notation = notation.concat(String.valueOf(this.q + 7));
                break;

            case 7:
                notation = notation.concat("H");
                notation = notation.concat(String.valueOf(this.q + 8));
                break;

            case 8:
                notation = notation.concat("I");
                notation = notation.concat(String.valueOf(this.q + 9));
                break;

            case 9:
                notation = notation.concat("J");
                notation = notation.concat(String.valueOf(this.q + 10));
                break;

            case 10:
                notation = notation.concat("K");
                notation = notation.concat(String.valueOf(this.q + 11));
                break;

            case 11:
                notation = notation.concat("L");
                notation = notation.concat(String.valueOf(this.q + 12));
                break;

            case 12:
                notation = notation.concat("M");
                notation = notation.concat(String.valueOf(this.q + 13));
                break;

            default:
                notation = notation.concat("Z");
                notation = notation.concat("42");
        }

        return notation;
    }
}
