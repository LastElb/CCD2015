package de.mki.jchess.server.implementation.threePersonChess;

import de.mki.jchess.server.exception.MoveNotAllowedException;
import de.mki.jchess.server.exception.NotationNotFoundException;
import de.mki.jchess.server.implementation.threePersonChess.figures.King;
import de.mki.jchess.server.model.*;
import de.mki.jchess.server.model.websocket.FigureEvent;
import de.mki.jchess.server.model.websocket.MovementEvent;
import de.mki.jchess.server.model.websocket.PlayerChangedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.Collection;
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

    public Hexagon getFieldByNotation(int column, int row) throws NotationNotFoundException {
        return getFields().stream().filter(field -> field.column == column && field.row == row).findFirst().orElseThrow(() -> new NotationNotFoundException("column=" + column + ", row=" + row));
    }

    /**
     * Returns if the {@link de.mki.jchess.server.implementation.threePersonChess.figures.King} of a {@link de.mki.jchess.server.model.Client} is checked with the current figure layout.
     * @param clientId The ID of the {@link de.mki.jchess.server.model.Client}.
     * @return Returns true if the {@link de.mki.jchess.server.implementation.threePersonChess.figures.King} is checked.
     */
    @Override
    public boolean isKingChecked(String clientId) {
        // The king
        final boolean[] output = {false};
        Optional<King> king = getFigures().stream()
                .filter(hexagonFigure -> hexagonFigure.getClient().getId().equals(clientId))
                .filter(hexagonFigure -> hexagonFigure instanceof King)
                .map(hexagonFigure -> (King) hexagonFigure)
                .findFirst();
        // Check if any figure can attack our kings hexagon
        king.ifPresent(king1 -> getFigures().stream()
                .filter(hexagonFigure -> !hexagonFigure.isRemoved()) // Only active figures
                .filter(hexagonFigure -> !hexagonFigure.getClient().getId().equals(clientId)) // Enemy players
                .forEach(hexagonFigure -> hexagonFigure.getAttackableFields(this).forEach(hexagon -> {
                    if (hexagon.getNotation().equals(king1.getPosition().getNotation()))
                        output[0] = true;
                })));
        return output[0];
    }

    /**
     * Returns if the {@link de.mki.jchess.server.implementation.threePersonChess.figures.King} of a
     * {@link de.mki.jchess.server.model.Client} is checked with a hypothetical figure layout. Used for predetermination if a movement is valid.
     * @param clientId The ID of the {@link de.mki.jchess.server.model.Client}.
     * @return Returns true if the {@link de.mki.jchess.server.implementation.threePersonChess.figures.King} would be checked.
     */
    @Override
    public boolean willKingBeChecked(String clientId) {
        // The king
        final boolean[] output = {false};
        Optional<King> king = getFigures().stream()
                .filter(hexagonFigure -> hexagonFigure.getClient().getId().equals(clientId))
                .filter(hexagonFigure -> hexagonFigure instanceof King)
                .map(hexagonFigure -> (King) hexagonFigure)
                .findFirst();
        // Check if any figure could attack our kings hexagon
        king.ifPresent(king1 -> getFigures().stream()
                .filter(hexagonFigure -> !hexagonFigure.getHypotheticalRemoved()) // Only active figures
                .filter(hexagonFigure -> !hexagonFigure.getClient().getId().equals(clientId)) // Enemy players
                .forEach(hexagonFigure -> hexagonFigure.getHypotheticalAttackableFields(this).forEach(hexagon -> {
                    if (hexagon.getNotation().equals(king1.getHypotheticalPosition().getNotation()))
                        output[0] = true;
                })));
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
        checkIfCurrentPlayerIsDefeated();
        while (getCurrentPlayer().isDefeated()) {
            setCurrentPlayer(getCurrentPlayer().getNextClient());
            checkIfCurrentPlayerIsDefeated();
        }
        getParentGame().getPlayerList().stream()
                .filter(client -> !client.isDefeated())
                .forEach(client -> {
                    PlayerChangedEvent playerChangedEvent = new PlayerChangedEvent().setItYouTurn(client.equals(getCurrentPlayer()));
                    simpMessagingTemplate.convertAndSend("/game/" + getParentGame().getId() + "/" + client.getId(), playerChangedEvent);
                });
    }

    /**
     * Determines if at least one {@link Field} is occupied.
     * @param positions A {@link List} of {@link Hexagon}s to check.
     * @return Returns true if at least one position is occupied. Returns false if no position is occupied.
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
    public boolean willFieldsBeOccupied(List<Hexagon> positions) {
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
                .map(sourceField::getNeighbourByDirection)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public boolean isFigureOwnedByEnemy(Field targetField, Client client) {
        return getFigures().stream()
                .filter(o -> ((Figure) o).getHypotheticalPosition().getNotation().equals(targetField.getNotation()))
                .filter(o -> ((Figure) o).getClient().getId().equals(client.getId())).count() == 0;
    }

    void checkIfCurrentPlayerIsDefeated() {
        Client client = getCurrentPlayer();
        if (client.isDefeated())
            return;
        // If the king is checked, go through all active figures and check if we can do any moves. If we can't do any moves
        // the player is defeated
        if (isKingChecked(client.getId()) && getFigures().stream()
                .filter(hexagonFigure -> hexagonFigure.getClient().getId().equals(client.getId()))
                .filter(hexagonFigure -> !hexagonFigure.isRemoved())
                .map(hexagonFigure -> hexagonFigure.getPossibleMovements(this))
                .flatMap(Collection::stream)
                .count() == 0)
            client.setDefeated(true);
    }
}
