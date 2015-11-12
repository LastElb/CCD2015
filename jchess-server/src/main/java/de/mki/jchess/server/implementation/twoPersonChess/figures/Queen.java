package de.mki.jchess.server.implementation.twoPersonChess.figures;

import de.mki.jchess.server.implementation.twoPersonChess.Square;
import de.mki.jchess.server.model.Chessboard;
import de.mki.jchess.server.model.Client;
import de.mki.jchess.server.model.Figure;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Igor on 12.11.2015.
 */
public class Queen extends Figure<Square> {
    public Queen(String id, Client client) {
        super(client);
        setId(id);
        setName("Queen");
    }

    @Override
    public List<Square> getPossibleMovements(Chessboard chessboard) {
        return null;
    }

}
