package de.mki.jchess.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.mki.jchess.server.service.RandomStringService;

/**
 * Created by Igor on 11.11.2015.
 */
public class Client {
    String id;
    String nickname;
    String connectedGameId;
    String team;
    @JsonIgnore
    Client nextClient;
    @JsonIgnore
    boolean isDefeated;

    public Client() {
        this.id = RandomStringService.getRandomString();
    }

    public String getId() {
        return id;
    }

    public Client setId(String id) {
        this.id = id;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public Client setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getConnectedGameId() {
        return connectedGameId;
    }

    public Client setConnectedGameId(String connectedGameId) {
        this.connectedGameId = connectedGameId;
        return this;
    }

    public String getTeam() {
        return team;
    }

    public Client setTeam(String team) {
        this.team = team;
        return this;
    }

    public Client getNextClient() {
        return nextClient;
    }

    public Client setNextClient(Client nextClient) {
        this.nextClient = nextClient;
        return this;
    }

    public boolean isDefeated() {
        return isDefeated;
    }

    public Client setDefeated(boolean defeated) {
        isDefeated = defeated;
        return this;
    }
}
