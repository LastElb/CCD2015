package de.mki.jchess.server.implementation.threePersonChess.figures;

import de.mki.jchess.server.Application;
import de.mki.jchess.server.implementation.threePersonChess.Direction;
import de.mki.jchess.server.implementation.threePersonChess.Hexagon;
import de.mki.jchess.server.implementation.threePersonChess.ThreePersonGame;
import de.mki.jchess.server.model.Client;
import de.mki.jchess.server.model.Game;
import de.mki.jchess.server.model.HistoryEntry;
import de.mki.jchess.server.model.websocket.MovementEvent;
import de.mki.jchess.server.service.RandomStringService;
import junitx.framework.ListAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Igor on 17.11.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class PawnTest extends FigureTest {

    @Test
    public void testGetPossibleMovements1() throws Exception {
        setUpGame();
        Pawn pawn = new Pawn(RandomStringService.getRandomString(), game.getPlayerList().get(0), Direction.DIAGONALBOTTOM);
        game.getChessboard().getFigures().add(pawn.setPosition((Hexagon) game.getChessboard().getFieldByNotation("b5")));
        List<Hexagon> possibleMovements = pawn.getPossibleMovements(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("c5"),
                (Hexagon) game.getChessboard().getFieldByNotation("c6"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetPossibleMovements2() throws Exception {
        // One field is occupied by an enemy figure that is not attacking our king
        setUpGame();
        Pawn pawn = new Pawn(RandomStringService.getRandomString(), game.getPlayerList().get(0), Direction.DIAGONALBOTTOM);
        game.getChessboard().getFigures().add(pawn.setPosition((Hexagon) game.getChessboard().getFieldByNotation("b5")));
        game.getChessboard().getFigures().add(new Pawn(RandomStringService.getRandomString(), game.getPlayerList().get(1), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("c6")));
        List<Hexagon> possibleMovements = pawn.getPossibleMovements(game.getChessboard());
        List<Hexagon> expectedMovements = Collections.singletonList((Hexagon) game.getChessboard().getFieldByNotation("c5"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetPossibleMovements3() throws Exception {
        // One field is occupied by an enemy figure that is attacking our king
        setUpGame();
        Pawn pawn = new Pawn(RandomStringService.getRandomString(), game.getPlayerList().get(0), Direction.DIAGONALBOTTOM);
        game.getChessboard().getFigures().add(pawn.setPosition((Hexagon) game.getChessboard().getFieldByNotation("b5")));
        game.getChessboard().getFigures().add(new Pawn(RandomStringService.getRandomString(), game.getPlayerList().get(1), Direction.DIAGONALTOPLEFT).setPosition((Hexagon) game.getChessboard().getFieldByNotation("c6")));
        List<Hexagon> possibleMovements = pawn.getPossibleMovements(game.getChessboard());
        List<Hexagon> expectedMovements = new ArrayList<>();
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetPossibleSpecialMovements1() throws Exception {
        // Starting from baseline. Expect are the two fields moves.
        setUpGame();
        Pawn pawn = new Pawn(RandomStringService.getRandomString(), game.getPlayerList().get(0), Direction.DIAGONALBOTTOM);
        game.getChessboard().getFigures().add(pawn.setPosition((Hexagon) game.getChessboard().getFieldByNotation("b5")));
        List<Hexagon> possibleMovements = pawn.getPossibleSpecialMovements(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList((Hexagon) game.getChessboard().getFieldByNotation("d5"), (Hexagon) game.getChessboard().getFieldByNotation("d7"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetPossibleSpecialMovements2() throws Exception {
        // Regular position. Pawn was already moved. Not attacking any other figure. Expect empty list
        setUpGame();
        Pawn pawn = new Pawn(RandomStringService.getRandomString(), game.getPlayerList().get(0), Direction.DIAGONALBOTTOM);
        game.getChessboard().getFigures().add(pawn.setPosition((Hexagon) game.getChessboard().getFieldByNotation("c5")));

        game.getGameHistory().add(new HistoryEntry().setChessboardEvents(Arrays.asList(new MovementEvent().setFigureId(pawn.getId()).setFromNotation("b5").setToNotation("c5"))));

        List<Hexagon> possibleMovements = pawn.getPossibleSpecialMovements(game.getChessboard());
        List<Hexagon> expectedMovements = new ArrayList<>();
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetPossibleSpecialMovements3() throws Exception {
        // Starting from baseline. Was not moved. Target field for two field move is blocked
        setUpGame();
        Pawn pawn = new Pawn(RandomStringService.getRandomString(), game.getPlayerList().get(0), Direction.DIAGONALBOTTOM);
        game.getChessboard().getFigures().add(pawn.setPosition((Hexagon) game.getChessboard().getFieldByNotation("b5")));
        game.getChessboard().getFigures().add(new Pawn(RandomStringService.getRandomString(), game.getPlayerList().get(1), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("d5")));
        List<Hexagon> possibleMovements = pawn.getPossibleSpecialMovements(game.getChessboard());
        List<Hexagon> expectedMovements = Collections.singletonList((Hexagon) game.getChessboard().getFieldByNotation("d7"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetPossibleSpecialMovements5() throws Exception {
        // Starting from baseline. Was not moved. Target fields for two field move is blocked
        setUpGame();
        Pawn pawn = new Pawn(RandomStringService.getRandomString(), game.getPlayerList().get(0), Direction.DIAGONALBOTTOM);
        game.getChessboard().getFigures().add(pawn.setPosition((Hexagon) game.getChessboard().getFieldByNotation("b5")));
        game.getChessboard().getFigures().add(new Pawn(RandomStringService.getRandomString(), game.getPlayerList().get(1), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("d7")));
        game.getChessboard().getFigures().add(new Pawn(RandomStringService.getRandomString(), game.getPlayerList().get(1), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("d5")));
        List<Hexagon> possibleMovements = pawn.getPossibleSpecialMovements(game.getChessboard());
        List<Hexagon> expectedMovements = new ArrayList<>();
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetPossibleSpecialMovements6() throws Exception {
        // Starting from baseline. Was not moved. Can attack one figure with free diagonal path
        setUpGame();
        Pawn pawn = new Pawn(RandomStringService.getRandomString(), game.getPlayerList().get(0), Direction.DIAGONALBOTTOM);
        game.getChessboard().getFigures().add(pawn.setPosition((Hexagon) game.getChessboard().getFieldByNotation("b5")));
        game.getChessboard().getFigures().add(new Pawn(RandomStringService.getRandomString(), game.getPlayerList().get(1), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("c4")));
        List<Hexagon> possibleMovements = pawn.getPossibleSpecialMovements(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList((Hexagon) game.getChessboard().getFieldByNotation("d5"), (Hexagon) game.getChessboard().getFieldByNotation("d7"), (Hexagon) game.getChessboard().getFieldByNotation("c4"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetPossibleSpecialMovements7() throws Exception {
        // Starting from baseline. Was not moved. Can attack one figure with diagonal path occupied by one.
        setUpGame();
        Pawn pawn = new Pawn(RandomStringService.getRandomString(), game.getPlayerList().get(0), Direction.DIAGONALBOTTOM);
        game.getChessboard().getFigures().add(pawn.setPosition((Hexagon) game.getChessboard().getFieldByNotation("b5")));
        game.getChessboard().getFigures().add(new Pawn(RandomStringService.getRandomString(), game.getPlayerList().get(1), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("c4")));
        game.getChessboard().getFigures().add(new Pawn(RandomStringService.getRandomString(), game.getPlayerList().get(1), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("b4")));
        List<Hexagon> possibleMovements = pawn.getPossibleSpecialMovements(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList((Hexagon) game.getChessboard().getFieldByNotation("d5"), (Hexagon) game.getChessboard().getFieldByNotation("d7"), (Hexagon) game.getChessboard().getFieldByNotation("c4"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetPossibleSpecialMovements8() throws Exception {
        // Starting from baseline. Was not moved. Can attack one figure with diagonal path occupied by two. One two field move is blocked as well.
        setUpGame();
        Pawn pawn = new Pawn(RandomStringService.getRandomString(), game.getPlayerList().get(0), Direction.DIAGONALBOTTOM);
        game.getChessboard().getFigures().add(pawn.setPosition((Hexagon) game.getChessboard().getFieldByNotation("b5")));
        game.getChessboard().getFigures().add(new Pawn(RandomStringService.getRandomString(), game.getPlayerList().get(1), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("c4")));
        game.getChessboard().getFigures().add(new Pawn(RandomStringService.getRandomString(), game.getPlayerList().get(1), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("b4")));
        game.getChessboard().getFigures().add(new Pawn(RandomStringService.getRandomString(), game.getPlayerList().get(1), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("c5")));
        List<Hexagon> possibleMovements = pawn.getPossibleSpecialMovements(game.getChessboard());
        List<Hexagon> expectedMovements = Collections.singletonList((Hexagon) game.getChessboard().getFieldByNotation("d7"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetPossibleSpecialMovements9() throws Exception {
        // Starting from baseline. Was not moved. Can attack one figure with diagonal path occupied by one. But king is checked
        setUpGame();
        Pawn pawn = new Pawn(RandomStringService.getRandomString(), game.getPlayerList().get(0), Direction.DIAGONALBOTTOM);
        game.getChessboard().getFigures().add(pawn.setPosition((Hexagon) game.getChessboard().getFieldByNotation("b5")));
        game.getChessboard().getFigures().add(new Pawn(RandomStringService.getRandomString(), game.getPlayerList().get(1), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("c4")));
        game.getChessboard().getFigures().add(new Pawn(RandomStringService.getRandomString(), game.getPlayerList().get(1), Direction.DIAGONALTOPRIGHT).setPosition((Hexagon) game.getChessboard().getFieldByNotation("b4")));
        List<Hexagon> possibleMovements = pawn.getPossibleSpecialMovements(game.getChessboard());
        List<Hexagon> expectedMovements = new ArrayList<>();
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetPossibleSpecialMovements10() throws Exception {
        // Can attack one figure with diagonal path occupied by none. King is checked by attackable figure
        setUpGame();
        Pawn pawn = new Pawn(RandomStringService.getRandomString(), game.getPlayerList().get(0), Direction.DIAGONALBOTTOM);
        game.getChessboard().getFigures().add(pawn.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a2")));
        game.getChessboard().getFigures().add(new Pawn(RandomStringService.getRandomString(), game.getPlayerList().get(1), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("c4")));
        game.getChessboard().getFigures().add(new Pawn(RandomStringService.getRandomString(), game.getPlayerList().get(1), Direction.DIAGONALTOPRIGHT).setPosition((Hexagon) game.getChessboard().getFieldByNotation("b4")));
        List<Hexagon> possibleMovements = pawn.getPossibleSpecialMovements(game.getChessboard());
        List<Hexagon> expectedMovements = Collections.singletonList((Hexagon) game.getChessboard().getFieldByNotation("b4"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetAttackableFields1() throws Exception {
        setUpGame();
        Pawn pawn = new Pawn(RandomStringService.getRandomString(), game.getPlayerList().get(0), Direction.DIAGONALBOTTOM);
        game.getChessboard().getFigures().add(pawn.setPosition((Hexagon) game.getChessboard().getFieldByNotation("f7")));

        List<Hexagon> possibleMovements = pawn.getAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList((Hexagon) game.getChessboard().getFieldByNotation("h8"), (Hexagon) game.getChessboard().getFieldByNotation("g6"), (Hexagon) game.getChessboard().getFieldByNotation("g9"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetAttackableFields2() throws Exception {
        setUpGame();
        Pawn pawn = new Pawn(RandomStringService.getRandomString(), game.getPlayerList().get(0), Direction.DIAGONALTOPRIGHT);
        game.getChessboard().getFigures().add(pawn.setPosition((Hexagon) game.getChessboard().getFieldByNotation("f7")));

        List<Hexagon> possibleMovements = pawn.getAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList((Hexagon) game.getChessboard().getFieldByNotation("d6"), (Hexagon) game.getChessboard().getFieldByNotation("e8"), (Hexagon) game.getChessboard().getFieldByNotation("g9"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetAttackableFields3() throws Exception {
        setUpGame();
        Pawn pawn = new Pawn(RandomStringService.getRandomString(), game.getPlayerList().get(0), Direction.DIAGONALTOPLEFT);
        game.getChessboard().getFigures().add(pawn.setPosition((Hexagon) game.getChessboard().getFieldByNotation("f7")));

        List<Hexagon> possibleMovements = pawn.getAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList((Hexagon) game.getChessboard().getFieldByNotation("d6"), (Hexagon) game.getChessboard().getFieldByNotation("e5"), (Hexagon) game.getChessboard().getFieldByNotation("g6"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetHypotheticalAttackableFields1() throws Exception {
        setUpGame();
        Pawn pawn = new Pawn(RandomStringService.getRandomString(), game.getPlayerList().get(0), Direction.DIAGONALBOTTOM);
        game.getChessboard().getFigures().add(pawn.setPosition((Hexagon) game.getChessboard().getFieldByNotation("f7")));

        List<Hexagon> possibleMovements = pawn.getHypotheticalAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList((Hexagon) game.getChessboard().getFieldByNotation("h8"), (Hexagon) game.getChessboard().getFieldByNotation("g6"), (Hexagon) game.getChessboard().getFieldByNotation("g9"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetHypotheticalAttackableFields2() throws Exception {
        setUpGame();
        Pawn pawn = new Pawn(RandomStringService.getRandomString(), game.getPlayerList().get(0), Direction.DIAGONALTOPRIGHT);
        game.getChessboard().getFigures().add(pawn.setPosition((Hexagon) game.getChessboard().getFieldByNotation("f7")));

        List<Hexagon> possibleMovements = pawn.getHypotheticalAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList((Hexagon) game.getChessboard().getFieldByNotation("d6"), (Hexagon) game.getChessboard().getFieldByNotation("e8"), (Hexagon) game.getChessboard().getFieldByNotation("g9"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetHypotheticalAttackableFields3() throws Exception {
        setUpGame();
        Pawn pawn = new Pawn(RandomStringService.getRandomString(), game.getPlayerList().get(0), Direction.DIAGONALTOPLEFT);
        game.getChessboard().getFigures().add(pawn.setPosition((Hexagon) game.getChessboard().getFieldByNotation("f7")));

        List<Hexagon> possibleMovements = pawn.getHypotheticalAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList((Hexagon) game.getChessboard().getFieldByNotation("d6"), (Hexagon) game.getChessboard().getFieldByNotation("e5"), (Hexagon) game.getChessboard().getFieldByNotation("g6"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }
}