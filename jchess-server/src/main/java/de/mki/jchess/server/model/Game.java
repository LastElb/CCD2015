package de.mki.jchess.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import de.mki.jchess.server.controller.GameModeController;
import de.mki.jchess.server.exception.TooManyPlayersException;
import de.mki.jchess.server.json.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class to implement a specific game mode.
 * @author Igor Lückel
 * @since 11.11.2015
 */
public abstract class Game {

    String id;
    String gameMode;
    @JsonIgnore
    List<Client> playerList;
    @JsonIgnore
    List<Client> observerList;
    int maximumPlayers;

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
     * @return Returns the game mode as {@link String}. Possible values can be found in {@link GameModeController#getAvailableGameModes()}.
     */
    public String getGameMode() {
        return gameMode;
    }

    public Game setGameMode(String gameMode) {
        this.gameMode = gameMode;
        return this;
    }

    /**
     * Adds a {@link Client} as player. Initializes the game (call to {@link #initializeGame()}) as soon as there are enough players.
     * @param client The {@link Client} that wants to join as player.
     * @return Returns a modified version of {@link Client} where the {@link Client#connectedGameId} is set to the current {@link Game} id.
     * @throws TooManyPlayersException If you want to add a player and the count of sufficient players is exceeded an exception is thrown.
     */
    public Client addClientAsPlayer(Client client) throws TooManyPlayersException {
        if (!hasSufficientPlayers())
            playerList.add(client);
        else
            throw new TooManyPlayersException();
        client.setConnectedGameId(getId());
        if (hasSufficientPlayers())
            initializeGame();
        return client;
    }

    /**
     * Adds a {@link Client} as observer.
     * @param client The {@link Client} that wants to join as observer.
     * @return Returns a modified version of {@link Client} where the {@link Client#connectedGameId} is set to the current {@link Game} id.
     */
    public Client addClientAsObserver(Client client) {
        observerList.add(client);
        client.setConnectedGameId(getId());
        return client;
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

    /**
     * Initializes the current game. The actual implementation should set up the {@link Chessboard} with all {@link Field}s and
     * {@link Figure}s.
     */
    public abstract void initializeGame();

    public abstract Chessboard getChessboard();
}
