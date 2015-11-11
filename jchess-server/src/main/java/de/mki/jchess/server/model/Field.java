package de.mki.jchess.server.model;

import java.util.List;

/**
 * Created by Igor on 11.11.2015.
 */

/**
 * Created by Igor on 11.11.2015.
 * @param <T> The Direction enum
 */
public abstract class Field<T> {
    /**
     * Notation of the position. Like A7 (on a normal chessboard)
     */
    public abstract String getNotation();
    public abstract void addNeighbour(Field neighbour, T direction);
}
