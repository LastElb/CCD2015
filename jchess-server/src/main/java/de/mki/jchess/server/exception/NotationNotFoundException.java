package de.mki.jchess.server.exception;

/**
 * Created by Igor on 11.11.2015.
 */
public class NotationNotFoundException extends Exception {
    public NotationNotFoundException(String notation) {
        super("Notation '" + notation + "' not found.");
    }
}
