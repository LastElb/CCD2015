package de.mki.jchess.server.implementation.threePersonChess.figures;

import de.mki.jchess.server.implementation.threePersonChess.Chessboard;
import de.mki.jchess.server.implementation.threePersonChess.Direction;
import de.mki.jchess.server.implementation.threePersonChess.Hexagon;
import de.mki.jchess.commons.Client;
import de.mki.jchess.server.model.Figure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Commonly used functions by {@link Figure}s on a {@link Chessboard}.
 * Created by Igor on 16.12.2015.
 */
public class FigureUtils {

    private static final Logger logger = LoggerFactory.getLogger(FigureUtils.class);

    /**
     * Don't allow instances of this class
     */
    private FigureUtils() {
    }

    /**
     * Evaluates if {@link Hexagon}s of a {@link List} are usable for given {@link Figure}.
     * @param figure The {@link Figure}
     * @param attackableFields A {@link List} of {@link Hexagon} the {@link Figure} can attack.
     * @param chessboard A {@link Chessboard} instance
     * @param client The {@link Client Owner} of the {@link Figure}.
     * @param possibleFields The {@link List}-Reference that gets updated with the possible {@link Hexagon}s to move to.
     */
    public static void evaluatePossibleMovements(Figure<Hexagon> figure, List<Hexagon> attackableFields, Chessboard chessboard, Client client, List<Hexagon> possibleFields) {
        attackableFields.forEach(hexagon -> {
            // If the field is occupied, lets assume we beat this figure
            if (chessboard.areFieldsOccupied(Collections.singletonList(hexagon))) {
                chessboard.getFigures().stream()
                        // Just keep the figures of the enemies.
                        .filter(o -> !((Figure) o).getClient().getId().equals(client.getId()))
                        // Just get the figure that is standing on our target field
                        .filter(o -> ((Figure) o).getPosition().getNotation().equals(hexagon.getNotation()))
                        // Just active ones
                        .filter(o -> !((Figure) o).isRemoved())
                        .findFirst()
                        .ifPresent(o -> {
                            // We hypothetically beat this figure
                            Figure beatableFigure = (Figure) o;
                            beatableFigure.setHypotheticalRemoved(true);
                            logger.trace("Found beatable figure ({}) at field {}" , beatableFigure.getName(), hexagon.getNotation());

                            figure.setHypotheticalPosition(hexagon);
                            // Check if we really can beat the figure without our king being checked with this move.
                            if (!chessboard.willKingBeChecked(client.getId())) {
                                possibleFields.add(hexagon);
                                logger.trace("We can beat the figure ({}) at field {}." , beatableFigure.getName(), hexagon.getNotation());
                            } else {
                                logger.trace("We can not beat the figure ({}) at field {} as our king would be checked." , beatableFigure.getName(), hexagon.getNotation());
                            }

                            figure.setHypotheticalPosition(null);
                            beatableFigure.setHypotheticalRemoved(null);
                        });
            } else {
                // We have a free field.
                figure.setHypotheticalPosition(hexagon);
                if (!chessboard.willKingBeChecked(client.getId()))
                    possibleFields.add(hexagon);
                figure.setHypotheticalPosition(null);
            }
        });
    }

    /**
     * Adds the target {@link Hexagon} to a {@link List} if the {@link Hexagon} is free
     * or occupied by an enemy {@link Figure}.
     * @param chessboard The instance of the {@link Chessboard}.
     * @param hexagonOptional The target {@link Hexagon}.
     * @param possibleFields The {@link List}-reference where the possible {@link Hexagon}s are saved to.
     * @param client The {@link Client Owner} of the {@link Figure}.
     * @param direction The {@link Direction} the {@link Figure} wants to move to.
     * @return Returns an {@link Optional} of {@link Hexagon} if we can go one {@link Hexagon} further
     * or an empty {@link Optional} if this was the last {@link Hexagon} in this {@link Direction}.
     */
    public static Optional<Hexagon> addHexagonToListIfFreeOrEnemy(Chessboard chessboard, Optional<Hexagon> hexagonOptional, List<Hexagon> possibleFields, Client client, Direction direction) {
        if (chessboard.areFieldsOccupied(Collections.singletonList(hexagonOptional.get()))) {
            // Check if the occupied field has an enemy figure. If so, the field is indeed attackable
            logger.trace("{} is occupied", hexagonOptional.get().getNotation());
            if (chessboard.isFigureOwnedByEnemy(hexagonOptional.get(), client)) {
                // It's an enemy figure
                possibleFields.add(hexagonOptional.get());
                logger.trace("{} is occupied by an enemy figure", hexagonOptional.get().getNotation());
            }
            logger.trace("Not going any longer in the direction {}", direction.toString());
            // Return an empty {@link Optional} when we are on the end of our line.
            return Optional.empty();
        } else {
            // Return the next {@link Optional} in our {@link Direction}.
            possibleFields.add(hexagonOptional.get());
            logger.trace("{} is not occupied. Going more into the direction.", hexagonOptional.get().getNotation());
            return hexagonOptional.get().getNeighbourByDirection(direction);
        }
    }

    /**
     * Adds the target {@link Hexagon} to a {@link List} if the {@link Hexagon} is free
     * or will be occupied by an enemy {@link Figure}.
     * @param chessboard      The instance of the {@link Chessboard}.
     * @param hexagonOptional The target {@link Hexagon}.
     * @param possibleFields  The {@link List}-reference where the possible {@link Hexagon}s are saved to.
     * @param client          The {@link Client Owner} of the {@link Figure}.
     * @param direction       The {@link Direction} the {@link Figure} wants to move to.
     * @return Returns an {@link Optional} of {@link Hexagon} if we can go one {@link Hexagon} further
     * or an empty {@link Optional} if this was the last {@link Hexagon} in this {@link Direction}.
     */
    public static Optional<Hexagon> addHexagonToListIfFreeOrEnemyHypothetical(Chessboard chessboard, Optional<Hexagon> hexagonOptional, List<Hexagon> possibleFields, Client client, Direction direction) {
        if (chessboard.willFieldsBeOccupied(Collections.singletonList(hexagonOptional.get()))) {
            // Check if the occupied field has an enemy figure. If so, the field is indeed attackable
            logger.trace("{} is occupied", hexagonOptional.get().getNotation());
            if (chessboard.isFigureOwnedByEnemy(hexagonOptional.get(), client)) {
                // It's an enemy figure
                possibleFields.add(hexagonOptional.get());
                logger.trace("{} is occupied by an enemy figure", hexagonOptional.get().getNotation());
            }
            logger.trace("Not going any longer in the direction {}", direction.toString());
            // Return an empty {@link Optional} when we are on the end of our line.
            return Optional.empty();
        } else {
            // Return the next {@link Optional} in our {@link Direction}.
            possibleFields.add(hexagonOptional.get());
            logger.trace("{} is not occupied. Going more into the direction.", hexagonOptional.get().getNotation());
            return hexagonOptional.get().getNeighbourByDirection(direction);
        }
    }

    /**
     * Detects a straight direction between two fields. Simplified!!
     * Just use it for castling detection!
     * @param source The target {@link Hexagon}
     * @param target The source {@link Hexagon}
     * @return Returns the direction the target is in view of the source.
     */
    public static Direction findDirection (@NotNull Hexagon source, @NotNull Hexagon target) {
        List<Direction> directions = Arrays.asList(
                Direction.RIGHT,
                Direction.BOTTOMRIGHT,
                Direction.BOTTOMLEFT,
                Direction.LEFT,
                Direction.TOPLEFT,
                Direction.TOPRIGHT
        );
        Optional<Direction> targetDirection = directions.stream()
                .filter(direction -> {
                    Optional<Hexagon> neighbour = source.getNeighbourByDirection(direction);
                    if (!neighbour.isPresent())
                        return false;
                    int cost = 100;
                    while (cost > (Math.abs(target.getRow() - neighbour.get().getRow()) +  Math.abs(target.getColumn() - neighbour.get().getColumn()))) {
                        cost = Math.abs(target.getRow() - neighbour.get().getRow()) + Math.abs(target.getColumn() - neighbour.get().getColumn());
                        if (cost == 0)
                            return true;
                        neighbour = neighbour.get().getNeighbourByDirection(direction);
                        if (!neighbour.isPresent())
                            return false;
                    }
                    return false;
                })
                .findFirst();
        return targetDirection.get();
    }

    /**
     * Checks if all {@link Hexagon}s in a {@link List} are attacked by enemy {@link Figure}s or not.
     * @param observedHexagons The {@link Hexagon}s to check for.
     * @param chessboard       Instance of the {@link Chessboard}
     * @param client           The current active {@link Client}
     * @return Returns true if at least one {@link Hexagon} is attacked by an enemy.
     */
    public static boolean areHexagonsAttacked(List<Hexagon> observedHexagons, Chessboard chessboard, Client client) {
        List<Hexagon> attackedPositions = chessboard.getFigures().stream()
                // Just active figures
                .filter(hexagonFigure -> !hexagonFigure.isRemoved())
                // Just enemy figures
                .filter(hexagonFigure -> !hexagonFigure.getClient().getId().equals(client.getId()))
                // Get all attackable fields
                .parallel()
                .map(hexagonFigure -> {
                    List<Hexagon> attackableFields = hexagonFigure.getAttackableFields(chessboard);
                    logger.trace("hexagonFigure = {}:{}, attackableFields = {}", hexagonFigure.getName(),
                            hexagonFigure.getPosition().getNotation(), attackableFields);
                    return attackableFields;
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        for (Hexagon position : attackedPositions) {
            if (observedHexagons.contains(position))
                return true;
        }
        return false;
    }

    /**
     * Checks if all {@link Hexagon}s in a {@link List} are free or occupied by a {@link Figure}.
     * @param observedHexagons    The {@link Hexagon}s to check for.
     * @param chessboard          Instance of the {@link Chessboard}
     * @return Returns true if all {@link Hexagon}s are not occupied. False otherwise.
     */
    public static boolean areHexagonsFree(List<Hexagon> observedHexagons, Chessboard chessboard) {
        List<Hexagon> figurePositions = chessboard.getFigures().stream()
                // Just active figures
                .filter(hexagonFigure -> !hexagonFigure.isRemoved())
                // We just want the positions of the figures
                .map(de.mki.jchess.commons.Figure::getPosition)
                .collect(Collectors.toList());
        for (Hexagon position : figurePositions) {
            if (observedHexagons.contains(position))
                return false;
        }
        return true;
    }
}
