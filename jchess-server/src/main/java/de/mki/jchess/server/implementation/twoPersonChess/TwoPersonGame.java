package de.mki.jchess.server.implementation.twoPersonChess;

import de.mki.jchess.server.model.Game;

/**
 * Created by Igor on 11.11.2015.
 */
public class TwoPersonGame extends Game {
    public TwoPersonGame(String id) {
        super(id, 2);
        setGameMode("default-2-person-chess");
    }
}
