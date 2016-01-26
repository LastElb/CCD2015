package jchess.client.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.mki.jchess.commons.HistoryEntry;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties({"gameHistory"})

/**
 * Class to implement a specific game mode.
 * @author Igor Lückel
 * @since 11.11.2015
 */
public class Game {

    String id;
    String gameMode;
    int maximumPlayers;
    List<HistoryEntry> gameHistory;
    Chessboard chessboard;

    public Game() {
        this.gameHistory = new ArrayList<>();
    }

    /**
     * Default constructor for creating a new game.
     * @param id A random string the game can be identified with
     * @param maximumPlayers The maximum count of players.
     */
    public Game(String id, int maximumPlayers) {
        this.id = id;
        this.maximumPlayers = maximumPlayers;
        this.gameHistory = new ArrayList<>();
    }

    /**
     * Get the game ID
     * @return Returns the game ID as {@link String}
     */
    public String getId() {
        return id;
    }

    /**
     * Get the game mode
     * @return Returns the game mode as {@link String}.
     */
    public String getGameMode() {
        return gameMode;
    }

    /**
     * Set the game mode e.g. default-3-person-chess
     * @param gameMode
     * @return current {@link Game} object
     */
    public Game setGameMode(String gameMode) {
        this.gameMode = gameMode;
        return this;
    }

    /**
     * @return Returns an integer with indicating the maximum count of players for this game.
     */
    public int getMaximumPlayers() {
        return maximumPlayers;
    }

    /**
     * Get the history of the current Game as List. Not implemented yet.
     * @return
     */
    public List<HistoryEntry> getGameHistory() {
        return gameHistory;
    }

    /**
     * Get the {@link Chessboard} object
     * @return Chessboard
     */
    public Chessboard getChessboard() {
        return chessboard;
    }

    /**
     * Set the {@link Chessboard} object
     * @param chessboard
     * @return current {@link Game} object
     */
    public Game setChessboard(Chessboard chessboard) {
        this.chessboard = chessboard;
        return this;
    }
}
