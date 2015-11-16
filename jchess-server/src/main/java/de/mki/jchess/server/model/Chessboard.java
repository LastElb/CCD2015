package de.mki.jchess.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.mki.jchess.server.exception.MoveNotAllowedException;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor on 11.11.2015.
 */
public abstract class Chessboard<T extends Field> {
    public List<Figure<T>> figures;
    public List<T> fields;
    @JsonIgnore
    Client currentPlayer;
    @JsonIgnore
    Game parentGame;

    public Chessboard(Game parentGame) {
        fields = new ArrayList<>();
        figures = new ArrayList<>();
        this.parentGame = parentGame;
    }

    public List<Figure<T>> getFigures() {
        return figures;
    }

    public List<T> getFields() {
        return fields;
    }

    public Chessboard addField(T field) {
        this.fields.add(field);
        return this;
    }

    public Field getFieldByNotation(String notation) throws Exception {
        return fields.stream().filter(field -> field.getNotation().equals(notation)).findFirst().orElseThrow(() -> new Exception("Notation not found"));
    }

    public Client getCurrentPlayer() {
        return currentPlayer;
    }

    public Chessboard setCurrentPlayer(Client currentPlayer) {
        this.currentPlayer = currentPlayer;
        return this;
    }

    public abstract List<T> getPossibleFieldsToMove(String figureId);

    public abstract void performMovement(String figureId, String targetFieldNotation, SimpMessagingTemplate simpMessagingTemplate) throws MoveNotAllowedException;

    public Game getParentGame() {
        return parentGame;
    }

    public abstract boolean areFieldsOccupied(List<T> positions);
}
