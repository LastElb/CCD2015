package de.mki.jchess.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.mki.jchess.commons.Client;
import de.mki.jchess.commons.Field;
import de.mki.jchess.commons.Figure;
import de.mki.jchess.commons.HistoryEntry;
import de.mki.jchess.server.controller.GameModeController;
import de.mki.jchess.server.exception.TooManyPlayersException;
import de.mki.jchess.commons.websocket.PlayerChangedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Abstract class to implement a specific game mode.
 * @author Igor Lückel
 * @since 11.11.2015
 */
public abstract class Game {

    private static final Logger logger = LoggerFactory.getLogger(Game.class);

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
     * @return Returns the game mode as {@link String}. Possible values can be found in {@link GameModeController#getAvailableGameModes()}.
     */
    public String getGameMode() {
        return gameMode;
    }

    /**
     * Sets the game mode of the current game
     * @param gameMode The game mode as valid string
     * @return Returns this {@link Game} instance
     */
    public Game setGameMode(String gameMode) {
        this.gameMode = gameMode;
        return this;
    }

    /**
     * Adds a {@link Client} as player. Initializes the game (call to {@link #initializeGame()}) as soon as there are enough players.
     * @param client The {@link Client} that wants to join as player.
     * @param simpMessagingTemplate {@link Optional} of {@link SimpMessagingTemplate} for sending websocket messages
     * @return Returns a modified version of {@link Client} where the {@link Client#connectedGameId} is set to the current {@link Game} id.
     * @throws TooManyPlayersException If you want to add a player and the count of sufficient players is exceeded an exception is thrown.
     */
    public Client addClientAsPlayer(Client client, Optional<SimpMessagingTemplate> simpMessagingTemplate) throws TooManyPlayersException {
        if (!hasSufficientPlayers()) {
            playerList.add(client);
            if (playerList.size() - 2 >= 0)
                playerList.get(playerList.size() - 2).setNextClient(client);
        } else
            throw new TooManyPlayersException();
        client.setConnectedGameId(getId());
        if (hasSufficientPlayers()) {
            client.setNextClient(playerList.get(0));
            initializeGame();
            getChessboard().setCurrentPlayer(playerList.get(0));
            playerList.forEach(client1 -> {
                Map<String, Object> webSocketDataHeader = new LinkedHashMap<>();
                webSocketDataHeader.put("data-type", "PlayerChangedEvent");

                PlayerChangedEvent playerChangedEvent = new PlayerChangedEvent().setItYouTurn(client1.equals(getChessboard().getCurrentPlayer()));
                simpMessagingTemplate.ifPresent(simpMessagingTemplate1 -> {
                    simpMessagingTemplate1.convertAndSend("/game/" + getId() + "/" + client1.getId(), playerChangedEvent, webSocketDataHeader);
                });
            });
            //The last client does connect to the websocket channel after this method is executed. So he will not get the message send above
            // Ugly workaround: Wait a second and resend the message
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    logger.error("", e);
                }
                Map<String, Object> webSocketDataHeader = new LinkedHashMap<>();
                webSocketDataHeader.put("data-type", "PlayerChangedEvent");

                Client lastClient = playerList.get(playerList.size() - 1);
                PlayerChangedEvent playerChangedEvent = new PlayerChangedEvent().setItYouTurn(lastClient.equals(getChessboard().getCurrentPlayer()));
                simpMessagingTemplate.ifPresent(simpMessagingTemplate1 -> {
                    simpMessagingTemplate1.convertAndSend("/game/" + getId() + "/" + lastClient.getId(), playerChangedEvent, webSocketDataHeader);
                });
            }).start();

        }
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
     * Returns a {@link List} of {@link Client}s that are registered as player.
     * @return Returns a {@link List} of {@link Client}s that are registered as player.
     */
    public List<Client> getPlayerList() {
        return playerList;
    }

    /**
     * Returns a {@link List} of {@link Client}s that are registered as observer.
     * @return Returns a {@link List} of {@link Client}s that are registered as observer.
     */
    public List<Client> getObserverList() {
        return observerList;
    }

    /**
     * Returns an integer with indicating the maximum count of players for this game.
     * @return Returns an integer with indicating the maximum count of players for this game.
     */
    public int getMaximumPlayers() {
        return maximumPlayers;
    }

    /**
     * Returns a boolean indication if there are enough players registered to start the game.
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

    /**
     * Returns the {@link Chessboard} specific to the game mode. As long as {@link #initializeGame()} is not called,
     * this should return null.
     * @return Returns the {@link Chessboard} specific to the game mode.
     */
    public abstract Chessboard getChessboard();

    /**
     * Returns the history of the current {@link Game}.
     * @return Returns the history of the current {@link Game}.
     */
    public List<HistoryEntry> getGameHistory() {
        return gameHistory;
    }
}
