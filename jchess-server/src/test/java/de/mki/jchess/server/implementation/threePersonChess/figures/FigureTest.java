package de.mki.jchess.server.implementation.threePersonChess.figures;

import de.mki.jchess.server.implementation.threePersonChess.Direction;
import de.mki.jchess.server.implementation.threePersonChess.Hexagon;
import de.mki.jchess.server.implementation.threePersonChess.ThreePersonGame;
import de.mki.jchess.commons.Client;
import de.mki.jchess.server.model.Game;
import de.mki.jchess.commons.RandomStringService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.junit.Assert.assertEquals;

/**
 * Created by Igor on 30.11.2015.
 */
public abstract class FigureTest {
    Game game;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    /**
     * Sets up a chessboard with three players and three kings at their initial position.
     * All other figures are removed.
     * @throws Exception
     */
    @Before
    public void setUpGame() throws Exception {
        game = new ThreePersonGame(RandomStringService.getRandomString());
        game.addClientAsPlayer(new Client().setNickname("Client1"), simpMessagingTemplate);
        game.addClientAsPlayer(new Client().setNickname("Client2"), simpMessagingTemplate);
        game.addClientAsPlayer(new Client().setNickname("Client3"), simpMessagingTemplate);
        game.initializeGame();
        // Reset figures
        game.getChessboard().getFigures().clear();
        game.getChessboard().getFigures().add(new King(RandomStringService.getRandomString(), game.getPlayerList().get(0)).setPosition((Hexagon) game.getChessboard().getFieldByNotation("a5")));
        game.getChessboard().getFigures().add(new King(RandomStringService.getRandomString(), game.getPlayerList().get(1)).setPosition((Hexagon) game.getChessboard().getFieldByNotation("j13")));
        game.getChessboard().getFigures().add(new King(RandomStringService.getRandomString(), game.getPlayerList().get(2)).setPosition((Hexagon) game.getChessboard().getFieldByNotation("i4")));
    }

    public Game getGame() {
        return game;
    }

    public SimpMessagingTemplate getSimpMessagingTemplate() {
        return simpMessagingTemplate;
    }
}
