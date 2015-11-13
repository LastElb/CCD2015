package de.mki.jchess.server.implementation.threePersonChess;

import de.mki.jchess.server.model.Figure;

/**
 * Created by Igor on 13.11.2015.
 */
public class Chessboard extends de.mki.jchess.server.model.Chessboard<Hexagon> {

    public Hexagon getFieldByNotation(int x, int y) throws Exception {
        return fields.stream().filter(field -> field.column == x && field.row == y).findFirst().orElseThrow(() -> new Exception("Notation not found"));
    }

    @Override
    public boolean isKingCheckedAtPosition(Figure<Hexagon> king, Hexagon field) {
        return false;
    }
}
