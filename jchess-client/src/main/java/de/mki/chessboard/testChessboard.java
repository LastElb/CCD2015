package de.mki.chessboard;

import de.mki.chessboard.implementation.threePersonChess.Hexagon;
import de.mki.chessboard.implementation.threePersonChess.Hexboard;

/**
 * Created by Ikarios on 28.11.15.
 */
public class testChessboard {
    public static void main(String[] args) {
        Hexboard hexboard = new Hexboard();

        for (Hexagon hex: hexboard.fields
             ) {
            System.out.println(""+hex.q + hex.r);
        }

    }
}
