package de.mki.jchess.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.mki.jchess.server.exception.TooManyPlayersException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor on 11.11.2015.
 */
public abstract class Game {
    String id;
    String gameMode;
    @JsonIgnore
    List<Client> playerList;
    @JsonIgnore
    List<Client> observerList;
    int maximumPlayers;

    public Game(String id, int maximumPlayers) {
        this.id = id;
        this.maximumPlayers = maximumPlayers;
        this.playerList = new ArrayList<>();
        this.observerList = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public Game setId(String id) {
        this.id = id;
        return this;
    }

    public String getGameMode() {
        return gameMode;
    }

    public Game setGameMode(String gameMode) {
        this.gameMode = gameMode;
        return this;
    }

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

    public Client addClientAsObserver(Client client) {
        observerList.add(client);
        client.setConnectedGameId(getId());
        return client;
    }

    public List<Client> getPlayerList() {
        return playerList;
    }

    public List<Client> getObserverList() {
        return observerList;
    }

    public int getMaximumPlayers() {
        return maximumPlayers;
    }

    public boolean hasSufficientPlayers() {
        return playerList.size() >= maximumPlayers;
    }

    public abstract void initializeGame();
}
