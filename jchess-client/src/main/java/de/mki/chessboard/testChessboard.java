package de.mki.chessboard;

import de.mki.chessboard.implementation.threePersonChess.Hexagon;
import de.mki.chessboard.implementation.threePersonChess.Hexboard;
import de.mki.chessboard.implementation.threePersonChess.smallHexboard;
import jchess.client.models.Figure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ikarios on 28.11.15.
 */
public class testChessboard {
    public static void main(String[] args) {
        Hexboard hexboard = new smallHexboard(new ArrayList<Figure>());
        List<Hexagon> fields = hexboard.getFields();

        for (Hexagon hex : fields
             ) {
            System.out.println("q:" + hex.q + " | r:" + hex.r + " = " + hex.getNotation());
        }

        System.out.println(hexboard.getClickedField(100,20));
    }
}
