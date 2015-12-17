package de.mki.jchess.server.controller;

import de.mki.jchess.server.Application;
import de.mki.jchess.server.exception.HostedGameNotFoundException;
import de.mki.jchess.server.exception.InvalidGameModeException;
import de.mki.jchess.server.exception.TooManyPlayersException;
import de.mki.jchess.server.model.Client;
import de.mki.jchess.server.model.Game;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

/**
 * Created by Igor on 17.12.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class HostingControllerTest {

    @Autowired
    HostingController hostingController;

    /**
     * Check if an invalid game mode throws {@link InvalidGameModeException}.
     * @throws Exception
     */
    @Test(expected = InvalidGameModeException.class)
    public void testHostRequestByGameModeInvalid() throws Exception {
        hostingController.hostRequestByGameMode("foobar");
    }

    /**
     * Check if host can create a two person game
     * @throws Exception
     */
    @Test
    public void testHostRequestByGameModeTwoPerson() throws Exception {
        hostingController.hostRequestByGameMode("default-2-person-chess");
    }

    /**
     * Check if host can create a three person game
     * @throws Exception
     */
    @Test
    public void testHostRequestByGameModeThreePerson() throws Exception {
        hostingController.hostRequestByGameMode("default-3-person-chess");
    }

    /**
     * Creates a game and connects a {@link de.mki.jchess.server.model.Client} as player
     * @throws Exception
     */
    @Test
    public void testConnectToHostedGameAsPlayer() throws Exception {
        Game game = hostingController.hostRequestByGameMode("default-3-person-chess");
        Client client = new Client();
        hostingController.connectToHostedGameAsPlayer(game.getId(), client);
    }

    /**
     * Simulates too many players connecting to the same {@link Game}
     * @throws Exception
     */
    @Test(expected = TooManyPlayersException.class)
    public void testConnectToHostedGameAsPlayerWithTooManyPlayers() throws Exception {
        Game game = hostingController.hostRequestByGameMode("default-3-person-chess");
        Client client = new Client();
        hostingController.connectToHostedGameAsPlayer(game.getId(), client);
        hostingController.connectToHostedGameAsPlayer(game.getId(), client);
        hostingController.connectToHostedGameAsPlayer(game.getId(), client);
        hostingController.connectToHostedGameAsPlayer(game.getId(), client);
    }

    /**
     * Simulates a {@link Client} trying to connect to an invalid game id as player.
     * @throws Exception
     */
    @Test(expected = HostedGameNotFoundException.class)
    public void testConnectToInvalidHostedGameAsPlayer() throws Exception {
        Client client = new Client();
        hostingController.connectToHostedGameAsPlayer("invalidId", client);
    }

    /**
     * Creates a {@link Game} and connects a {@link Client} as observer.
     * @throws Exception
     */
    @Test
    public void testConnectToHostedGameAsObserver() throws Exception {
        Game game = hostingController.hostRequestByGameMode("default-3-person-chess");
        Client client = new Client();
        hostingController.connectToHostedGameAsObserver(game.getId(), client);
    }

    /**
     * Simulates a {@link Client} trying to connect to an invalid game id as observer.
     * @throws Exception
     */
    @Test(expected = HostedGameNotFoundException.class)
    public void testConnectToInvalidHostedGameAsObserver() throws Exception {
        Client client = new Client();
        hostingController.connectToHostedGameAsObserver("invalidId", client);
    }
}