package de.mki.jchess.server.model.websocket;

import de.mki.jchess.server.model.ChessboardEvent;

/**
 * Created by Igor on 14.11.2015.
 */
public class MovementEvent implements ChessboardEvent {
    String figureId;
    String fromNotation;
    String toNotation;

    public String getFigureId() {
        return figureId;
    }

    public MovementEvent setFigureId(String figureId) {
        this.figureId = figureId;
        return this;
    }

    public String getFromNotation() {
        return fromNotation;
    }

    public MovementEvent setFromNotation(String fromNotation) {
        this.fromNotation = fromNotation;
        return this;
    }

    public String getToNotation() {
        return toNotation;
    }

    public MovementEvent setToNotation(String toNotation) {
        this.toNotation = toNotation;
        return this;
    }
}
