package de.mki.jchess.server.exception;

/**
 * Created by Igor on 14.11.2015.
 */
public class MoveNotAllowedException extends Exception {
    public MoveNotAllowedException() {
        super("The received move is not allowed.");
    }
}
