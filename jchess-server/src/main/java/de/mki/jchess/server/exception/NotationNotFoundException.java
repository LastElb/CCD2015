package de.mki.jchess.server.exception;

/**
 * Exception when a unknown field notation was entered
 * Created by Igor on 11.11.2015.
 */
public class NotationNotFoundException extends Exception {
    /**
     * Constructor generating the message
     * <code>
     *     Notation {notation} not found.
     * </code>
     * @param notation    The unknown notation.
     */
    public NotationNotFoundException(String notation) {
        super("Notation '" + notation + "' not found.");
    }
}
