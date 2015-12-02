package de.mki.jchess.server.model.websocket;

import de.mki.jchess.server.model.ChessboardEvent;

/**
 * Created by Igor on 14.11.2015.
 */
public class FigureEvent extends ChessboardEvent {

    String figureId;
    Event event;

    public String getFigureId() {
        return figureId;
    }

    public FigureEvent setFigureId(String figureId) {
        this.figureId = figureId;
        return this;
    }

    public Event getEvent() {
        return event;
    }

    public FigureEvent setEvent(Event event) {
        this.event = event;
        return this;
    }

    public enum Event {
        REMOVED, ADDED;

        private Event oppositeEvent;

        static {
            REMOVED.oppositeEvent = ADDED;
            ADDED.oppositeEvent = REMOVED;
        }

        public Event getOppositeEvent() {
            return oppositeEvent;
        }
    }
}
