package de.mki.jchess.server.model;

/**
 * Created by Igor on 11.11.2015.
 */

/**
 * Created by Igor on 11.11.2015.
 * @param <T> The Direction enum
 */
public abstract class Field<T> {

    /**
     * Notation of the position. Like a7 on a normal chessboard
     * @return Notation of the position. Like a7 on a normal chessboard
     */
    public abstract String getNotation();

    /**
     * Use this method to connect {@link Field} to each other.
     * @param neighbour The neighbour relative to the current {@link Field}.
     * @param direction The direction where this neighbour {@link Field} is located.
     */
    public abstract void addNeighbour(Field neighbour, T direction);
}
