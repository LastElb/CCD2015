package de.mki.jchess.server.model;

/**
 * Created by Igor on 11.11.2015.
 */
public class Client {
    String id;
    String nickname;
    String connectedGameId;

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

    public Client() {
    }
}
