package de.mki.jchess.commons.websocket;

/**
 * Event for websockets to signal that a player is defeated. The player receiving the message
 * and having {@link #areYouDefeated} set to true is out of the game. The other clients of the game just get the name
 * of the defeated player.
 * Resolves CCD2015-40.
 * Created by Igor on 14.11.2015.
 */
public class PlayerDefeatedEvent {
    boolean areYouDefeated;
    String name;

    public boolean isAreYouDefeated() {
        return areYouDefeated;
    }

    public PlayerDefeatedEvent setAreYouDefeated(boolean areYouDefeated) {
        this.areYouDefeated = areYouDefeated;
        return this;
    }

    public String getName() {
        return name;
    }

    public PlayerDefeatedEvent setName(String name) {
        this.name = name;
        return this;
    }
}
