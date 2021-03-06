package de.mki.jchess.server.implementation.threePersonChess;

import de.mki.jchess.commons.websocket.PlayerChangedEvent;
import de.mki.jchess.server.Application;
import de.mki.jchess.server.exception.TooManyPlayersException;
import de.mki.jchess.commons.Client;
import de.mki.jchess.server.model.Game;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by Igor on 18.12.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ThreePersonGameTest {

    ThreePersonGame game;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    /**
     * Creates a new game and assigns it to a local variable.
     */
    @Before
    public void setUp() {
        game = new ThreePersonGame("foobar");
    }

    /**
     * Testing if initializing the {@link Game} with a {@link Chessboard} is NOT working when there are not enough players.
     * @throws Exception
     */
    @Test()
    public void testInitializeGame() throws Exception {
        assertNull(game.getChessboard());
        game.initializeGame();
        assertNull(game.getChessboard());
    }

    /**
     * Adds if adding a player is working and if a exception is thrown when there already enough players.
     * @throws Exception
     */
    @Test(expected = TooManyPlayersException.class)
    public void testAddClientAsPlayer() throws Exception {
        Client client = new Client();
        game.addClientAsPlayer(client, Optional.ofNullable(simpMessagingTemplate));
        game.addClientAsPlayer(client, Optional.ofNullable(simpMessagingTemplate));
        game.addClientAsPlayer(client, Optional.ofNullable(simpMessagingTemplate));
        game.addClientAsPlayer(client, Optional.ofNullable(simpMessagingTemplate));
    }

    /**
     * Tests if the maximum player count is correct
     * @throws Exception
     */
    @Test
    public void testMaximumPlayers() throws Exception {
        assertTrue(game.getMaximumPlayers() == 3);
    }

    /**
     * Tests if the observer list is not null.
     * @throws Exception
     */
    @Test
    public void testGetObserverList() throws Exception {
        assertNotNull(game.getObserverList());
    }

    /**
     * Tests of the game mode is stored correctly.
     * @throws Exception
     */
    @Test
    public void testGetGameMode() throws Exception {
        assertEquals(game.getGameMode(), "default-3-person-chess");
    }
}