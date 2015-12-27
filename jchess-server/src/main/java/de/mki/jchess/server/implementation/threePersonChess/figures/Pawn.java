package de.mki.jchess.server.implementation.threePersonChess.figures;

import de.mki.jchess.server.exception.InvalidFacingDirection;
import de.mki.jchess.server.implementation.threePersonChess.Direction;
import de.mki.jchess.server.implementation.threePersonChess.Hexagon;
import de.mki.jchess.server.model.Chessboard;
import de.mki.jchess.server.model.Client;
import de.mki.jchess.server.model.Figure;
import de.mki.jchess.server.model.websocket.MovementEvent;
import de.mki.jchess.server.service.RandomStringService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of moves with a three person pawn.
 * Created by Igor on 12.11.2015.
 */
public class Pawn extends Figure<Hexagon> {

    private static final Logger logger = LoggerFactory.getLogger(Pawn.class);
    Direction facingDirection;
    List<Direction> attackableDirections;
    List<Direction> movableDirections;

    /**
     * Creates a new instance of a pawn. Constructor with the possibility to pass an own id.
     * @param id The figures id.
     * @param client The owner of the {@link Pawn}.
     * @param direction Allowed values: {@link Direction#DIAGONALBOTTOM}, {@link Direction#DIAGONALTOPLEFT}, {@link Direction#DIAGONALTOPRIGHT}
     * @throws InvalidFacingDirection
     */
    public Pawn(String id, Client client, Direction direction) throws InvalidFacingDirection {
        super(client);
        setId(id);
        setName("Pawn");
        this.facingDirection = direction;
        switch (facingDirection) {
            case DIAGONALBOTTOM:
                // White Player
                attackableDirections = Arrays.asList(Direction.DIAGONALBOTTOM, Direction.DIAGONALBOTTOMLEFT, Direction.DIAGONALBOTTOMRIGHT);
                movableDirections = Arrays.asList(Direction.BOTTOMLEFT, Direction.BOTTOMRIGHT);
                break;
            case DIAGONALTOPLEFT:
                // Grey Player
                attackableDirections = Arrays.asList(Direction.DIAGONALTOP, Direction.DIAGONALTOPLEFT, Direction.DIAGONALBOTTOMLEFT);
                movableDirections = Arrays.asList(Direction.LEFT, Direction.TOPLEFT);
                break;
            case DIAGONALTOPRIGHT:
                // Black Player
                attackableDirections = Arrays.asList(Direction.DIAGONALTOP, Direction.DIAGONALTOPRIGHT, Direction.DIAGONALBOTTOMRIGHT);
                movableDirections = Arrays.asList(Direction.RIGHT, Direction.TOPRIGHT);
                break;
            default:
                // Every other direction is not allowed
                throw new InvalidFacingDirection(direction, this);
        }
    }

    /**
     * Default constructor.
     * @param client The owner of the {@link Pawn}.
     * @param direction Allowed values: {@link Direction#DIAGONALBOTTOM}, {@link Direction#DIAGONALTOPLEFT}, {@link Direction#DIAGONALTOPRIGHT}
     * @throws InvalidFacingDirection
     */
    public Pawn(Client client, Direction direction) throws InvalidFacingDirection {
        this(RandomStringService.getRandomString(), client, direction);
    }

    /**
     * {@inheritDoc}
     * @param chessboard The instance of the {@link Chessboard} of the current {@link de.mki.jchess.server.model.Game}
     * @return Returns a {@link List} of {@link Hexagon}s.
     */
    @Override
    public List<Hexagon> getPossibleMovements(Chessboard chessboard) {
        List<Hexagon> output = new ArrayList<>();
        movableDirections.forEach(direction -> getPosition().getNeighbourByDirection(direction)
                .ifPresent(hexagon -> {
                    if (!chessboard.areFieldsOccupied(Collections.singletonList(hexagon))) {
                        logger.trace("Field " + hexagon.getNotation() + " is not occupied.");
                        setHypotheticalPosition(hexagon);
                        if (!chessboard.willKingBeChecked(getClient().getId())) {
                            output.add(hexagon);
                            logger.trace("King will not be checked with this move.");
                        } else
                            logger.trace("King will be checked with this move.");
                        setHypotheticalPosition(null);
                    } else
                        logger.trace("Field " + hexagon.getNotation() + " is occupied.");
                }));
        return output;
    }

    /**
     * {@inheritDoc}
     * @param chessboard The current {@link Chessboard} instance for checking purposes.
     * @return Returns a {@link List} of {@link Hexagon}s.
     */
    @Override
    public List<Hexagon> getPossibleSpecialMovements(Chessboard chessboard) {
        List<Hexagon> output = new ArrayList<>();
        // If we did not move our pawn yet we may move 2 fields if the king does not become checked
        boolean wasMoved = chessboard.getParentGame().getGameHistory().stream()
                .filter(historyEntry -> historyEntry.getChessboardEvents().stream()
                        .filter(chessboardEvent -> chessboardEvent instanceof MovementEvent)
                        .map(chessboardEvent -> (MovementEvent) chessboardEvent)
                        .filter(movementEvent -> movementEvent.getFigureId().equals(getId())).count() != 0).findAny().isPresent();
        if (!wasMoved) {
            movableDirections.forEach(direction -> getPosition().getNeighbourByDirection(direction)
                    .ifPresent(hexagon -> hexagon.getNeighbourByDirection(direction)
                            .ifPresent(targetHexagon -> {
                                // Both fields need to be free and the king can not be checked
                                logger.trace("Inspecting special move to field " + targetHexagon.getNotation());
                                if (!chessboard.areFieldsOccupied(Collections.singletonList(hexagon)) &&
                                        !chessboard.areFieldsOccupied(Collections.singletonList(targetHexagon))) {
                                    logger.trace("Fields  " + hexagon.getNotation() + " and " + targetHexagon.getNotation() + " are not occupied");
                                    setHypotheticalPosition(targetHexagon);
                                    if (!chessboard.willKingBeChecked(getClient().getId())) {
                                        output.add(targetHexagon);
                                        logger.trace("King will not be checked with this move.");
                                    } else
                                        logger.trace("King will be checked with this move.");
                                    setHypotheticalPosition(null);
                                } else {
                                    logger.trace("Fields " + hexagon.getNotation() + " or " + targetHexagon.getNotation() + " are occupied");
                                }
                            })));
        }
        // Attack move on pawns are special moves, as they are diagonal
        attackableDirections.forEach(direction -> getPosition().getNeighbourByDirection(direction)
                .ifPresent(hexagon -> getAttackableFields(chessboard).stream()
                        .filter(attackableHexagon -> attackableHexagon.getNotation().equals(hexagon.getNotation()))
                        .findFirst()
                        .ifPresent(hexagon1 -> chessboard.getFigures().stream()
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
                                }))));

        return output;
    }

    /**
     * {@inheritDoc}
     * @param chessboard The current {@link Chessboard} instance for checking purposes.
     * @return Returns a {@link List} of {@link Hexagon}s.
     */
    @Override
    public List<Hexagon> getAttackableFields(Chessboard chessboard) {
        List<Hexagon> output = new ArrayList<>();
        attackableDirections.forEach(direction -> {
            Optional<Hexagon> optional = getPosition().getNeighbourByDirection(direction);
            // Check if this neighbour field does exist and if the bordering fields are free
            if (optional.isPresent() &&
                    direction.getNecessaryFreeDirectionsForDiagonal().isPresent() &&
                    (!chessboard.areFieldsOccupied(direction.getNecessaryFreeDirectionsForDiagonal().get()
                        .stream()
                        .map(freeDirection -> getPosition().getNeighbourByDirection(freeDirection))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList()))))
                output.add(optional.get());
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
        attackableDirections.forEach(direction -> {
            Optional<Hexagon> optional = getHypotheticalPosition().getNeighbourByDirection(direction);
            // Check if this neighbour field does exist and if the bordering fields are free
            if (optional.isPresent() && (!actualChessboard.willFieldsBeOccupied(direction.getNecessaryFreeDirectionsForDiagonal().get()
                    .stream()
                    .map(freeDirection -> getHypotheticalPosition().getNeighbourByDirection(freeDirection))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList()))))
                output.add(optional.get());
        });
        return output;
    }
}
