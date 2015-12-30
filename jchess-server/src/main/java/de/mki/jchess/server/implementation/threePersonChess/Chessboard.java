package de.mki.jchess.server.implementation.threePersonChess;

import de.mki.jchess.commons.Client;
import de.mki.jchess.commons.Field;
import de.mki.jchess.server.implementation.threePersonChess.figures.Rook;
import de.mki.jchess.server.model.Figure;
import de.mki.jchess.commons.HistoryEntry;
import de.mki.jchess.server.exception.MoveNotAllowedException;
import de.mki.jchess.server.exception.NotationNotFoundException;
import de.mki.jchess.server.implementation.threePersonChess.figures.King;
import de.mki.jchess.server.model.*;
import de.mki.jchess.commons.websocket.FigureEvent;
import de.mki.jchess.commons.websocket.MovementEvent;
import de.mki.jchess.commons.websocket.PlayerChangedEvent;
import de.mki.jchess.commons.websocket.PlayerDefeatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implements some logic for three person chess.
 * Created by Igor on 13.11.2015.
 */
public class Chessboard extends de.mki.jchess.server.model.Chessboard<Hexagon> {

    private static final Logger logger = LoggerFactory.getLogger(Chessboard.class);

    /**
     * Default constructor
     * @param parentGame The {@link ThreePersonGame} this {@link Chessboard} belongs to.
     */
    public Chessboard(Game parentGame) {
        super(parentGame);
    }

    /**
     * Find a field by its row and column.
     * @param column The field's column
     * @param row The field's row
     * @return Returns a {@link Hexagon} if there exists one with the column - row - combination.
     * @throws NotationNotFoundException
     */
    public Hexagon getFieldByNotation(int column, int row) throws NotationNotFoundException {
        return getFields().stream()
                .filter(field -> field.column == column && field.row == row)
                .findFirst()
                .orElseThrow(() -> new NotationNotFoundException("column=" + column + ", row=" + row));
    }

    /**
     * Returns if the {@link de.mki.jchess.server.implementation.threePersonChess.figures.King}
     * of a {@link Client} is checked with the current figure layout.
     * @param clientId The ID of the {@link Client}.
     * @return Returns true if the {@link de.mki.jchess.server.implementation.threePersonChess.figures.King} is checked.
     */
    @Override
    public boolean isKingChecked(String clientId) {
        final boolean[] output = {false};

        // Find our king
        Optional<King> king = getFigures().stream()
                // Only the figures of the client
                .filter(hexagonFigure -> hexagonFigure.getClient().getId().equals(clientId))
                // Filter all out that are not our king
                .filter(hexagonFigure -> hexagonFigure instanceof King)
                .map(hexagonFigure -> (King) hexagonFigure)
                .findFirst();

        // Check if any figure can attack our kings hexagon
        king.ifPresent(king1 -> getFigures().stream().parallel()
                // Filter out inactive figures
                .filter(hexagonFigure -> !hexagonFigure.isRemoved())
                // Only use enemy figures
                .filter(hexagonFigure -> !hexagonFigure.getClient().getId().equals(clientId))
                // Go with each figure and check if it could attack our king
                .forEach(hexagonFigure -> hexagonFigure.getAttackableFields(this).forEach(hexagon -> {
                    if (hexagon.getNotation().equals(king1.getPosition().getNotation())) {
                        output[0] = true;
                        logger.trace("Figure {} from {} on position {} can beat the king of {} on {}",
                                hexagonFigure.getName(),
                                hexagonFigure.getClient().getTeam(),
                                hexagonFigure.getPosition().getNotation(),
                                king1.getClient().getTeam(),
                                king1.getPosition().getNotation());
                    }
                })));
        return output[0];
    }

    /**
     * Returns if the {@link de.mki.jchess.server.implementation.threePersonChess.figures.King} of a
     * {@link Client} is checked with a hypothetical figure layout.0
     * Used for predetermination if a movement is valid.
     * @param clientId The ID of the {@link Client}.
     * @return Returns true if the {@link de.mki.jchess.server.implementation.threePersonChess.figures.King}
     * would be checked.
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
        king.ifPresent(king1 -> getFigures().stream().parallel()
                // Only active figures
                .filter(hexagonFigure -> !hexagonFigure.getHypotheticalRemoved())
                // Enemy players figures
                .filter(hexagonFigure -> !hexagonFigure.getClient().getId().equals(clientId))
                // Go through all attackable fields
                .forEach(hexagonFigure -> hexagonFigure.getHypotheticalAttackableFields(this).forEach(hexagon -> {
                    if (hexagon.getNotation().equals(king1.getHypotheticalPosition().getNotation())) {
                        output[0] = true;
                        logger.trace("King would be checked by {} on field {}", hexagonFigure.getName(), hexagonFigure.getPosition().getNotation());
                    }
                })));
        return output[0];
    }

    /**
     * {@inheritDoc}
     * @param figureId The id of the {@link Figure}.
     * @return Returns a combined {@link List} of {@link Figure#getPossibleMovements(de.mki.jchess.server.model.Chessboard)}
     * and {@link Figure#getPossibleSpecialMovements(de.mki.jchess.server.model.Chessboard)}.
     */
    @Override
    public List<Hexagon> getPossibleFieldsToMove(String figureId) {
        List<Hexagon> hexagons = new ArrayList<>();
        getFigures().stream()
                // Only active figures
                .filter(hexagonFigure -> !hexagonFigure.isRemoved())
                // Only the desired figure
                .filter(hexagonFigure -> hexagonFigure.getId().equals(figureId))
                .findFirst().ifPresent(hexagonFigure -> {
            hexagons.addAll(hexagonFigure.getPossibleMovements(this));
            hexagons.addAll(hexagonFigure.getPossibleSpecialMovements(this));
        });
        return hexagons;
    }

    private List<List<Hexagon>> getMovementFieldsSeparated(String figureId) {
        List<List<Hexagon>> hexagons = new ArrayList<>();
        getFigures().stream()
                // Only active figures
                .filter(hexagonFigure -> !hexagonFigure.isRemoved())
                // Only the desired figure
                .filter(hexagonFigure -> hexagonFigure.getId().equals(figureId))
                .findFirst().ifPresent(hexagonFigure -> {
            hexagons.add(hexagonFigure.getPossibleMovements(this));
            hexagons.add(hexagonFigure.getPossibleSpecialMovements(this));
        });
        return hexagons;
    }

    /**
     * {@inheritDoc}
     * @param figureId The id of the {@link Figure}.
     * @param clientId The id of the {@link Figure}s {@link Client owner}.
     * @param targetFieldNotation The {@link Field#getNotation() notation} of the {@link Field} the {@link Figure} wants to move to.
     * @param simpMessagingTemplate Template for sending websocket messages.
     * @throws MoveNotAllowedException
     */
    @Override
    public void performMovement(String figureId, String clientId, String targetFieldNotation, SimpMessagingTemplate simpMessagingTemplate) throws MoveNotAllowedException {
        // Only the current player is allowed to do moves.
        // Fixes CCD2015-50
        if (!clientId.equals(getCurrentPlayer().getId()))
            throw new MoveNotAllowedException();

        List<List<Hexagon>> possibleMovements = getMovementFieldsSeparated(figureId);
        List<Hexagon> flatPossibleMovements = possibleMovements.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        // Throw an exception when the client ID does not match the figures owner id
        // or when the target field is not in the list of possible moves.
        if (flatPossibleMovements.stream()
                .filter(hexagon -> hexagon.getNotation().equals(targetFieldNotation))
                .count() == 0
                ||
                getFigures().stream()
                        // Only figures, that are sill active.
                        .filter(hexagonFigure -> !hexagonFigure.isRemoved())
                        // Only figures equals to the given id
                        .filter(hexagonFigure -> hexagonFigure.getId().equals(figureId))
                        // Only the clients figures
                        .filter(hexagonFigure -> hexagonFigure.getClient().getId().equals(clientId))
                        .count() == 0)
            throw new MoveNotAllowedException();

        HistoryEntry historyEntry = new HistoryEntry();
        historyEntry.setPlayer(getCurrentPlayer());
        // Remove a figure from the chessboard if there is a figure on the target field
        getFigures().stream()
                // Only figures, that are sill active.
                // Fixes CCD2015-52
                .filter(hexagonFigure -> !hexagonFigure.isRemoved())
                // Only figures on the target hexagon
                .filter(hexagonFigure -> hexagonFigure.getPosition().getNotation().equals(targetFieldNotation))
                // Only enemy figures
                // Else the king gets deleted when doing castling
                .filter(hexagonFigure -> !hexagonFigure.getClient().getId().equals(clientId))
                .findFirst().ifPresent(hexagonFigure -> {
                    historyEntry.getChessboardEvents().add(new FigureEvent().setFigureId(hexagonFigure.getId()).setEvent(FigureEvent.Event.REMOVED));
                    hexagonFigure.setRemoved(true);
                });

        // Move our figure to the target field
        getFigures().stream()
                // Only figures, that are sill active.
                .filter(hexagonFigure -> !hexagonFigure.isRemoved())
                // Only figures on the source hexagon
                .filter(hexagonFigure -> hexagonFigure.getId().equals(figureId))
                .filter(hexagonFigure -> {
                    // Do additional moves for special movements here
                    // This method should always return true
                    List<String> specialTargetFields = possibleMovements.get(1).stream()
                            .map(Hexagon::getNotation)
                            .collect(Collectors.toList());
                    if (hexagonFigure instanceof King && specialTargetFields.contains(targetFieldNotation)) {
                        Rook rook = ((King) hexagonFigure).findRookForCastling(this, targetFieldNotation);
                        historyEntry.getChessboardEvents().add(new MovementEvent().setFigureId(rook.getId()).setFromNotation(rook.getPosition().getNotation()).setToNotation(hexagonFigure.getPosition().getNotation()));
                        rook.setPosition(hexagonFigure.getPosition());
                    }
                    if (hexagonFigure instanceof Rook && specialTargetFields.contains(targetFieldNotation)) {
                        King king = ((Rook) hexagonFigure).moveKingForCastling(this);
                        historyEntry.getChessboardEvents().add(new MovementEvent().setFigureId(king.getId()).setFromNotation(king.getHypotheticalPosition().getNotation()).setToNotation(king.getPosition().getNotation()));
                        king.setHypotheticalPosition(null);
                    }
                    return true;
                })
                .findFirst().ifPresent(hexagonFigure -> {
                    historyEntry.getChessboardEvents().add(new MovementEvent().setFigureId(figureId).setFromNotation(hexagonFigure.getPosition().getNotation()).setToNotation(targetFieldNotation));
                    try {
                        hexagonFigure.setPosition((Hexagon) getFieldByNotation(targetFieldNotation));
                    } catch (NotationNotFoundException e) {
                        logger.error("", e);
                    }
                });

        // Add action to history
        getParentGame().getGameHistory().add(historyEntry);
        // Send event through websocket
        Map<String, Object> webSocketHeader = new LinkedHashMap<>();
        webSocketHeader.put("data-type", "HistoryEntry");
        simpMessagingTemplate.convertAndSend("/game/" + getParentGame().getId(), historyEntry, webSocketHeader);
        // Change the active player and send it through websocket
        setCurrentPlayer(getCurrentPlayer().getNextClient());
        checkIfCurrentPlayerIsDefeated(simpMessagingTemplate);
        while (getCurrentPlayer().isDefeated()) {
            setCurrentPlayer(getCurrentPlayer().getNextClient());
            checkIfCurrentPlayerIsDefeated(simpMessagingTemplate);
        }

        getParentGame().getPlayerList().stream()
                .filter(client -> !client.isDefeated())
                .forEach(client -> {
                    Map<String, Object> webSocketDataHeader = new LinkedHashMap<>();
                    webSocketDataHeader.put("data-type", "PlayerChangedEvent");

                    PlayerChangedEvent playerChangedEvent = new PlayerChangedEvent().setItYouTurn(client.equals(getCurrentPlayer()));
                    simpMessagingTemplate.convertAndSend("/game/" + getParentGame().getId() + "/" + client.getId(), playerChangedEvent, webSocketDataHeader);
                });
    }

    /**
     * {@inheritDoc}
     * @param positions A {@link List} of {@link Hexagon}s to check.
     * @return Returns true if at least one position is occupied. Returns false if no position is occupied.
     */
    @Override
    public boolean areFieldsOccupied(List<Hexagon> positions) {
        return getFigures().stream()
                // Filter removed figures out
                .filter(hexagonFigure -> !hexagonFigure.isRemoved())
                // Just keep the figures, that have the same position as one in our "positions" List.
                .filter(hexagonFigure -> {
                    for (Hexagon position : positions) {
                        if (hexagonFigure.getPosition().getNotation().equals(position.getNotation()))
                            return true;
                    }
                    return false;
                }).count() == positions.size();
    }

    /**
     * {@inheritDoc}
     * @param positions A {@link List} of {@link Field}s to check.
     * @return Returns true if at least one position is occupied. Returns false if no position is occupied.
     */
    @Override
    public boolean willFieldsBeOccupied(List<Hexagon> positions) {
        return getFigures().stream()
                // Filter removed figures out
                .filter(hexagonFigure -> !hexagonFigure.getHypotheticalRemoved())
                // Just keep the figures, that have the same position as one in our "positions" List.
                .filter(hexagonFigure -> {
                    for (Hexagon position : positions) {
                        if (hexagonFigure.getHypotheticalPosition().getNotation().equals(position.getNotation()))
                            return true;
                    }
                    return false;
                }).count() == positions.size();
    }

    /**
     * In our hexagonal three person chess we need two free bordering {@link Hexagon}s for a diagonal move.
     * This function calculates these two {@link Hexagon}s for a given {@link Hexagon} and {@link Direction}.
     * @param sourceField The source field where the {@link Figure} currently stands on.
     * @param direction The diagonal {@link Direction} the figure wants to move to.
     * @return Returns a {@link List} of {@link Hexagon}.
     */
    public List<Hexagon> getFreeFieldsForDiagonalMove(Hexagon sourceField, Direction direction) {
        return direction.getNecessaryFreeDirectionsForDiagonal().get().stream()
                // Map to {@link Optional} of {@link Hexagon} by getting possible neighbours.
                .map(sourceField::getNeighbourByDirection)
                // Get the neighbour field as non {@link Optional} when present
                .filter(Optional::isPresent)
                .map(Optional::get)
                // Return the {@link List}
                .collect(Collectors.toList());
    }

    /**
     * Decides whether the {@link Figure} on a field is owned by an enemy or not.
     * @param targetField The {@link Field} the figure stand on.
     * @param client The {@link Client} from which point of view we are looking
     * @return Returns true if there is no figure on the field or the {@link Figure} on the {@link Field}
     * does not belong to the {@link Client} in the parameter.
     */
    public boolean isFigureOwnedByEnemy(Field targetField, Client client) {
        return getFigures().stream()
                // Just use non removed figures. Fixes CCD2015-51
                .filter(o -> !((Figure) o).isRemoved())
                // Just those figures on the target position
                .filter(o -> ((Figure) o).getHypotheticalPosition().getNotation().equals(targetField.getNotation()))
                // Just enemy figures
                .filter(o -> ((Figure) o).getClient().getId().equals(client.getId()))
                .count() == 0;
    }

    /**
     * Decides whether the {@link #getCurrentPlayer()} is defeated or not.
     * @param simpMessagingTemplate Template for websocket messages
     */
    void checkIfCurrentPlayerIsDefeated(SimpMessagingTemplate simpMessagingTemplate) {
        Client client = getCurrentPlayer();
        if (client.isDefeated())
            return;
        // If the king is checked, go through all active figures and check if we can do any moves.
        // If we can't do any moves the player is defeated
        if (isKingChecked(client.getId()) && getFigures().stream()
                // Just the players figures
                .filter(hexagonFigure -> hexagonFigure.getClient().getId().equals(client.getId()))
                // Just non removed figures
                .filter(hexagonFigure -> !hexagonFigure.isRemoved())
                // Change type to a list of hexagon
                .map(hexagonFigure -> hexagonFigure.getPossibleMovements(this))
                // Convert list of list of hexagon to list of hexagon
                .flatMap(Collection::stream)
                .count() == 0) {
            client.setDefeated(true);

            // Remove the king of the player.
            // Fixes CCD2015-56
            getFigures().stream().parallel()
                    .filter(o -> o instanceof King)
                    .filter(o1 -> o1.getClient().getId().equals(client.getId()))
                    .findFirst().get().setRemoved(true);

            // Send the defeated message to all clients
            getParentGame().getPlayerList().stream()
                    .forEach(client1 -> {
                        PlayerDefeatedEvent playerDefeatedEvent = new PlayerDefeatedEvent()
                                .setAreYouDefeated(client1.isDefeated()).setName(client.getNickname());
                        Map<String, Object> webSocketDataHeader = new LinkedHashMap<>();
                        webSocketDataHeader.put("data-type", "PlayerDefeatedEvent");
                        simpMessagingTemplate.convertAndSend("/game/" + getParentGame().getId() + "/" + client1.getId(), playerDefeatedEvent, webSocketDataHeader);
                    });
        }

    }
}
