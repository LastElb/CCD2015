package de.mki.jchess.server.implementation.threePersonChess.figures;

import de.mki.jchess.server.implementation.threePersonChess.Chessboard;
import de.mki.jchess.server.implementation.threePersonChess.Hexagon;
import de.mki.jchess.server.model.Client;
import de.mki.jchess.server.model.Figure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * Created by Igor on 16.12.2015.
 */
public class FigureUtils {

    /**
     * Don't allow instances of this class
     */
    private FigureUtils() {}

    private static final Logger logger = LoggerFactory.getLogger(FigureUtils.class);

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
}
