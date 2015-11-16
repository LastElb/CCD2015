package de.mki.jchess.server.implementation.twoPersonChess.figures;

import de.mki.jchess.server.implementation.twoPersonChess.Square;
import de.mki.jchess.server.model.Chessboard;
import de.mki.jchess.server.model.Client;
import de.mki.jchess.server.model.Figure;

import java.util.List;

/**
 * Created by Igor on 12.11.2015.
 */
public class Rook extends Figure<Square> {
    public Rook(String id, Client client) {
        super(client);
        setId(id);
        setName("Rook");
    }

    @Override
    public List<Square> getPossibleMovements(Chessboard chessboard) {
        return null;
    }

    @Override
    public List<Square> getPossibleSpecialMovements(Chessboard chessboard) {
        return null;
    }

    @Override
    public List<Square> getAttackableFields(Chessboard chessboard) {
        return null;
    }

}
