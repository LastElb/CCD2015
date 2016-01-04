package de.mki.jchess.server.exception;

import de.mki.jchess.server.model.Game;

/**
 * Exception for a game that was not found.
 * Created by Igor on 11.11.2015.
 */
public class HostedGameNotFoundException extends Exception {
    /**
     * Constructor generating message
     * <code>
     *     Game with ID {id} not found.
     * </code>
     * @param id The ID of the {@link Game} that was not found
     */
    public HostedGameNotFoundException(String id) {
        super("Game with ID '" + id + "' not found.");
    }
}
