package de.mki.jchess.server.implementation.threePersonChess.figures;

import de.mki.jchess.server.implementation.threePersonChess.Direction;
import de.mki.jchess.server.implementation.threePersonChess.Hexagon;
import de.mki.jchess.server.model.Chessboard;
import de.mki.jchess.commons.Client;
import de.mki.jchess.server.model.Figure;
import de.mki.jchess.commons.RandomStringService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Implementation of moves with a three person queen.
 * Created by Igor on 12.11.2015.
 */
public class Queen extends Figure<Hexagon> {

    private static final Logger logger = LoggerFactory.getLogger(Queen.class);
    List<Direction> directions;

    /**
     * Constructor with the possibility to pass an own id.
     * @param id The figures id.
     * @param client The owner of the {@link Queen}.
     */
    public Queen(String id, Client client) {
        super(client);
        setId(id);
        setName("Queen");
        directions = Arrays.asList(Direction.DIAGONALTOP, Direction.DIAGONALTOPRIGHT, Direction.DIAGONALBOTTOMRIGHT, Direction.DIAGONALBOTTOM, Direction.DIAGONALBOTTOMLEFT, Direction.DIAGONALTOPLEFT,
                Direction.TOPRIGHT, Direction.RIGHT, Direction.BOTTOMRIGHT, Direction.BOTTOMLEFT, Direction.LEFT, Direction.TOPLEFT);
    }

    /**
     * Default constructor.
     * @param client The owner of the {@link Queen}.
     */
    public Queen(Client client) {
        this(RandomStringService.getRandomString(), client);
    }

    /**
     * {@inheritDoc}
     * @param chessboard The instance of the {@link Chessboard} of the current {@link de.mki.jchess.server.model.Game}
     * @return Returns a {@link List} of {@link Hexagon}s.
     */
    @Override
    public List<Hexagon> getPossibleMovements(Chessboard chessboard) {
        List<Hexagon> attackableFields = getAttackableFields(chessboard);
        List<Hexagon> output = new ArrayList<>();
        FigureUtils.evaluatePossibleMovements(this, attackableFields, (de.mki.jchess.server.implementation.threePersonChess.Chessboard) chessboard, getClient(), output);
        return output;
    }

    /**
     * {@inheritDoc}
     * The {@link Queen} has no special moves.
     * @param chessboard The current {@link Chessboard} instance for checking purposes.
     * @return Returns an empty {@link List} of {@link Hexagon}s.
     */
    @Override
    public List<Hexagon> getPossibleSpecialMovements(Chessboard chessboard) {
        // Queen has no special moves
        return new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     * @param chessboard The current {@link Chessboard} instance for checking purposes.
     * @return Returns a {@link List} of {@link Hexagon}s.
     */
    @Override
    public List<Hexagon> getAttackableFields(Chessboard chessboard) {
        de.mki.jchess.server.implementation.threePersonChess.Chessboard actualChessboard = (de.mki.jchess.server.implementation.threePersonChess.Chessboard) chessboard;
        List<Hexagon> output = new ArrayList<>();
        directions.forEach(direction -> {
            Optional<Hexagon> hexagonOptional = getPosition().getNeighbourByDirection(direction);
            while (hexagonOptional.isPresent()) {
                logger.trace("Checking attackable fields for direction {} from {} to {}", direction, getPosition().getNotation(), hexagonOptional.get().getNotation());
                // If it is a diagonal move and the path is blocked, break the loop
                if (direction.getNecessaryFreeDirectionsForDiagonal().isPresent() && actualChessboard.areFieldsOccupied(actualChessboard.getFreeFieldsForDiagonalMove(hexagonOptional.get().getNeighbourByDirection(direction.getOppositeDirection()).get(), direction))) {
                    logger.trace("Stopping from {} to {}. Diagonal movement fields are not free {}. Switching to next direction.", getPosition().getNotation(), hexagonOptional.get().getNotation(), actualChessboard.getFreeFieldsForDiagonalMove(hexagonOptional.get(), direction).toString());
                    break;
                }
                if (chessboard.areFieldsOccupied(Collections.singletonList(hexagonOptional.get()))) {
                    // Check if the occupied field has an enemy figure. If so, the field is indeed attackable
                    if (actualChessboard.isFigureOwnedByEnemy(hexagonOptional.get(), getClient())) {
                        // It's an enemy figure
                        output.add(hexagonOptional.get());
                    }
                    logger.trace("Stopping from {} to {}. Field is occupied. Switching to next direction.", getPosition().getNotation(), hexagonOptional.get().getNotation());
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
     * @return Returns a {@link List} of {@link Hexagon}s.
     */
    @Override
    public List<Hexagon> getHypotheticalAttackableFields(Chessboard chessboard) {
        de.mki.jchess.server.implementation.threePersonChess.Chessboard actualChessboard = (de.mki.jchess.server.implementation.threePersonChess.Chessboard) chessboard;
        List<Hexagon> output = new ArrayList<>();
        directions.forEach(direction -> {
            Optional<Hexagon> hexagonOptional = getHypotheticalPosition().getNeighbourByDirection(direction);
            while (hexagonOptional.isPresent()) {
                logger.trace("Checking attackable fields for direction {} from {} to {}", direction, getPosition().getNotation(), hexagonOptional.get().getNotation());
                // If it is a diagonal move and the path is blocked, break the loop
                if (direction.getNecessaryFreeDirectionsForDiagonal().isPresent() && actualChessboard.willFieldsBeOccupied(actualChessboard.getFreeFieldsForDiagonalMove(hexagonOptional.get().getNeighbourByDirection(direction.getOppositeDirection()).get(), direction))) {
                    logger.trace("Stopping from {} to {}. Diagonal movement fields are not free {}. Switching to next direction.", getPosition().getNotation(), hexagonOptional.get().getNotation(), actualChessboard.getFreeFieldsForDiagonalMove(hexagonOptional.get(), direction).toString());
                    break;
                }
                if (chessboard.willFieldsBeOccupied(Collections.singletonList(hexagonOptional.get()))) {
                    // Check if the occupied field has an enemy figure. If so, the field is indeed attackable
                    if (actualChessboard.isFigureOwnedByEnemy(hexagonOptional.get(), getClient())) {
                        // It's an enemy figure
                        output.add(hexagonOptional.get());
                    }
                    logger.trace("Stopping from {} to {}. Field is occupied. Switching to next direction.", getPosition().getNotation(), hexagonOptional.get().getNotation());
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
