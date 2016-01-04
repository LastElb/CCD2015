package de.mki.jchess.server.exception;

/**
 * Exception when a {@link de.mki.jchess.commons.Client} wants to connect to a full
 * {@link de.mki.jchess.server.model.Game}.
 * Created by Igor on 11.11.2015.
 */
public class TooManyPlayersException extends Exception {
    /**
     * Constructor generating the message
     * <code>
     *     There are already enough players registered for the game. Maybe join as observer?
     * </code>
     */
    public TooManyPlayersException() {
        super("There are already enough players registered for the game. Maybe join as observer?");
    }
}
