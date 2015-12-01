package de.mki.chessboard.implementation.threePersonChess;

import de.mki.chessboard.model.Field;

public class Hexagon extends Field {

    public final int q;
    public final int r;

    public Hexagon(int q, int r) {
        this.q = q;
        this.r = r;
    }

}
