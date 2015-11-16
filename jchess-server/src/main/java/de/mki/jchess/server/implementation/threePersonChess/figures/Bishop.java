package de.mki.jchess.server.implementation.threePersonChess.figures;

import de.mki.jchess.server.implementation.threePersonChess.Hexagon;
import de.mki.jchess.server.implementation.twoPersonChess.Square;
import de.mki.jchess.server.model.Chessboard;
import de.mki.jchess.server.model.Client;
import de.mki.jchess.server.model.Figure;

import java.util.List;

/**
 * Created by Igor on 12.11.2015.
 */
public class Bishop extends Figure<Hexagon> {
    public Bishop(String id, Client client) {
        super(client);
        setId(id);
        setName("Bishop");
    }

    @Override
    public List<Hexagon> getPossibleMovements(Chessboard chessboard) {
        return null;
    }

    @Override
    public List<Hexagon> getPossibleSpecialMovements(Chessboard chessboard) {
        return null;
    }

    @Override
    public List<Hexagon> getAttackableFields() {
        return null;
    }

}
