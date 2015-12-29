package de.mki.jchess.server.implementation.threePersonChess.figures;

import de.mki.jchess.server.implementation.threePersonChess.Chessboard;
import de.mki.jchess.server.implementation.threePersonChess.Direction;
import de.mki.jchess.server.implementation.threePersonChess.Hexagon;
import de.mki.jchess.commons.Client;
import de.mki.jchess.server.model.Figure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
     * @param chessboard The instance of the {@link Chessboard}.
     * @param hexagonOptional The target {@link Hexagon}.
     * @param possibleFields The {@link List}-reference where the possible {@link Hexagon}s are saved to.
     * @param client The {@link Client Owner} of the {@link Figure}.
     * @param direction The {@link Direction} the {@link Figure} wants to move to.
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
}
