package de.mki.jchess.server.exception;

/**
 * Exception when a {@link de.mki.jchess.commons.Client} wants to perform an invalid move with a
 * {@link de.mki.jchess.server.model.Figure}
 * Created by Igor on 14.11.2015.
 */
public class MoveNotAllowedException extends Exception {
    /**
     * Constructor generating the message
     * <code>
     *     The received move is not allowed.
     * </code>
     */
    public MoveNotAllowedException() {
        super("The received move is not allowed.");
    }
}
