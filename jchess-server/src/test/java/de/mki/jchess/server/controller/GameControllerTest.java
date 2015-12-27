package de.mki.jchess.server.controller;

import de.mki.jchess.server.Application;
import de.mki.jchess.server.exception.HostedGameNotFoundException;
import de.mki.jchess.server.exception.MoveNotAllowedException;
import de.mki.jchess.server.model.Client;
import de.mki.jchess.server.model.Field;
import de.mki.jchess.server.model.Figure;
import de.mki.jchess.server.model.Game;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Test for game logic and calling methods that are used in a game
 * Created by Igor on 17.12.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class GameControllerTest {

    @Autowired
    GameController gameController;

    @Autowired
    HostingController hostingController;

    /**
     * Creates a game and checks if we get all information about the game.
     * @throws Exception
     */
    @Test
    public void testGetFullGame() throws Exception {
        Game game = hostingController.hostRequestByGameMode("default-3-person-chess");
        gameController.getFullGame(game.getId());
    }

    /**
     * Checks if we get all information about an invalid game.
     * @throws Exception
     */
    @Test(expected = HostedGameNotFoundException.class)
    public void testGetFullInvalidGame() throws Exception {
        gameController.getFullGame("invalidId");
    }

    /**
     * Creates a game, joins three {@link de.mki.jchess.server.model.Client Clients} as players and checks for possible moves for one figure.
     * This test does NOT verify if all figure moves are correct. This belongs to the figure and chessboard tests!
     * @throws Exception
     */
    @Test
    public void testGetPossibleMoves() throws Exception {
        Game game = hostingController.hostRequestByGameMode("default-3-person-chess");
        Client client1 = new Client();
        Client client2 = new Client();
        Client client3 = new Client();
        client1 = hostingController.connectToHostedGameAsPlayer(game.getId(), client1);
        client2 = hostingController.connectToHostedGameAsPlayer(game.getId(), client2);
        client3 = hostingController.connectToHostedGameAsPlayer(game.getId(), client3);
        game = gameController.getFullGame(game.getId());
        gameController.getPossibleMoves(game.getId(), ((Figure) game.getChessboard().getFigures().get(0)).getId());
    }

    /**
     * Creates a game, joins three {@link de.mki.jchess.server.model.Client Clients} as players and checks for possible moves for one figure.
     * But does enter a wrong game id
     * This test does NOT verify if all figure moves are correct. This belongs to the figure and chessboard tests!
     * @throws Exception
     */
    @Test(expected = HostedGameNotFoundException.class)
    public void testGetPossibleMovesInvalidGame() throws Exception {
        Game game = hostingController.hostRequestByGameMode("default-3-person-chess");
        Client client1 = new Client();
        Client client2 = new Client();
        Client client3 = new Client();
        client1 = hostingController.connectToHostedGameAsPlayer(game.getId(), client1);
        client2 = hostingController.connectToHostedGameAsPlayer(game.getId(), client2);
        client3 = hostingController.connectToHostedGameAsPlayer(game.getId(), client3);
        gameController.getPossibleMoves("invalidId", ((Figure) game.getChessboard().getFigures().get(0)).getId());
    }

    /**
     * Creates a game, joins three {@link de.mki.jchess.server.model.Client Clients} as players and checks for possible moves for a figure on the specified field.
     * This test does NOT verify if all figure moves are correct. This belongs to the figure and chessboard tests!
     * @throws Exception
     */
    @Test
    public void testGetPossibleMovesByField() throws Exception {
        Game game = hostingController.hostRequestByGameMode("default-3-person-chess");
        Client client1 = new Client();
        Client client2 = new Client();
        Client client3 = new Client();
        client1 = hostingController.connectToHostedGameAsPlayer(game.getId(), client1);
        client2 = hostingController.connectToHostedGameAsPlayer(game.getId(), client2);
        client3 = hostingController.connectToHostedGameAsPlayer(game.getId(), client3);
        game = gameController.getFullGame(game.getId());
        gameController.getPossibleMovesByField(game.getId(), "b1");
    }

    /**
     * Creates a game, joins three {@link de.mki.jchess.server.model.Client Clients} as players and checks for possible moves for a figure on a invalid field.
     * This test does NOT verify if all figure moves are correct. This belongs to the figure and chessboard tests!
     * @throws Exception
     */
    @Test
    public void testGetPossibleMovesByInvalidField() throws Exception {
        Game game = hostingController.hostRequestByGameMode("default-3-person-chess");
        Client client1 = new Client();
        Client client2 = new Client();
        Client client3 = new Client();
        client1 = hostingController.connectToHostedGameAsPlayer(game.getId(), client1);
        client2 = hostingController.connectToHostedGameAsPlayer(game.getId(), client2);
        client3 = hostingController.connectToHostedGameAsPlayer(game.getId(), client3);
        game = gameController.getFullGame(game.getId());
        assertTrue(gameController.getPossibleMovesByField(game.getId(), "m1").size() == 0);
    }

    /**
     * Creates a game, joins three {@link de.mki.jchess.server.model.Client Clients} as players and checks for possible moves for a figure on a invalid field.
     * This test does NOT verify if all figure moves are correct. This belongs to the figure and chessboard tests!
     * @throws Exception
     */
    @Test(expected = HostedGameNotFoundException.class)
    public void testGetPossibleMovesByFieldInvalidGame() throws Exception {
        Game game = hostingController.hostRequestByGameMode("default-3-person-chess");
        Client client1 = new Client();
        Client client2 = new Client();
        Client client3 = new Client();
        client1 = hostingController.connectToHostedGameAsPlayer(game.getId(), client1);
        client2 = hostingController.connectToHostedGameAsPlayer(game.getId(), client2);
        client3 = hostingController.connectToHostedGameAsPlayer(game.getId(), client3);
        game = gameController.getFullGame(game.getId());
        gameController.getPossibleMovesByField("invalidId", "m1");
    }

    /**
     * Creates a game, joins three {@link de.mki.jchess.server.model.Client Clients} as players and performs a move for a figure.
     * This test does NOT verify if all figure moves are correct. This belongs to the figure and chessboard tests!
     * @throws Exception
     */
    @Test
    public void testPerformMove() throws Exception {
        Game game = hostingController.hostRequestByGameMode("default-3-person-chess");
        Client client1 = new Client();
        Client client2 = new Client();
        Client client3 = new Client();
        client1 = hostingController.connectToHostedGameAsPlayer(game.getId(), client1);
        client2 = hostingController.connectToHostedGameAsPlayer(game.getId(), client2);
        client3 = hostingController.connectToHostedGameAsPlayer(game.getId(), client3);
        game = gameController.getFullGame(game.getId());
        List<Field> possibleFields = gameController.getPossibleMoves(game.getId(), ((Figure) game.getChessboard().getFigures().get(10)).getId());
        gameController.performMove(game.getId(), ((Figure) game.getChessboard().getFigures().get(10)).getId(), client1.getId(), possibleFields.get(0).getNotation());
    }

    /**
     * Creates a game, joins three {@link de.mki.jchess.server.model.Client Clients} as players and performs an illegal move for a figure.
     * This test does NOT verify if all figure moves are correct. This belongs to the figure and chessboard tests!
     * @throws Exception
     */
    @Test(expected = MoveNotAllowedException.class)
    public void testPerformIllegalMove() throws Exception {
        Game game = hostingController.hostRequestByGameMode("default-3-person-chess");
        Client client1 = new Client();
        Client client2 = new Client();
        Client client3 = new Client();
        client1 = hostingController.connectToHostedGameAsPlayer(game.getId(), client1);
        client2 = hostingController.connectToHostedGameAsPlayer(game.getId(), client2);
        client3 = hostingController.connectToHostedGameAsPlayer(game.getId(), client3);
        game = gameController.getFullGame(game.getId());
        gameController.performMove(game.getId(), ((Figure) game.getChessboard().getFigures().get(0)).getId(), client1.getId(), "a2");
    }

    /**
     * Creates a game, joins three {@link de.mki.jchess.server.model.Client Clients} as players and performs a move for a figure on a field.
     * This test does NOT verify if all figure moves are correct. This belongs to the figure and chessboard tests!
     * @throws Exception
     */
    @Test(expected = MoveNotAllowedException.class)
    public void testPerformIllegalMoveByField() throws Exception {
        Game game = hostingController.hostRequestByGameMode("default-3-person-chess");
        Client client1 = new Client();
        Client client2 = new Client();
        Client client3 = new Client();
        client1 = hostingController.connectToHostedGameAsPlayer(game.getId(), client1);
        client2 = hostingController.connectToHostedGameAsPlayer(game.getId(), client2);
        client3 = hostingController.connectToHostedGameAsPlayer(game.getId(), client3);
        game = gameController.getFullGame(game.getId());
        gameController.performMoveByField(game.getId(), ((Figure) game.getChessboard().getFigures().get(0)).getPosition().getNotation(), client1.getId(), "a2");
    }

    /**
     * Creates a game, joins three {@link de.mki.jchess.server.model.Client Clients} as players and performs a move for a figure on a field.
     * This test does NOT verify if all figure moves are correct. This belongs to the figure and chessboard tests!
     * @throws Exception
     */
    @Test()
    public void testPerformMoveByField() throws Exception {
        Game game = hostingController.hostRequestByGameMode("default-3-person-chess");
        Client client1 = new Client();
        Client client2 = new Client();
        Client client3 = new Client();
        client1 = hostingController.connectToHostedGameAsPlayer(game.getId(), client1);
        client2 = hostingController.connectToHostedGameAsPlayer(game.getId(), client2);
        client3 = hostingController.connectToHostedGameAsPlayer(game.getId(), client3);
        game = gameController.getFullGame(game.getId());
        List<Field> possibleFields = gameController.getPossibleMoves(game.getId(), ((Figure) game.getChessboard().getFigures().get(10)).getId());
        gameController.performMoveByField(game.getId(), ((Figure) game.getChessboard().getFigures().get(10)).getPosition().getNotation(), client1.getId(), possibleFields.get(0).getNotation());
    }
}