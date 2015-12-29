package de.mki.jchess.server.model;

import java.util.ArrayList;
import java.util.List;

/**
 * An entry for the game history. Contains a {@link List} of {@link ChessboardEvent}s.
 * Created by Igor on 13.11.2015.
 */
public class HistoryEntry {
    List<ChessboardEvent> chessboardEvents;
    Client player;

    /**
     * Default constructor
     */
    public HistoryEntry() {
        chessboardEvents = new ArrayList<>();
    }

    /**
     * Returns the {@link List} of {@link ChessboardEvent}s.
     * @return Returns the {@link List} of {@link ChessboardEvent}s.
     */
    public List<ChessboardEvent> getChessboardEvents() {
        return chessboardEvents;
    }

    /**
     * Get the owner of the {@link HistoryEntry}.
     * @return Get the owner of the {@link HistoryEntry}.
     */
    public Client getPlayer() {
        return player;
    }

    /**
     * Set the owner of the {@link HistoryEntry}.
     * @param player Set the owner of the {@link HistoryEntry}.
     * @return Returns the instance of the {@link HistoryEntry}.
     */
    public HistoryEntry setPlayer(Client player) {
        this.player = player;
        return this;
    }
}
