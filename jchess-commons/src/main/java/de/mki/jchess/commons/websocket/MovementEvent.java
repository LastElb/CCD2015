package de.mki.jchess.commons.websocket;

/**
 * Movement event that is send through websockets.
 * Created by Igor on 14.11.2015.
 */
public class MovementEvent implements ChessboardEvent {
    String figureId;
    String fromNotation;
    String toNotation;

    /**
     * The id of the {@link de.mki.jchess.server.model.Figure}.
     * @return The id of the {@link de.mki.jchess.server.model.Figure}.
     */
    public String getFigureId() {
        return figureId;
    }

    /**
     * Set the id of the {@link de.mki.jchess.server.model.Figure}.
     * @param figureId The id of the {@link de.mki.jchess.server.model.Figure}.
     * @return Returns the updated instance of {@link MovementEvent}.
     */
    public MovementEvent setFigureId(String figureId) {
        this.figureId = figureId;
        return this;
    }

    /**
     * Gets the notation of the source {@link de.mki.jchess.server.model.Field}.
     * @return Gets the notation of the source {@link de.mki.jchess.server.model.Field}.
     */
    public String getFromNotation() {
        return fromNotation;
    }

    /**
     * Set the notation of the source {@link de.mki.jchess.server.model.Field}.
     * @param fromNotation The notation of the source {@link de.mki.jchess.server.model.Field} as {@link String}.
     * @return Returns the updated instance of {@link MovementEvent}.
     */
    public MovementEvent setFromNotation(String fromNotation) {
        this.fromNotation = fromNotation;
        return this;
    }

    /**
     * Gets the notation of the target {@link de.mki.jchess.server.model.Field}.
     * @return Gets the notation of the target {@link de.mki.jchess.server.model.Field}.
     */
    public String getToNotation() {
        return toNotation;
    }

    /**
     * Set the notation of the target {@link de.mki.jchess.server.model.Field}.
     * @param toNotation The notation of the target {@link de.mki.jchess.server.model.Field} as {@link String}.
     * @return Returns the updated instance of {@link MovementEvent}.
     */
    public MovementEvent setToNotation(String toNotation) {
        this.toNotation = toNotation;
        return this;
    }
}
