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
public class King extends Figure<Square> {
    public King(String id, Client client) {
        super(client);
        setId(id);
        setName("King");
    }

    @Override
    public List<Square> getPossibleMovements(Chessboard chessboard) {
        return null;
//        List<Square> output = new ArrayList<>();
//        output.addAll(getPosition().getNeighbours().stream().map(Square.SquareNeighbourModel::getSquare).collect(Collectors.toList()));
//        return output;
    }
}
