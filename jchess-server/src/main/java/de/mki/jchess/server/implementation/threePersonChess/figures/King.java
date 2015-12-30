package de.mki.jchess.server.implementation.threePersonChess.figures;

import de.mki.jchess.commons.websocket.MovementEvent;
import de.mki.jchess.server.implementation.threePersonChess.Direction;
import de.mki.jchess.server.implementation.threePersonChess.Hexagon;
import de.mki.jchess.server.model.Chessboard;
import de.mki.jchess.commons.Client;
import de.mki.jchess.server.model.Figure;
import de.mki.jchess.commons.RandomStringService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

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
        FigureUtils.evaluatePossibleMovements(this, attackableFields, (de.mki.jchess.server.implementation.threePersonChess.Chessboard) chessboard, getClient(), output);
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
        // Lets find the rooks of the player
        List<Rook> rooks = (List<Rook>) chessboard.getFigures().stream()
                .filter(o -> o instanceof Rook)
                .filter(o -> !((Rook) o).isRemoved())
                .filter(o1 -> ((Rook) o1).getClient().getId().equals(getClient().getId()))
                .collect(Collectors.toList());

        return rooks.stream()
                .filter(rook -> {
                    // Check if the rook was already moved
                    boolean wasMoved = chessboard.getParentGame().getGameHistory().stream().parallel()
                            .filter(historyEntry -> historyEntry.getChessboardEvents().stream()
                                    .filter(chessboardEvent -> chessboardEvent instanceof MovementEvent)
                                    .map(chessboardEvent -> (MovementEvent) chessboardEvent)
                                    .filter(movementEvent -> movementEvent.getFigureId().equals(getId()) || movementEvent.getFigureId().equals(rook.getId()))
                                    .count() != 0)
                            .findAny().isPresent();
                    logger.trace("Figure {} ({}) was moved: {}", getName(), getPosition().getNotation(), wasMoved);
                    logger.trace("Figure {} ({}) was moved: {}", rook.getName(), rook.getPosition().getNotation(), wasMoved);
                    return !wasMoved;
                })
                .parallel()
                .map(rook -> {
                    // Get the direction of the castling first
                    // from the kings view
                    Direction direction = FigureUtils.findDirection(getPosition(), rook.getPosition());

                    // Check if all fields between the figures are not attacked
                    List<Hexagon> hexagonsBetween = new ArrayList<>();
                    Hexagon current = getPosition();
                    while (!current.getNotation().equals(rook.getPosition().getNotation())) {
                        hexagonsBetween.add(current);
                        current = current.getNeighbourByDirection(direction).get();
                    }
                    hexagonsBetween.add(current);

                    if (!FigureUtils.areHexagonsAttacked(
                            hexagonsBetween,
                            (de.mki.jchess.server.implementation.threePersonChess.Chessboard) chessboard,
                            getClient())) {
                        // Remove the first and last hexagon
                        hexagonsBetween.remove(0);
                        hexagonsBetween.remove(hexagonsBetween.size() - 1);
                        // Check if the fields between King and Rook are free
                        if (FigureUtils.areHexagonsFree(hexagonsBetween,
                                (de.mki.jchess.server.implementation.threePersonChess.Chessboard) chessboard)) {
                            // The fields are free
                            // Check if the king would be checked with the new position of the figures
                            rook.setHypotheticalPosition(getPosition());
                            setHypotheticalPosition(hexagonsBetween.get(1));
                            boolean wouldKingBeChecked = chessboard.willKingBeChecked(getClient().getId());
                            setHypotheticalPosition(null);
                            rook.setHypotheticalPosition(null);
                            // When the king would not be checked the user can do this move.
                            if (!wouldKingBeChecked)
                                return hexagonsBetween.get(1);
                        }
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
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
        directions.forEach(direction -> getPosition().getNeighbourByDirection(direction).ifPresent(hexagon -> {
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
        }));
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
        directions.forEach(direction -> getHypotheticalPosition().getNeighbourByDirection(direction).ifPresent(hexagon -> {
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
        }));
        return output;
    }
}
