package de.mki.jchess.server.implementation.threePersonChess.figures;

import de.mki.jchess.server.implementation.threePersonChess.Chessboard;
import de.mki.jchess.server.implementation.threePersonChess.Direction;
import de.mki.jchess.server.implementation.threePersonChess.Hexagon;
import de.mki.jchess.server.model.Client;
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
                        .filter(o -> !((Figure) o).getClient().getId().equals(client.getId()))
                        .filter(o -> ((Figure) o).getPosition().getNotation().equals(hexagon.getNotation()))
                        .findFirst()
                        .ifPresent(o -> {
                            Figure beatableFigure = (Figure) o;
                            beatableFigure.setHypotheticalRemoved(true);
                            logger.trace("Found beatable figure (" + beatableFigure.getName() + ") at field " + hexagon.getNotation());

                            figure.setHypotheticalPosition(hexagon);
                            try {
                                if (!chessboard.willKingBeChecked(client.getId()))
                                    possibleFields.add(hexagon);
                            } catch (Exception e) {
                                logger.error("", e);
                            }
                            figure.setHypotheticalPosition(null);
                            beatableFigure.setHypotheticalRemoved(null);
                        });
            } else {
                figure.setHypotheticalPosition(hexagon);
                try {
                    if (!chessboard.willKingBeChecked(client.getId()))
                        possibleFields.add(hexagon);
                } catch (Exception e) {
                    logger.error("", e);
                }
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
            if (chessboard.isFigureOwnedByEnemy(hexagonOptional.get(), client)) {
                // It's an enemy figure
                possibleFields.add(hexagonOptional.get());
            }
            // Return an empty {@link Optional} when we are on the end of our line.
            return Optional.empty();
        } else {
            // Return the next {@link Optional} in our {@link Direction}.
            possibleFields.add(hexagonOptional.get());
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
            if (chessboard.isFigureOwnedByEnemy(hexagonOptional.get(), client)) {
                // It's an enemy figure
                possibleFields.add(hexagonOptional.get());
            }
            // Return an empty {@link Optional} when we are on the end of our line.
            return Optional.empty();
        } else {
            // Return the next {@link Optional} in our {@link Direction}.
            possibleFields.add(hexagonOptional.get());
            return hexagonOptional.get().getNeighbourByDirection(direction);
        }
    }
}
