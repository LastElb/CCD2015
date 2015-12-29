package de.mki.jchess.server.implementation.threePersonChess;

import de.mki.jchess.commons.Field;
import de.mki.jchess.server.Application;
import de.mki.jchess.server.exception.MoveNotAllowedException;
import de.mki.jchess.server.exception.NotationNotFoundException;
import de.mki.jchess.server.exception.TooManyPlayersException;
import de.mki.jchess.commons.Client;
import de.mki.jchess.commons.Figure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Igor on 18.12.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ChessboardTest {

    Chessboard chessboard;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    /**
     * Sets up a {@link ThreePersonGame} and {@link Chessboard} with all initial figures.
     */
    @Before
    public void setUp() {
        ThreePersonGame game = new ThreePersonGame("foobar");
        try {
            game.addClientAsPlayer(new Client().setNickname("Client1"), simpMessagingTemplate);
            game.addClientAsPlayer(new Client().setNickname("Client2"), simpMessagingTemplate);
            game.addClientAsPlayer(new Client().setNickname("Client3"), simpMessagingTemplate);
        } catch (TooManyPlayersException e) {
            //Ignore, we don't want to add more players than possible.
        }
        chessboard = game.getChessboard();
    }

    /**
     * Gets a valid {@link Field}.
     * @throws Exception
     */
    @Test
    public void testGetFieldByNotation() throws Exception {
        chessboard.getFieldByNotation("a1");
    }

    /**
     * Gets an invalid {@link Field}.
     * @throws Exception
     */
    @Test(expected = NotationNotFoundException.class)
    public void testGetInvalidFieldByNotation() throws Exception {
        chessboard.getFieldByNotation("m1");
    }

    /**
     * Testing if the {@link de.mki.jchess.server.implementation.threePersonChess.figures.King} of each player is not checked.
     * @throws Exception
     */
    @Test
    public void testIsKingChecked() throws Exception {
        chessboard.getParentGame().getPlayerList().forEach(client ->
                assertFalse(client.getTeam() + "'s King is checked.", chessboard.isKingChecked(client.getId())));
    }

    /**
     * Testing if the {@link de.mki.jchess.server.implementation.threePersonChess.figures.King} of each player
     * will not be checked with the hypothetical position.
     * @throws Exception
     */
    @Test
    public void testWillKingBeChecked() throws Exception {
        chessboard.getParentGame().getPlayerList().forEach(client ->
                assertFalse(client.getTeam() + "'s King is checked.", chessboard.willKingBeChecked(client.getId())));
    }

    /**
     * Tests if no player is defeated at the beginning of a game.
     * @throws Exception
     */
    @Test
    public void testPlayerStatus() throws Exception {
        chessboard.getParentGame().getPlayerList().forEach(client -> assertFalse(client.isDefeated()));
    }

    /**
     * Tests if an removed {@link Figure} has fields to move.
     * An active {@link Figure} has just a combined {@link List} of
     * {@link Figure#getPossibleMovements(de.mki.jchess.server.model.Chessboard)} and
     * {@link Figure#getPossibleSpecialMovements(de.mki.jchess.server.model.Chessboard)}.
     * @throws Exception
     */
    @Test
    public void testGetPossibleFieldsToMove() throws Exception {
        Figure<Hexagon> pawn = chessboard.getFigures().get(10);
        pawn.setRemoved(true);
        assertTrue(chessboard.getPossibleFieldsToMove(pawn.getId()).size() == 0);
    }

    /**
     * Tests if we can do a valid move and if the history gets a new entry.
     * @throws Exception
     */
    @Test
    public void testPerformMovement() throws Exception {
        Figure<Hexagon> pawn = chessboard.getFigures().get(10);
        chessboard.performMovement(
                pawn.getId(),
                pawn.getClient().getId(),
                chessboard.getPossibleFieldsToMove(pawn.getId()).get(0).getNotation(),
                simpMessagingTemplate);
        assertTrue(chessboard.getParentGame().getGameHistory().size() == 1);
    }

    /**
     * Tests if we can do an invalid move.
     * @throws Exception
     */
    @Test(expected = MoveNotAllowedException.class)
    public void testPerformInvalidMovement() throws Exception {
        Figure<Hexagon> rook = chessboard.getFigures().get(1);
        chessboard.performMovement(
                rook.getId(),
                rook.getClient().getId(),
                "f12",
                simpMessagingTemplate);
    }

    /**
     * Tests if we can do a second move right after we have done the first one.
     * @throws Exception
     */
    @Test(expected = MoveNotAllowedException.class)
    public void testPerformInvalidDoubleMovement() throws Exception {
        Figure<Hexagon> pawn = chessboard.getFigures().get(10);
        chessboard.performMovement(
                pawn.getId(),
                pawn.getClient().getId(),
                chessboard.getPossibleFieldsToMove(pawn.getId()).get(0).getNotation(),
                simpMessagingTemplate);

        Figure<Hexagon> pawn2 = chessboard.getFigures().get(11);
        chessboard.performMovement(
                pawn2.getId(),
                pawn2.getClient().getId(),
                chessboard.getPossibleFieldsToMove(pawn2.getId()).get(0).getNotation(),
                simpMessagingTemplate);
    }

//    @Test
//    public void testAreFieldsOccupied() throws Exception {
//
//    }
//
//    @Test
//    public void testWillFieldsBeOccupied() throws Exception {
//
//    }
//
//    @Test
//    public void testGetFreeFieldsForDiagonalMove() throws Exception {
//
//    }
//
//    @Test
//    public void testIsFigureOwnedByEnemy() throws Exception {
//
//    }
//
//    @Test
//    public void testCheckIfCurrentPlayerIsDefeated() throws Exception {
//
//    }
}