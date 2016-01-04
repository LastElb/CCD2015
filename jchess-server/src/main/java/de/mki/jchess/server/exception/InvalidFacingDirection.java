package de.mki.jchess.server.exception;

import de.mki.jchess.commons.Figure;

/**
 * Exception for an invalid facing direction. Used by Pawns
 * Created by Igor on 02.12.2015.
 */
public class InvalidFacingDirection extends Exception {

    /**
     * Constructor generating the message
     * <code>
     *     Invalid facing direction {direction} for figure {figure name}
     * </code>
     * @param direction     The invalid facing direction
     * @param figure        The {@link Figure} the invalid facing direction was assigned to.
     */
    public InvalidFacingDirection(Object direction, Figure figure) {
        super("Invalid facing direction " + direction.toString() + " for figure " + figure.getName());
    }
}
