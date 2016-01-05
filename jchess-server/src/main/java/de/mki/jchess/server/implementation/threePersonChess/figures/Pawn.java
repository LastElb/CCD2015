package de.mki.jchess.server.implementation.threePersonChess.figures;

import de.mki.jchess.commons.HistoryEntry;
import de.mki.jchess.server.exception.InvalidFacingDirection;
import de.mki.jchess.server.exception.NotationNotFoundException;
import de.mki.jchess.server.implementation.threePersonChess.Direction;
import de.mki.jchess.server.implementation.threePersonChess.Hexagon;
import de.mki.jchess.server.model.Chessboard;
import de.mki.jchess.commons.Client;
import de.mki.jchess.server.model.Figure;
import de.mki.jchess.commons.websocket.MovementEvent;
import de.mki.jchess.commons.RandomStringService;
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
    static Map<Client, List<Hexagon>> playerBaselines;

    /**
     * Creates a new instance of a pawn. Constructor with the possibility to pass an own id.
     * @param id The figures id.
     * @param client The owner of the {@link Pawn}.
     * @param direction Allowed values: {@link Direction#DIAGONALBOTTOM}, {@link Direction#DIAGONALTOPLEFT}, {@link Direction#DIAGONALTOPRIGHT}
     * @param chessboard Instance of the {@link Chessboard}.
     * @throws InvalidFacingDirection
     */
    public Pawn(String id, Client client, Direction direction, Chessboard chessboard) throws InvalidFacingDirection {
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
        if (playerBaselines == null)
            playerBaselines = new LinkedHashMap<>();
        if (!playerBaselines.containsKey(client)) {
            List<Hexagon> baseline = new ArrayList<>();
            switch (client.getTeam()) {
                case "white":
                    try {
                        baseline = Arrays.asList(
                                (Hexagon) chessboard.getFieldByNotation("a1"),
                                (Hexagon) chessboard.getFieldByNotation("a2"),
                                (Hexagon) chessboard.getFieldByNotation("a3"),
                                (Hexagon) chessboard.getFieldByNotation("a4"),
                                (Hexagon) chessboard.getFieldByNotation("a5"),
                                (Hexagon) chessboard.getFieldByNotation("a6"),
                                (Hexagon) chessboard.getFieldByNotation("a7"),
                                (Hexagon) chessboard.getFieldByNotation("a8")
                        );
                    } catch (NotationNotFoundException e) {
                        logger.error("", e);
                    }
                    break;
                case "grey":
                    try {
                        baseline = Arrays.asList(
                                (Hexagon) chessboard.getFieldByNotation("f1"),
                                (Hexagon) chessboard.getFieldByNotation("g2"),
                                (Hexagon) chessboard.getFieldByNotation("h3"),
                                (Hexagon) chessboard.getFieldByNotation("i4"),
                                (Hexagon) chessboard.getFieldByNotation("j5"),
                                (Hexagon) chessboard.getFieldByNotation("k6"),
                                (Hexagon) chessboard.getFieldByNotation("l7"),
                                (Hexagon) chessboard.getFieldByNotation("m8")
                        );
                    } catch (NotationNotFoundException e) {
                        logger.error("", e);
                    }
                    break;
                case "black":
                    try {
                        baseline = Arrays.asList(
                                (Hexagon) chessboard.getFieldByNotation("f13"),
                                (Hexagon) chessboard.getFieldByNotation("g13"),
                                (Hexagon) chessboard.getFieldByNotation("h13"),
                                (Hexagon) chessboard.getFieldByNotation("i13"),
                                (Hexagon) chessboard.getFieldByNotation("j13"),
                                (Hexagon) chessboard.getFieldByNotation("k13"),
                                (Hexagon) chessboard.getFieldByNotation("l13"),
                                (Hexagon) chessboard.getFieldByNotation("m13")
                        );
                    } catch (NotationNotFoundException e) {
                        logger.error("", e);
                    }
                    break;
            }
            playerBaselines.put(client, baseline);
        }
    }

    /**
     * Default constructor.
     * @param client The owner of the {@link Pawn}.
     * @param direction Allowed values: {@link Direction#DIAGONALBOTTOM}, {@link Direction#DIAGONALTOPLEFT}, {@link Direction#DIAGONALTOPRIGHT}
     * @param chessboard Instance of the {@link Chessboard}.
     * @throws InvalidFacingDirection
     */
    public Pawn(Client client, Direction direction, Chessboard chessboard) throws InvalidFacingDirection {
        this(RandomStringService.getRandomString(), client, direction, chessboard);
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
                                // Just active figures
                                .filter(o -> !((Figure) o).isRemoved())
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

        // En passant
        // Just get the latest movements
        List<HistoryEntry> latestMoves = new ArrayList<>();
        if (chessboard.getParentGame().getGameHistory().size() >= 3) {
            latestMoves.add(chessboard.getParentGame().getGameHistory().get(chessboard.getParentGame().getGameHistory().size() - 1));
            latestMoves.add(chessboard.getParentGame().getGameHistory().get(chessboard.getParentGame().getGameHistory().size() - 2));
        }

        latestMoves.stream()
                .map(historyEntry -> historyEntry.getChessboardEvents().stream()
                        // Just consider Movement Events
                        .filter(chessboardEvent -> chessboardEvent instanceof MovementEvent)
                        .map(chessboardEvent -> (MovementEvent) chessboardEvent)
                        // Just keep those events where the figure was a pawn.
                        .map(movementEvent -> {
                            Optional<Pawn> pawn = chessboard.getFigures().stream()
                                    .filter(o -> ((Figure) o).getId().equals(movementEvent.getFigureId()))
                                    .filter(o -> o instanceof Pawn)
                                    .findFirst();
                            if (pawn.isPresent()) {
                                logger.trace("Keeping pawn at {}", pawn.get().getPosition().getNotation());
                                return Arrays.asList(movementEvent, pawn.get());
                            }
                            return null;
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList())
                        )
                .filter(objects2 -> {
                    logger.trace("En Passant Entry before flatMap");
                    return true;
                })
                .flatMap(Collection::stream)
                .filter(objects1 -> !objects1.isEmpty())
                .filter(objects2 -> {
                    logger.trace("En Passant Entry after flatMap");
                    return true;
                })
                // Now we have all pawns that were moved last turn.
                .filter(objects -> {
                    MovementEvent movementEvent = (MovementEvent) objects.get(0);
                    Hexagon source;
                    Hexagon target;
                    try {
                        source = (Hexagon) chessboard.getFieldByNotation(movementEvent.getFromNotation());
                        target = (Hexagon) chessboard.getFieldByNotation(movementEvent.getToNotation());
                    } catch (NotationNotFoundException e) {
                        logger.error("", e);
                        return false;
                    }
                    // We are now checking if the fields are neighbours. Double step fields are not neighbours.
                    return !source.isHexagonNeighbour(target);
                })
                // Now we have all pawns that did a double step last move.
                .forEach(lists -> {
                    // Get the field between the double step
                    // and check if it is in the attackable fields of this figure.
                    MovementEvent movementEvent = (MovementEvent) lists.get(0);
                    Hexagon source;
                    Hexagon target;
                    try {
                        source = (Hexagon) chessboard.getFieldByNotation(movementEvent.getFromNotation());
                        target = (Hexagon) chessboard.getFieldByNotation(movementEvent.getToNotation());
                    } catch (NotationNotFoundException e) {
                        logger.error("", e);
                        return;
                    }
                    Direction direction = FigureUtils.findDirection(source, target);
                    Optional<Hexagon> hexagonBetween = source.getNeighbourByDirection(direction);
                    hexagonBetween.ifPresent(hexagon -> {
                        Pawn pawn = (Pawn) lists.get(1);
                        pawn.setHypotheticalRemoved(true);
                        setHypotheticalPosition(hexagon);
                        if (getAttackableFields(chessboard).contains(hexagon) && !chessboard.willKingBeChecked(getClient().getId()))
                            output.add(hexagon);
                        setHypotheticalPosition(null);
                        pawn.setHypotheticalRemoved(null);
                    });
                });

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

    /**
     * Function that determines if a {@link Figure} is at the enemy baseline. This would cause a pawn promotion.
     * @return Returns true if the pawn is at an enemy baseline.
     */
    public boolean isAtEnemyBaseline() {
        final boolean[] output = {false};
        playerBaselines.forEach((client, hexagons) -> {
            if (!client.getId().equals(getClient().getId()) && playerBaselines.get(client).contains(getPosition()))
                output[0] = true;
        });
        return output[0];
    }

    /**
     * Returns the {@link Optional} of the beaten {@link Pawn} when doing an "en passant" move with this {@link Pawn}
     * @param targetFieldNotation    The {@link Hexagon#getNotation()} of this {@link Pawn} after the move.
     * @param chessboard             The {@link Chessboard} instance.
     * @return Returns the {@link Optional} of the beaten {@link Pawn}.
     */
    public Optional<Pawn> enPassantPawn(String targetFieldNotation, Chessboard chessboard) {
        List<HistoryEntry> latestMoves = new ArrayList<>();
        List<HistoryEntry> reversedMoves = chessboard.getParentGame().getGameHistory();
        Collections.reverse(reversedMoves);

        for (HistoryEntry historyEntry : reversedMoves) {
            if (historyEntry.getPlayer().equals(getClient()))
                break;
            latestMoves.add(historyEntry);
        }
        return latestMoves.stream()
                .map(historyEntry -> historyEntry.getChessboardEvents().stream()
                        // Just consider Movement Events
                        .filter(chessboardEvent -> chessboardEvent instanceof MovementEvent)
                        .map(chessboardEvent -> (MovementEvent) chessboardEvent)
                        // Just keep those events where the figure was a pawn.
                        .map(movementEvent -> {
                            Optional<Pawn> pawn = chessboard.getFigures().stream()
                                    .filter(o -> ((Figure) o).getId().equals(movementEvent.getFigureId()))
                                    .filter(o -> o instanceof Pawn)
                                    .findFirst();
                            if (pawn.isPresent()) {
                                logger.trace("Keeping pawn at {}", pawn.get().getPosition().getNotation());
                                return Arrays.asList(movementEvent, pawn.get());
                            }
                            return null;
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList())
                )
                .flatMap(Collection::stream)
                .filter(lists1 -> !lists1.isEmpty())
                // Now we have all pawns that were moved last turn.
                .filter(objects -> {
                    MovementEvent movementEvent = (MovementEvent) objects.get(0);
                    Hexagon source;
                    Hexagon target;
                    try {
                        source = (Hexagon) chessboard.getFieldByNotation(movementEvent.getFromNotation());
                        target = (Hexagon) chessboard.getFieldByNotation(movementEvent.getToNotation());
                    } catch (NotationNotFoundException e) {
                        logger.error("", e);
                        return false;
                    }
                    // We are now checking if the fields are neighbours. Double step fields are not neighbours.
                    if (source.isHexagonNeighbour(target))
                        return false;
                    // Now we get the direction
                    Direction direction = FigureUtils.findDirection(source, target);
                    Optional<Hexagon> hexagonBetween = source.getNeighbourByDirection(direction);
                    return hexagonBetween.isPresent() && hexagonBetween.get().getNotation().equals(targetFieldNotation);
                })
                .map(lists -> (Pawn) lists.get(1))
                .findFirst();
    }
}
