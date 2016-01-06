package de.mki.jchess.commons.websocket;

/**
 * Event for websockets. This message is send when the active Player changes
 * Created by Igor on 14.11.2015.
 */
public class PlayerChangedEvent {

    boolean isItYouTurn;
    String nickname;
    String team;

    /**
     * Message specific for a player. Returns whether it's its turn on the game.
     * @return Returns a boolean indicating if it is the receiving Player's turn
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

    /**
     * Get the nickname of the active player.
     * @return Returns the nickname of the current {@link de.mki.jchess.commons.Client Player}.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Sets the name of the current active {@link de.mki.jchess.commons.Client Player}.
     * @param nickname    The name of the player as String.
     * @return Returns the updated instance of the {@link PlayerChangedEvent}.
     */
    public PlayerChangedEvent setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    /**
     * Returns the team of the active {@link de.mki.jchess.commons.Client Player}.
     * @return Returns the team of the active {@link de.mki.jchess.commons.Client Player}.
     */
    public String getTeam() {
        return team;
    }

    /**
     * Sets the team of the active {@link de.mki.jchess.commons.Client Player}.
     * @param team    The color of the team.
     * @return Returns the updated instance of the {@link PlayerChangedEvent}.
     */
    public PlayerChangedEvent setTeam(String team) {
        this.team = team;
        return this;
    }
}
