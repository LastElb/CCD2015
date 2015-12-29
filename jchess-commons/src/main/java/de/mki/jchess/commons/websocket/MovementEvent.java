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
     * The id of the Figure.
     * @return The id of the Figure.
     */
    public String getFigureId() {
        return figureId;
    }

    /**
     * Set the id of the Figure.
     * @param figureId The id of the Figure.
     * @return Returns the updated instance of {@link MovementEvent}.
     */
    public MovementEvent setFigureId(String figureId) {
        this.figureId = figureId;
        return this;
    }

    /**
     * Gets the notation of the source Figure.
     * @return Gets the notation of the source Figure.
     */
    public String getFromNotation() {
        return fromNotation;
    }

    /**
     * Set the notation of the source Figure.
     * @param fromNotation The notation of the source Figure as {@link String}.
     * @return Returns the updated instance of {@link MovementEvent}.
     */
    public MovementEvent setFromNotation(String fromNotation) {
        this.fromNotation = fromNotation;
        return this;
    }

    /**
     * Gets the notation of the target Figure.
     * @return Gets the notation of the target Figure.
     */
    public String getToNotation() {
        return toNotation;
    }

    /**
     * Set the notation of the target Figure.
     * @param toNotation The notation of the target Figure as {@link String}.
     * @return Returns the updated instance of {@link MovementEvent}.
     */
    public MovementEvent setToNotation(String toNotation) {
        this.toNotation = toNotation;
        return this;
    }
}
