package de.mki.jchess.server.model.websocket;

/**
 * Created by Igor on 14.11.2015.
 */
public class PlayerChangedEvent {
    boolean isItYouTurn;

    public boolean isItYouTurn() {
        return isItYouTurn;
    }

    public PlayerChangedEvent setItYouTurn(boolean itYouTurn) {
        isItYouTurn = itYouTurn;
        return this;
    }
}
