package de.mki.jchess.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.mki.jchess.server.exception.MoveNotAllowedException;

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

    public Chessboard() {
        fields = new ArrayList<>();
        figures = new ArrayList<>();
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

    public abstract boolean isKingCheckedAtPosition(Figure<T> king, T field);

    public abstract List<T> getPossibleFieldsToMove(String figureId);

    public abstract void performMovement(String figureId, String targetFieldNotation) throws MoveNotAllowedException;
}
