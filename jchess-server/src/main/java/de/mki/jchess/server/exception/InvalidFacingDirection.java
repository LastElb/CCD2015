package de.mki.jchess.server.exception;

import de.mki.jchess.server.model.Figure;

/**
 * Created by Igor on 02.12.2015.
 */
public class InvalidFacingDirection extends Exception {
    public InvalidFacingDirection(Object direction, Figure figure) {
        super("Invalid facing direction " + direction.toString() + " for figure " + figure.getName());
    }
}
