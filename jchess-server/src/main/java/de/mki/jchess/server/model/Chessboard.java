package de.mki.jchess.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.mki.jchess.server.exception.MoveNotAllowedException;
import de.mki.jchess.server.implementation.threePersonChess.Hexagon;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor on 11.11.2015.
 * @param <T> The {@link Field} implementation of the game mode.
 */
public abstract class Chessboard<T extends Field> {
    public List<Figure<T>> figures;
    public List<T> fields;
    @JsonIgnore
    Client currentPlayer;
    @JsonIgnore
    Game parentGame;

    /**
     * Constructor initializing the parent {@link Game} and empty {@link List}s.
     * @param parentGame The parent {@link Game}.
     */
    public Chessboard(Game parentGame) {
        fields = new ArrayList<>();
        figures = new ArrayList<>();
        this.parentGame = parentGame;
    }

    /**
     * Returns a {@link List} of {@link Figure}s that were added to the {@link Chessboard}.
     * @return Returns a {@link List} of {@link Figure}s that were added to the {@link Chessboard}.
     */
    public List<Figure<T>> getFigures() {
        return figures;
    }

    /**
     * Returns a {@link List} of {@link Field}s that were added to the {@link Chessboard}.
     * @return Returns a {@link List} of {@link Field}s that were added to the {@link Chessboard}.
     */
    public List<T> getFields() {
        return fields;
    }

    /**
     * Adds a {@link Field} to the {@link Chessboard}. This method is used upon creation of the {@link Game}
     * @param field The {@link Field} you want to add.
     * @return Returns the instance of {@link Chessboard}.
     */
    public Chessboard addField(T field) {
        this.fields.add(field);
        return this;
    }

    /**
     * This function is looking for a {@link Field} that has the same notation as the parameter.
     * @param notation The {@link Field}'s notation as {@link String}
     * @return Returns the {@link Field} that has the same {@link Field#getNotation()} as the given parameter.
     * @throws Exception
     */
    public Field getFieldByNotation(String notation) throws Exception {
        return fields.stream().filter(field -> field.getNotation().equals(notation)).findFirst().orElseThrow(() -> new Exception("Notation not found"));
    }

    /**
     * @return Returns the active {@link Client player}. Only the active player is allowed to perform movements.
     */
    public Client getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Updates the active player. Only the active player is allowed to perform movements.
     * @param currentPlayer The {@link Client player} that is active next.
     * @return Returns the instance of {@link Chessboard}.
     */
    public Chessboard setCurrentPlayer(Client currentPlayer) {
        this.currentPlayer = currentPlayer;
        return this;
    }

    /**
     * Calculates all {@link Field}s a given {@link Figure} can move to.
     * @param figureId The id of the {@link Figure}.
     * @return Returns a {@link Field}s the {@link Figure} can move to.
     */
    public abstract List<T> getPossibleFieldsToMove(String figureId);

    /**
     * Performs a movement on the {@link Chessboard}.
     * If the move is allowed, the {@link Figure}s properties get updated and all {@link Client}s getting informed.
     * The history of the game will be updated as well.
     * @param figureId The id of the {@link Figure}.
     * @param clientId The id of the {@link Figure}s {@link Client owner}.
     * @param targetFieldNotation The {@link Field#getNotation() notation} of the {@link Field} the {@link Figure} wants to move to.
     * @param simpMessagingTemplate Template for sending websocket messages.
     * @throws MoveNotAllowedException
     */
    public abstract void performMovement(String figureId, String clientId, String targetFieldNotation, SimpMessagingTemplate simpMessagingTemplate) throws MoveNotAllowedException;

    /**
     * @return Returns the {@link Game} for the current {@link Chessboard}.
     */
    public Game getParentGame() {
        return parentGame;
    }

    /**
     * Determines if at least one {@link Field} is occupied.
     * @param positions A {@link List} of {@link Field}s to check.
     * @return Returns true if at least one position is occupied. Returns false if no position is occupied.
     */
    public abstract boolean areFieldsOccupied(List<T> positions);

    /**
     * Determines if at least one {@link Field} would be occupied with the {@link Figure#getHypotheticalPosition()} and {@link Figure#getHypotheticalRemoved()} of every {@link Figure}.
     * @param positions A {@link List} of {@link Field}s to check.
     * @return
     */
    public abstract boolean willFieldsBeOccupied(List<T> positions);

    /**
     * Determines if the {@link Client}'s king is checked with the {@link Figure#getPosition()} and {@link Figure#isRemoved()} of every other {@link Figure}.
     * @param clientId The id of the player. The is saved in {@link Client}.
     * @return Returns true if the king is checked.
     * @throws Exception
     */
    public abstract boolean isKingChecked(String clientId) throws Exception;

    /**
     * Determines if the {@link Client}'s king will be checked with the {@link Figure#getHypotheticalPosition()} and {@link Figure#getHypotheticalRemoved()} of every other {@link Figure}.
     * @param clientId The id of the player. The is saved in {@link Client}.
     * @return Returns true if the king will be checked.
     * @throws Exception
     */
    public abstract boolean willKingBeChecked(String clientId) throws Exception;
}
