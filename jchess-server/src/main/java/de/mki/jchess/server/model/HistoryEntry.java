package de.mki.jchess.server.model;

import java.util.List;

/**
 * Needs some thinking process
 * Created by Igor on 13.11.2015.
 */
public class HistoryEntry {
    List<ChessboardEvent> chessboardEvents;
    Client player;

    public List<ChessboardEvent> getChessboardEvents() {
        return chessboardEvents;
    }

    public HistoryEntry setChessboardEvents(List<ChessboardEvent> chessboardEvents) {
        this.chessboardEvents = chessboardEvents;
        return this;
    }

    public Client getPlayer() {
        return player;
    }

    public HistoryEntry setPlayer(Client player) {
        this.player = player;
        return this;
    }
}
