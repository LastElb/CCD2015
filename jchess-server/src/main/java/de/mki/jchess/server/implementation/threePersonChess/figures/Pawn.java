package de.mki.jchess.server.implementation.threePersonChess.figures;

import de.mki.jchess.server.implementation.threePersonChess.Hexagon;
import de.mki.jchess.server.implementation.threePersonChess.Direction;
import de.mki.jchess.server.model.Chessboard;
import de.mki.jchess.server.model.Client;
import de.mki.jchess.server.model.Field;
import de.mki.jchess.server.model.Figure;
import de.mki.jchess.server.model.websocket.MovementEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Igor on 12.11.2015.
 */
public class Pawn extends Figure<Hexagon> {

    Direction facingDirection;
    List<Direction> attackableDirections;
    List<Direction> movableDirections;

    public Pawn(String id, Client client, Direction direction) throws Exception {
        super(client);
        setId(id);
        setName("Pawn");
        this.facingDirection = direction;
        switch (facingDirection) {
            case DIAGONALBOTTOM: // White Player
                attackableDirections = Arrays.asList(Direction.DIAGONALBOTTOM, Direction.DIAGONALBOTTOMLEFT, Direction.DIAGONALBOTTOMRIGHT);
                movableDirections = Arrays.asList(Direction.BOTTOMLEFT, Direction.BOTTOMRIGHT);
                break;
            case DIAGONALTOPLEFT: // Grey Player
                attackableDirections = Arrays.asList(Direction.DIAGONALTOP, Direction.DIAGONALTOPLEFT, Direction.DIAGONALBOTTOMLEFT);
                movableDirections = Arrays.asList(Direction.LEFT, Direction.TOPLEFT);
                break;
            case DIAGONALTOPRIGHT: // Black Player
                attackableDirections = Arrays.asList(Direction.DIAGONALTOP, Direction.DIAGONALTOPRIGHT, Direction.DIAGONALBOTTOMRIGHT);
                movableDirections = Arrays.asList(Direction.RIGHT, Direction.TOPRIGHT);
                break;
            default: throw new Exception("Invalid facing direction " + direction.toString() + " for a pawn.");
        }
    }

    @Override
    public List<Hexagon> getPossibleMovements(Chessboard chessboard) {
        return null;
    }

    @Override
    public List<Hexagon> getPossibleSpecialMovements(Chessboard chessboard) {
        // If we did not move our pawn yet we may move 2 fields if the king does not become checked
        boolean wasMoved = chessboard.getParentGame().getGameHistory().stream()
                .filter(historyEntry -> historyEntry.getChessboardEvents().stream()
                        .filter(chessboardEvent -> chessboardEvent instanceof MovementEvent)
                        .map(chessboardEvent -> (MovementEvent) chessboardEvent)
                        .filter(movementEvent -> movementEvent.getFigureId().equals(getId())).count() != 0).findAny().isPresent();
        if (!wasMoved) {

        }
        return null;
    }

    @Override
    public List<Hexagon> getAttackableFields(Chessboard chessboard) {
        List<Hexagon> output = new ArrayList<>();
        attackableDirections.forEach(direction -> {
            Optional<Hexagon> optional = getPosition().getNeighbourByDirection(direction);
            // Check if this neighbour field does exist and if the bordering fields are free
            if (optional.isPresent() && (!chessboard.areFieldsOccupied(direction.getNecessaryFreeDirectionsForDiagonal().get()
                    .stream()
                    .map(freeDirection -> getPosition().getNeighbourByDirection(freeDirection))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList()))))
                output.add(optional.get());
        });
        return output;
    }

    @Override
    public List<Hexagon> getHypotheticalAttackableFields(Chessboard chessboard) {
        return null;
    }
}
