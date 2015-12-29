package de.mki.jchess.commons.websocket;

/**
 * Event for websockets. This message is send when the active {@link de.mki.jchess.server.model.Client Player} changes
 * Created by Igor on 14.11.2015.
 */
public class PlayerChangedEvent {
    boolean isItYouTurn;

    /**
     * Message specific for a player. Returns whether it's its turn on the game.
     * @return Returns a boolean indicating if it is the receiving {@link de.mki.jchess.server.model.Client Player}'s turn
     */
    public boolean isItYouTurn() {
        return isItYouTurn;
    }

    /**
     * Set if it is the turn of the receiving player.
     * @param itYouTurn Boolean
     * @return Returns the updated instance of the {@link PlayerChangedEvent}.
     */
    public PlayerChangedEvent setItYouTurn(boolean itYouTurn) {
        isItYouTurn = itYouTurn;
        return this;
    }
}
