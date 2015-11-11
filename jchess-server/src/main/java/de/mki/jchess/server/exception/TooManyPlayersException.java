package de.mki.jchess.server.exception;

/**
 * Created by Igor on 11.11.2015.
 */
public class TooManyPlayersException extends Exception {
    public TooManyPlayersException() {
        super("There are already enough players registered for the game. Maybe join as observer?");
    }
}
