package de.mki.jchess.server.implementation.threePersonChess.figures;

import de.mki.jchess.server.implementation.threePersonChess.Direction;
import de.mki.jchess.server.implementation.threePersonChess.Hexagon;
import de.mki.jchess.server.model.Chessboard;
import de.mki.jchess.server.model.Client;
import de.mki.jchess.server.model.Figure;
import de.mki.jchess.server.service.RandomStringService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Igor on 12.11.2015.
 */
public class Queen extends Figure<Hexagon> {

    private static Logger logger = LoggerFactory.getLogger(Queen.class);
    List<Direction> directions;

    public Queen(String id, Client client) {
        super(client);
        setId(id);
        setName("Queen");
        directions = Arrays.asList(Direction.DIAGONALTOP, Direction.DIAGONALTOPRIGHT, Direction.DIAGONALBOTTOMRIGHT, Direction.DIAGONALBOTTOM, Direction.DIAGONALBOTTOMLEFT, Direction.DIAGONALTOPLEFT,
                Direction.TOPRIGHT, Direction.RIGHT, Direction.BOTTOMRIGHT, Direction.BOTTOMLEFT, Direction.LEFT, Direction.TOPLEFT);
    }

    public Queen(Client client) {
        this(RandomStringService.getRandomString(), client);
    }

    @Override
    public List<Hexagon> getPossibleMovements(Chessboard chessboard) {
        return new ArrayList<>();
    }

    @Override
    public List<Hexagon> getPossibleSpecialMovements(Chessboard chessboard) {
        // Queen has no special moves
        return new ArrayList<>();
    }

    @Override
    public List<Hexagon> getAttackableFields(Chessboard chessboard) {
        List<Hexagon> output = new ArrayList<>();
        directions.forEach(direction -> {
            Optional<Hexagon> hexagonOptional = getPosition().getNeighbourByDirection(direction);
            while (hexagonOptional.isPresent()) {
                // If it is a diagonal move and the path is blocked, break the loop
                if (direction.getNecessaryFreeDirectionsForDiagonal().isPresent()) {
                    final Optional<Hexagon> finalHexagonOptional1 = hexagonOptional;
                    if (direction.getNecessaryFreeDirectionsForDiagonal().get().stream()
                            .filter(direction1 -> !chessboard.areFieldsOccupied(Collections.singletonList(finalHexagonOptional1.get().getNeighbourByDirection(direction1).get())))
                            .count() == 0)
                        break;
                }

                logger.trace("Checking attackable fields for direction {} from {} to {}", direction, getPosition().getNotation(), hexagonOptional.get().getNotation());
                if (chessboard.areFieldsOccupied(Collections.singletonList(hexagonOptional.get()))) {
                    final Optional<Hexagon> finalHexagonOptional = hexagonOptional;
                    // Check if the occupied field has an enemy figure. If so, the field is indeed attackable
                    if (chessboard.getFigures().stream()
                            .filter(o -> ((Figure) o).getPosition().getNotation().equals(finalHexagonOptional.get().getNotation()))
                            .filter(o -> ((Figure) o).getClient().getId().equals(getClient().getId())).count() == 0) {
                        // It's an enemy figure
                        output.add(hexagonOptional.get());
                    }
                    break;
                } else {
                    output.add(hexagonOptional.get());
                    hexagonOptional = hexagonOptional.get().getNeighbourByDirection(direction);
                }
            }
        });
        return output;
    }

    @Override
    public List<Hexagon> getHypotheticalAttackableFields(Chessboard chessboard) {
        return new ArrayList<>();
    }

}
