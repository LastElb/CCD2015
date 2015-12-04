package de.mki.jchess.server.implementation.threePersonChess;

import de.mki.jchess.server.exception.MoveNotAllowedException;
import de.mki.jchess.server.implementation.threePersonChess.figures.King;
import de.mki.jchess.server.model.*;
import de.mki.jchess.server.model.websocket.FigureEvent;
import de.mki.jchess.server.model.websocket.MovementEvent;
import de.mki.jchess.server.model.websocket.PlayerChangedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Igor on 13.11.2015.
 */
public class Chessboard extends de.mki.jchess.server.model.Chessboard<Hexagon> {

    private static final Logger logger = LoggerFactory.getLogger(Chessboard.class);

    public Chessboard(Game parentGame) {
        super(parentGame);
    }

    public Hexagon getFieldByNotation(int column, int row) throws Exception {
        return fields.stream().filter(field -> field.column == column && field.row == row).findFirst().orElseThrow(() -> new Exception("Notation not found"));
    }

    /**
     * Returns if the {@link de.mki.jchess.server.implementation.threePersonChess.figures.King} of a {@link de.mki.jchess.server.model.Client} is checked with the current figure layout.
     * @param clientId The ID of the {@link de.mki.jchess.server.model.Client}.
     * @return Returns true if the {@link de.mki.jchess.server.implementation.threePersonChess.figures.King} is checked.
     */
    @Override
    public boolean isKingChecked(String clientId) throws Exception {
        // The king
        final boolean[] output = {false};
        King king = getFigures().stream()
                .filter(hexagonFigure -> hexagonFigure.getClient().getId().equals(clientId))
                .filter(hexagonFigure -> hexagonFigure instanceof King)
                .map(hexagonFigure -> (King) hexagonFigure)
                .findFirst().orElseThrow(() -> new Exception("This should not happen. If the player has no king he is defeated and cannot receive movement suggestions"));
        // Check if any figure can attack our kings hexagon
        getFigures().stream()
                .filter(hexagonFigure -> !hexagonFigure.isRemoved()) // Only active figures
                .filter(hexagonFigure -> !hexagonFigure.getClient().getId().equals(clientId)) // Enemy players
                .forEach(hexagonFigure -> hexagonFigure.getAttackableFields(this).forEach(hexagon -> {
                    if (hexagon.getNotation().equals(king.getPosition().getNotation()))
                        output[0] = true;
                }));
        return output[0];
    }

    /**
     * Returns if the {@link de.mki.jchess.server.implementation.threePersonChess.figures.King} of a
     * {@link de.mki.jchess.server.model.Client} is checked with a hypothetical figure layout. Used for predetermination if a movement is valid.
     * @param clientId The ID of the {@link de.mki.jchess.server.model.Client}.
     * @return Returns true if the {@link de.mki.jchess.server.implementation.threePersonChess.figures.King} would be checked.
     */
    @Override
    public boolean willKingBeChecked(String clientId) throws Exception {
        // The king
        final boolean[] output = {false};
        King king = getFigures().stream()
                .filter(hexagonFigure -> hexagonFigure.getClient().getId().equals(clientId))
                .filter(hexagonFigure -> hexagonFigure instanceof King)
                .map(hexagonFigure -> (King) hexagonFigure)
                .findFirst().orElseThrow(() -> new Exception("This should not happen. If the player has no king he is defeated and cannot receive movement suggestions"));
        // Check if any figure could attack our kings hexagon
        getFigures().stream()
                .filter(hexagonFigure -> !hexagonFigure.getHypotheticalRemoved()) // Only active figures
                .filter(hexagonFigure -> !hexagonFigure.getClient().getId().equals(clientId)) // Enemy players
                .forEach(hexagonFigure -> hexagonFigure.getHypotheticalAttackableFields(this).forEach(hexagon -> {
                    if (hexagon.getNotation().equals(king.getHypotheticalPosition().getNotation()))
                        output[0] = true;
                }));
        return output[0];
    }

    @Override
    public List<Hexagon> getPossibleFieldsToMove(String figureId) {
        List<Hexagon> hexagons = new ArrayList<>();
        getFigures().stream()
                .filter(hexagonFigure -> hexagonFigure.getId().equals(figureId))
                .filter(hexagonFigure -> !hexagonFigure.isRemoved())
                .findFirst().ifPresent(hexagonFigure -> {
            hexagons.addAll(hexagonFigure.getPossibleMovements(this));
            hexagons.addAll(hexagonFigure.getPossibleSpecialMovements(this));
        });
        return hexagons;
    }

    @Override
    public void performMovement(String figureId, String clientId, String targetFieldNotation, SimpMessagingTemplate simpMessagingTemplate) throws MoveNotAllowedException {
        List<Hexagon> possibleMovements = getPossibleFieldsToMove(figureId);
        // Throw an exception when the client ID does not match the figures owner id or when the target field is not in the list of possible moves.
        if (possibleMovements.stream()
                .filter(hexagon -> hexagon.getNotation().equals(targetFieldNotation))
                .count() == 0
                ||
                getFigures().stream()
                        .filter(hexagonFigure -> hexagonFigure.getId().equals(figureId))
                        .filter(hexagonFigure -> hexagonFigure.getClient().getId().equals(clientId))
                        .count() == 0)
            throw new MoveNotAllowedException();

        HistoryEntry historyEntry = new HistoryEntry();
        // Remove a figure from the chessboard if there is a figure on the target field
        getFigures().stream()
                .filter(hexagonFigure -> hexagonFigure.getPosition().getNotation().equals(targetFieldNotation))
                .findFirst().ifPresent(hexagonFigure -> {
                    historyEntry.getChessboardEvents().add(new FigureEvent().setFigureId(hexagonFigure.getId()).setEvent(FigureEvent.Event.REMOVED));
                    hexagonFigure.setRemoved(true);
                });

        // Move our figure to the target field
        getFigures().stream()
                .filter(hexagonFigure -> hexagonFigure.getId().equals(figureId))
                .findFirst().ifPresent(hexagonFigure -> {
                    historyEntry.getChessboardEvents().add(new MovementEvent().setFigureId(figureId).setFromNotation(hexagonFigure.getPosition().getNotation()).setToNotation(targetFieldNotation));
                    try {
                        hexagonFigure.setPosition((Hexagon) getFieldByNotation(targetFieldNotation));
                    } catch (Exception e) {
                        logger.error("", e);
                    }
                });
        // Add action to history
        getParentGame().getGameHistory().add(historyEntry);
        // Send event through websocket
        simpMessagingTemplate.convertAndSend("/game/" + getParentGame().getId(), historyEntry);
        // Change the active player and send it through websocket
        setCurrentPlayer(getCurrentPlayer().getNextClient());
        getParentGame().getPlayerList().forEach(client1 -> {
            PlayerChangedEvent playerChangedEvent = new PlayerChangedEvent().setItYouTurn(client1.equals(getCurrentPlayer()));
            simpMessagingTemplate.convertAndSend("/game/" + getParentGame().getId() + "/" + client1.getId(), playerChangedEvent);
        });
    }

    /**
     * Returns true if at least one position is occupied. Returns false if no position is occupied.
     * @param positions
     * @return
     */
    @Override
    public boolean areFieldsOccupied(List<Hexagon> positions) {
        return getFigures().stream()
                .filter(hexagonFigure -> !hexagonFigure.isRemoved())
                .filter(hexagonFigure -> {
                    for (Hexagon position : positions) {
                        if (hexagonFigure.getPosition().getNotation().equals(position.getNotation()))
                            return true;
                    }
                    return false;
                }).count() == positions.size();
    }

    @Override
    public boolean willFieldsOccupied(List<Hexagon> positions) {
        return getFigures().stream()
                .filter(hexagonFigure -> !hexagonFigure.getHypotheticalRemoved())
                .filter(hexagonFigure -> {
                    for (Hexagon position : positions) {
                        if (hexagonFigure.getHypotheticalPosition().getNotation().equals(position.getNotation()))
                            return true;
                    }
                    return false;
                }).count() == positions.size();
    }

    public List<Hexagon> getFreeFieldsForDiagonalMove(Hexagon sourceField, Direction direction) {
        return direction.getNecessaryFreeDirectionsForDiagonal().get()
                .stream()
                .map(freeDirection -> sourceField.getNeighbourByDirection(freeDirection))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public boolean isFigureOwnedByEnemy(Field targetField, Client client) {
        return getFigures().stream()
                .filter(o -> ((Figure) o).getHypotheticalPosition().getNotation().equals(targetField.getNotation()))
                .filter(o -> ((Figure) o).getClient().getId().equals(client.getId())).count() == 0;
    }
}
