package de.mki.jchess.server.implementation.twoPersonChess;

/**
 * Created by Igor on 11.11.2015.
 */
public class Chessboard extends de.mki.jchess.server.model.Chessboard<Square> {

    public Square getFieldByNotation(int x, int y) throws Exception {
        return fields.stream().filter(field -> field.x == x && field.y == y).findFirst().orElseThrow(() -> new Exception("Notation not found"));
    }

}
