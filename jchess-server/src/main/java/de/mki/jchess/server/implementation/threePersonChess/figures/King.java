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

/**
 * Implementation of moves with a three person king.
 * Created by Igor on 12.11.2015.
 */
public class King extends Figure<Hexagon> {

    private static final Logger logger = LoggerFactory.getLogger(King.class);
    List<Direction> directions;

    /**
     * Constructor with the possibility to pass an own id.
     * @param id The figures id.
     * @param client The owner of the {@link King}.
     */
    public King(String id, Client client) {
        super(client);
        setId(id);
        setName("King");

        directions = Arrays.asList(Direction.DIAGONALTOP, Direction.DIAGONALTOPRIGHT, Direction.DIAGONALBOTTOMRIGHT, Direction.DIAGONALBOTTOM, Direction.DIAGONALBOTTOMLEFT, Direction.DIAGONALTOPLEFT,
                Direction.TOPRIGHT, Direction.RIGHT, Direction.BOTTOMRIGHT, Direction.BOTTOMLEFT, Direction.LEFT, Direction.TOPLEFT);
    }

    /**
     * Default constructor
     * @param client The owner of the {@link King}.
     */
    public King(Client client) {
        this(RandomStringService.getRandomString(), client);
    }

    /**
     * {@inheritDoc}
     * @param chessboard The instance of the {@link Chessboard} of the current {@link de.mki.jchess.server.model.Game}.
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
                            logger.trace("Found beatable figure (" + figure.getName() + ") at field " + hexagon.getNotation());

                            figure.setHypotheticalRemoved(true);
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
     * Implements castling
     * @param chessboard The current {@link Chessboard} instance for checking purposes.
     * @return Returns a {@link List} of {@link Hexagon}.
     */
    @Override
    public List<Hexagon> getPossibleSpecialMovements(Chessboard chessboard) {
        // TODO: Implement Castling
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
            getPosition().getNeighbourByDirection(direction).ifPresent(hexagon -> {
                if (direction.getNecessaryFreeDirectionsForDiagonal().isPresent() && actualChessboard.areFieldsOccupied(actualChessboard.getFreeFieldsForDiagonalMove(hexagon.getNeighbourByDirection(direction.getOppositeDirection()).get(), direction))) {
                    logger.trace("Stopping from {} to {}. Diagonal movement fields are not free {}.", getPosition().getNotation(), hexagon.getNotation(), actualChessboard.getFreeFieldsForDiagonalMove(hexagon, direction).toString());
                    return;
                }
                if (chessboard.areFieldsOccupied(Collections.singletonList(hexagon))) {
                    // Check if the occupied field has an enemy figure. If so, the field is indeed attackable
                    if (actualChessboard.isFigureOwnedByEnemy(hexagon, getClient())) {
                        // It's an enemy figure
                        output.add(hexagon);
                    }
                    logger.trace("Stopping from {} to {}. Field is occupied. Switching to next direction.", getPosition().getNotation(), hexagon.getNotation());
                } else {
                    output.add(hexagon);
                }
            });
        });
        return output;
    }

    /**
     *
     * @param chessboard The current {@link Chessboard} instance for checking purposes.
     * @return Returns a {@link List} of {@link Hexagon}.
     */
    @Override
    public List<Hexagon> getHypotheticalAttackableFields(Chessboard chessboard) {
        de.mki.jchess.server.implementation.threePersonChess.Chessboard actualChessboard = (de.mki.jchess.server.implementation.threePersonChess.Chessboard) chessboard;
        List<Hexagon> output = new ArrayList<>();
        directions.forEach(direction -> {
            getHypotheticalPosition().getNeighbourByDirection(direction).ifPresent(hexagon -> {
                if (direction.getNecessaryFreeDirectionsForDiagonal().isPresent() && actualChessboard.willFieldsBeOccupied(actualChessboard.getFreeFieldsForDiagonalMove(hexagon.getNeighbourByDirection(direction.getOppositeDirection()).get(), direction))) {
                    logger.trace("Stopping from {} to {}. Diagonal movement fields are not free {}.", getPosition().getNotation(), hexagon.getNotation(), actualChessboard.getFreeFieldsForDiagonalMove(hexagon, direction).toString());
                    return;
                }
                if (chessboard.willFieldsBeOccupied(Collections.singletonList(hexagon))) {
                    // Check if the occupied field has an enemy figure. If so, the field is indeed attackable
                    if (actualChessboard.isFigureOwnedByEnemy(hexagon, getClient())) {
                        // It's an enemy figure
                        output.add(hexagon);
                    }
                    logger.trace("Stopping from {} to {}. Field is occupied. Switching to next direction.", getPosition().getNotation(), hexagon.getNotation());
                } else {
                    output.add(hexagon);
                }
            });
        });
        return output;
    }
}
