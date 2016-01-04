package de.mki.jchess.server.exception;

/**
 * Exception when someone enters an invalid game mode.
 * Created by Igor on 11.11.2015.
 */
public class InvalidGameModeException extends Exception {

    /**
     * Constructor generating the message
     * <code>
     *     Invalid game mode {game mode}. Check the API for available game modes.
     * </code>
     * @param gameMode    The invalid game mode as {@link String}
     */
    public InvalidGameModeException(String gameMode) {
        super("Invalid game mode '" + gameMode + "'. Check the API for available game modes.");
    }
}
