package de.mki.jchess.server.implementation.threePersonChess.figures;

import de.mki.jchess.server.implementation.threePersonChess.Direction;
import de.mki.jchess.server.implementation.threePersonChess.Hexagon;
import de.mki.jchess.server.model.Chessboard;
import de.mki.jchess.server.model.Client;
import de.mki.jchess.server.model.Field;
import de.mki.jchess.server.model.Figure;
import de.mki.jchess.server.service.RandomStringService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of moves with a three person bishop.
 * Created by Igor on 12.11.2015.
 */
public class Bishop extends Figure<Hexagon> {

    private static final Logger logger = LoggerFactory.getLogger(Bishop.class);
    List<Direction> directions;

    /**
     * Constructor with the possibility to pass an own id.
     * @param id The figures id.
     * @param client The owner of the {@link Bishop}.
     */
    public Bishop(String id, Client client) {
        super(client);
        setId(id);
        setName("Bishop");
        directions = Arrays.asList(Direction.DIAGONALTOP, Direction.DIAGONALTOPRIGHT, Direction.DIAGONALBOTTOMRIGHT, Direction.DIAGONALBOTTOM, Direction.DIAGONALBOTTOMLEFT, Direction.DIAGONALTOPLEFT);
    }

    /**
     * Default constructor.
     * @param client The owner of the {@link Bishop}.
     */
    public Bishop(Client client) {
        this(RandomStringService.getRandomString(), client);
    }

    /**
     * {@inheritDoc}
     * @param chessboard The instance of the {@link Chessboard} of the current {@link de.mki.jchess.server.model.Game}
     * @return Returns a {@link List} of {@link Hexagon}.
     */
    @Override
    public List<Hexagon> getPossibleMovements(Chessboard chessboard) {
        List<Hexagon> attackableFields = getAttackableFields(chessboard);
        List<Hexagon> output = new ArrayList<>();
        attackableFields.forEach(hexagon -> {
            // If the field is occupied, lets assume we beat this figure
            if (chessboard.areFieldsOccupied(Collections.singletonList(hexagon))) {
                chessboard.getFigures().stream()
                        .filter(o -> !((Figure) o).getClient().getId().equals(getClient().getId()))
                        .filter(o -> ((Figure) o).getPosition().getNotation().equals(hexagon.getNotation()))
                        .findFirst()
                        .ifPresent(o -> {
                            Figure figure = (Figure) o;
                            figure.setHypotheticalRemoved(true);

                            logger.trace("Found beatable figure (" + figure.getName() + ") at field " + hexagon.getNotation());

                            setHypotheticalPosition(hexagon);
                            try {
                                if (!chessboard.willKingBeChecked(getClient().getId()))
                                    output.add(hexagon);
                            } catch (Exception e) {
                                logger.error("", e);
                            }
                            setHypotheticalPosition(null);
                            figure.setHypotheticalRemoved(null);
                        });
            } else {
                setHypotheticalPosition(hexagon);
                try {
                    if (!chessboard.willKingBeChecked(getClient().getId()))
                        output.add(hexagon);
                } catch (Exception e) {
                    logger.error("", e);
                }
                setHypotheticalPosition(null);
            }
        });
        return output;
    }

    /**
     * {@inheritDoc}
     * @param chessboard The current {@link Chessboard} instance for checking purposes.
     * @return Returns a {@link List} of {@link Hexagon}.
     */
    @Override
    public List<Hexagon> getPossibleSpecialMovements(Chessboard chessboard) {
        // This figure has no special movements
        return new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     * @param chessboard The current {@link Chessboard} instance for checking purposes.
     * @return Returns a {@link List} of {@link Hexagon}.
     */
    @Override
    public List<Hexagon> getAttackableFields(Chessboard chessboard) {
        de.mki.jchess.server.implementation.threePersonChess.Chessboard actualChessboard = (de.mki.jchess.server.implementation.threePersonChess.Chessboard) chessboard;
        List<Hexagon> output = new ArrayList<>();
        directions.forEach(direction -> {
            Optional<Hexagon> hexagonOptional = getPosition().getNeighbourByDirection(direction);
            while (hexagonOptional.isPresent() && (!chessboard.areFieldsOccupied(actualChessboard.getFreeFieldsForDiagonalMove(hexagonOptional.get().getNeighbourByDirection(direction.getOppositeDirection()).get(), direction)))) {
                logger.trace("Checking attackable fields for direction {} from {} to {}", direction, getPosition().getNotation(), hexagonOptional.get().getNotation());
                if (chessboard.areFieldsOccupied(Collections.singletonList(hexagonOptional.get()))) {
                    // Check if the occupied field has an enemy figure. If so, the field is indeed attackable
                    if (actualChessboard.isFigureOwnedByEnemy(hexagonOptional.get(), getClient())) {
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

    /**
     * {@inheritDoc}
     * @param chessboard The current {@link Chessboard} instance for checking purposes.
     * @return Returns a {@link List} of {@link Hexagon}.
     */
    @Override
    public List<Hexagon> getHypotheticalAttackableFields(Chessboard chessboard) {
        de.mki.jchess.server.implementation.threePersonChess.Chessboard actualChessboard = (de.mki.jchess.server.implementation.threePersonChess.Chessboard) chessboard;
        List<Hexagon> output = new ArrayList<>();
        directions.forEach(direction -> {
            Optional<Hexagon> hexagonOptional = getHypotheticalPosition().getNeighbourByDirection(direction);
            // Not nice on the second part to get the reverse direction. But I don't want do add another variable
            while (hexagonOptional.isPresent() && (!chessboard.willFieldsOccupied(actualChessboard.getFreeFieldsForDiagonalMove(hexagonOptional.get().getNeighbourByDirection(direction.getOppositeDirection()).get(), direction)))) {
                logger.trace("Checking attackable fields for direction {} from {} to {}", direction, getHypotheticalPosition().getNotation(), hexagonOptional.get());
                if (chessboard.willFieldsOccupied(Collections.singletonList(hexagonOptional.get()))) {
                    // Check if the occupied field has an enemy figure. If so, the field is indeed attackable
                    if (actualChessboard.isFigureOwnedByEnemy(hexagonOptional.get(), getClient())) {
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
}
