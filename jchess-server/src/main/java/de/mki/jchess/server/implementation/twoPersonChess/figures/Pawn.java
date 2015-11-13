package de.mki.jchess.server.implementation.twoPersonChess.figures;

import de.mki.jchess.server.implementation.twoPersonChess.Direction;
import de.mki.jchess.server.implementation.twoPersonChess.Square;
import de.mki.jchess.server.model.Chessboard;
import de.mki.jchess.server.model.Client;
import de.mki.jchess.server.model.Figure;

import java.util.List;

/**
 * Created by Igor on 12.11.2015.
 */
public class Pawn extends Figure<Square> {

    Direction facingDirection;

    public Pawn(String id, Client client, Direction direction) {
        super(client);
        setId(id);
        setName("Pawn");
        this.facingDirection = direction;
    }

    @Override
    public List<Square> getPossibleMovements(Chessboard chessboard) {
        return null;
    }

    @Override
    public List<Square> getPossibleSpecialMovements(Chessboard chessboard) {
        return null;
    }
}
