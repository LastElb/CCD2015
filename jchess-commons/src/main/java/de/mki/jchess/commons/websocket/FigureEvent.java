package de.mki.jchess.commons.websocket;

/**
 * Event for websocket communication. Indicates that the state of a {@link de.mki.jchess.server.model.Figure} has changed.
 * Created by Igor on 14.11.2015.
 */
public class FigureEvent implements ChessboardEvent {

    String figureId;
    Event event;

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
     * @return Returns an updated instance of {@link FigureEvent}.
     */
    public FigureEvent setFigureId(String figureId) {
        this.figureId = figureId;
        return this;
    }

    /**
     * Gets the event the Figure had.
     * @return Gets the event the Figure had.
     */
    public Event getEvent() {
        return event;
    }

    /**
     * Sets the event a Figure had.
     * @param event The event the Figure had.
     * @return Returns an updated instance of {@link FigureEvent}.
     */
    public FigureEvent setEvent(Event event) {
        this.event = event;
        return this;
    }

    /**
     * Enum to display the state change of a figure
     */
    public enum Event {
        REMOVED, ADDED;

        private Event oppositeEvent;

        static {
            REMOVED.oppositeEvent = ADDED;
            ADDED.oppositeEvent = REMOVED;
        }

        /**
         * Returns the opposite of the enum.
         * @return Returns {@link #ADDED} if {@link #REMOVED} or vice versa.
         */
        public Event getOppositeEvent() {
            return oppositeEvent;
        }
    }
}
