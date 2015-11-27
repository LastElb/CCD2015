package de.mki.jchess.server.model.websocket;

import java.time.LocalDateTime;

/**
 * Created by Igor on 26.11.2015.
 */
public class TimeEvent {
    LocalDateTime localDateTime;
    String playerNickname;

    public String getPlayerNickname() {
        return playerNickname;
    }

    public TimeEvent setPlayerNickname(String playerNickname) {
        this.playerNickname = playerNickname;
        return this;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public TimeEvent setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
        return this;
    }
}
