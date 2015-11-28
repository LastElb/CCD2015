package jchess.client.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to implement a specific game mode.
 * @author Igor Lückel
 * @since 11.11.2015
 */
public class Game {

    String id;
    String gameMode;
    @JsonIgnore
    List<Client> playerList;
    @JsonIgnore
    List<Client> observerList;
    int maximumPlayers;
    List<HistoryEntry> gameHistory;

    /**
     * Default constructor for creating a new game.
     * @param id A random string the game can be identified with
     * @param maximumPlayers The maximum count of players.
     */
    public Game(String id, int maximumPlayers) {
        this.id = id;
        this.maximumPlayers = maximumPlayers;
        this.playerList = new ArrayList<>();
        this.observerList = new ArrayList<>();
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

    public Game setGameMode(String gameMode) {
        this.gameMode = gameMode;
        return this;
    }

    /**
     * @return Returns a {@link List} of {@link Client}s that are registered as player.
     */
    public List<Client> getPlayerList() {
        return playerList;
    }

    /**
     * @return Returns a {@link List} of {@link Client}s that are registered as observer.
     */
    public List<Client> getObserverList() {
        return observerList;
    }

    /**
     * @return Returns an integer with indicating the maximum count of players for this game.
     */
    public int getMaximumPlayers() {
        return maximumPlayers;
    }

    /**
     * @return Returns a boolean indication if there are enough players registered to start the game.
     */
    public boolean hasSufficientPlayers() {
        return playerList.size() >= maximumPlayers;
    }

    public List<HistoryEntry> getGameHistory() {
        return gameHistory;
    }
}
