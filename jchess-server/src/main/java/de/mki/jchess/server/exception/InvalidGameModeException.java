package de.mki.jchess.server.exception;

/**
 * Created by Igor on 11.11.2015.
 */
public class InvalidGameModeException extends Exception {
    public InvalidGameModeException(String gameMode) {
        super("Invalid game mode '" + gameMode + "'. Check the API for available game modes.");
    }
}
