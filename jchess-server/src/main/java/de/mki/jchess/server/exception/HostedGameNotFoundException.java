package de.mki.jchess.server.exception;

/**
 * Created by Igor on 11.11.2015.
 */
public class HostedGameNotFoundException extends Exception {
    public HostedGameNotFoundException(String id) {
        super("Game with ID '" + id + "'not found.");
    }
}
