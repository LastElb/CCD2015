package de.mki.jchess.server.implementation.threePersonChess.figures;

import de.mki.jchess.server.Application;
import de.mki.jchess.server.implementation.threePersonChess.Direction;
import de.mki.jchess.server.implementation.threePersonChess.Hexagon;
import de.mki.jchess.server.model.HistoryEntry;
import de.mki.jchess.server.model.websocket.MovementEvent;
import de.mki.jchess.server.service.RandomStringService;
import junitx.framework.ListAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Test cases to check moves from {@link Pawn}s
 * Created by Igor on 17.11.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class PawnTest extends FigureTest {

    /**
     * Tests if a {@link Pawn} can be created with an invalid facing direction
     * @throws Exception
     */
    @Test
    public void testInvalidFacingDirection() throws Exception {
        try {
            new Pawn(game.getPlayerList().get(0), Direction.DIAGONALTOP);
            fail("Pawn created with invalid facing direction");
        } catch (Exception ignore) {
        }
    }

    /**
     * Tests if the {@link Pawn} can do its regular non-diagonal movements.
     * @throws Exception
     */
    @Test
    public void testGetPossibleMovements1() throws Exception {
        Pawn pawn = new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM);
        game.getChessboard().getFigures().add(pawn.setPosition((Hexagon) game.getChessboard().getFieldByNotation("b5")));
        List<Hexagon> possibleMovements = pawn.getPossibleMovements(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("c5"),
                (Hexagon) game.getChessboard().getFieldByNotation("c6"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * Tests if the {@link Pawn} can can not move to a {@link Hexagon} that is occupied by an enemy and not attacking our {@link King}
     * @throws Exception
     */
    @Test
    public void testGetPossibleMovements2() throws Exception {
        Pawn pawn = new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM);
        game.getChessboard().getFigures().add(pawn.setPosition((Hexagon) game.getChessboard().getFieldByNotation("b5")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(1), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("c6")));
        List<Hexagon> possibleMovements = pawn.getPossibleMovements(game.getChessboard());
        List<Hexagon> expectedMovements = Collections.singletonList((Hexagon) game.getChessboard().getFieldByNotation("c5"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * Tests if the {@link Pawn} can move to any {@link Hexagon}. An enemy {@link de.mki.jchess.server.model.Figure} is checking our {@link King}.
     * The {@link Pawn} should not be able to do any movements.
     * @throws Exception
     */
    @Test
    public void testGetPossibleMovements3() throws Exception {
        Pawn pawn = new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM);
        game.getChessboard().getFigures().add(pawn.setPosition((Hexagon) game.getChessboard().getFieldByNotation("b5")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(1), Direction.DIAGONALTOPLEFT).setPosition((Hexagon) game.getChessboard().getFieldByNotation("c6")));
        List<Hexagon> possibleMovements = pawn.getPossibleMovements(game.getChessboard());
        List<Hexagon> expectedMovements = new ArrayList<>();
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * Tests if the {@link Pawn} can do the two-fields-moves from its baseline.
     * @throws Exception
     */
    @Test
    public void testGetPossibleSpecialMovements1() throws Exception {
        Pawn pawn = new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM);
        game.getChessboard().getFigures().add(pawn.setPosition((Hexagon) game.getChessboard().getFieldByNotation("b5")));
        List<Hexagon> possibleMovements = pawn.getPossibleSpecialMovements(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("d5"),
                (Hexagon) game.getChessboard().getFieldByNotation("d7"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * Tests if the {@link Pawn} can NOT do the two-fields-moves if it was already moved.
     * @throws Exception
     */
    @Test
    public void testGetPossibleSpecialMovements2() throws Exception {
        Pawn pawn = new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM);
        game.getChessboard().getFigures().add(pawn.setPosition((Hexagon) game.getChessboard().getFieldByNotation("c5")));
        HistoryEntry historyEntry = new HistoryEntry();
        historyEntry.getChessboardEvents().add(new MovementEvent().setFigureId(pawn.getId()).setFromNotation("b5").setToNotation("c5"));
        game.getGameHistory().add(historyEntry);
        List<Hexagon> possibleMovements = pawn.getPossibleSpecialMovements(game.getChessboard());
        List<Hexagon> expectedMovements = new ArrayList<>();
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * Tests if the {@link Pawn} can do the two-fields-moves from its baseline. One of the target fields is occupied.
     * @throws Exception
     */
    @Test
    public void testGetPossibleSpecialMovements3() throws Exception {
        Pawn pawn = new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM);
        game.getChessboard().getFigures().add(pawn.setPosition((Hexagon) game.getChessboard().getFieldByNotation("b5")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(1), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("d5")));
        List<Hexagon> possibleMovements = pawn.getPossibleSpecialMovements(game.getChessboard());
        List<Hexagon> expectedMovements = Collections.singletonList((Hexagon) game.getChessboard().getFieldByNotation("d7"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * Tests if the {@link Pawn} can NOT do any of the two-fields-moves as both target fields are occupied.
     * @throws Exception
     */
    @Test
    public void testGetPossibleSpecialMovements5() throws Exception {
        Pawn pawn = new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM);
        game.getChessboard().getFigures().add(pawn.setPosition((Hexagon) game.getChessboard().getFieldByNotation("b5")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(1), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("d7")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(1), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("d5")));
        List<Hexagon> possibleMovements = pawn.getPossibleSpecialMovements(game.getChessboard());
        List<Hexagon> expectedMovements = new ArrayList<>();
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * Tests if the {@link Pawn} can beat a figure on a free diagonal {@link Hexagon}
     * @throws Exception
     */
    @Test
    public void testGetPossibleSpecialMovements6() throws Exception {
        Pawn pawn = new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM);
        game.getChessboard().getFigures().add(pawn.setPosition((Hexagon) game.getChessboard().getFieldByNotation("b5")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(1), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("c4")));
        List<Hexagon> possibleMovements = pawn.getPossibleSpecialMovements(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("d5"),
                (Hexagon) game.getChessboard().getFieldByNotation("d7"),
                (Hexagon) game.getChessboard().getFieldByNotation("c4"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * Tests if the {@link Pawn} can beat a figure on a (partially) free diagonal {@link Hexagon}. Also includes two-fields-moves.
     * @throws Exception
     */
    @Test
    public void testGetPossibleSpecialMovements7() throws Exception {
        Pawn pawn = new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM);
        game.getChessboard().getFigures().add(pawn.setPosition((Hexagon) game.getChessboard().getFieldByNotation("b5")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(1), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("c4")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(1), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("b4")));
        List<Hexagon> possibleMovements = pawn.getPossibleSpecialMovements(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("d5"),
                (Hexagon) game.getChessboard().getFieldByNotation("d7"),
                (Hexagon) game.getChessboard().getFieldByNotation("c4"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * Tests if the {@link Pawn} can NOT beat a {@link de.mki.jchess.server.model.Figure} on a blocked diagonal path.
     * One of the two-fields-move paths is blocked as well.
     * @throws Exception
     */
    @Test
    public void testGetPossibleSpecialMovements8() throws Exception {
        Pawn pawn = new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM);
        game.getChessboard().getFigures().add(pawn.setPosition((Hexagon) game.getChessboard().getFieldByNotation("b5")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(1), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("c4")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(1), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("b4")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(1), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("c5")));
        List<Hexagon> possibleMovements = pawn.getPossibleSpecialMovements(game.getChessboard());
        List<Hexagon> expectedMovements = Collections.singletonList((Hexagon) game.getChessboard().getFieldByNotation("d7"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * Tests if the {@link Pawn} can NOT beat an enemy figure because its {@link King} is checked.
     * @throws Exception
     */
    @Test
    public void testGetPossibleSpecialMovements9() throws Exception {
        Pawn pawn = new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM);
        game.getChessboard().getFigures().add(pawn.setPosition((Hexagon) game.getChessboard().getFieldByNotation("b5")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(1), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("c4")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(1), Direction.DIAGONALTOPRIGHT).setPosition((Hexagon) game.getChessboard().getFieldByNotation("b4")));
        List<Hexagon> possibleMovements = pawn.getPossibleSpecialMovements(game.getChessboard());
        List<Hexagon> expectedMovements = new ArrayList<>();
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * Tests if the {@link Pawn} has all possible attackable fields while facing {@link Direction#DIAGONALBOTTOM}
     * @throws Exception
     */
    @Test
    public void testGetAttackableFields1() throws Exception {
        Pawn pawn = new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM);
        game.getChessboard().getFigures().add(pawn.setPosition((Hexagon) game.getChessboard().getFieldByNotation("f7")));

        List<Hexagon> possibleMovements = pawn.getAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("h8"),
                (Hexagon) game.getChessboard().getFieldByNotation("g6"),
                (Hexagon) game.getChessboard().getFieldByNotation("g9"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * Tests if the {@link Pawn} has all possible attackable fields while facing {@link Direction#DIAGONALTOPRIGHT}
     * @throws Exception
     */
    @Test
    public void testGetAttackableFields2() throws Exception {
        Pawn pawn = new Pawn(game.getPlayerList().get(0), Direction.DIAGONALTOPRIGHT);
        game.getChessboard().getFigures().add(pawn.setPosition((Hexagon) game.getChessboard().getFieldByNotation("f7")));

        List<Hexagon> possibleMovements = pawn.getAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("d6"),
                (Hexagon) game.getChessboard().getFieldByNotation("e8"),
                (Hexagon) game.getChessboard().getFieldByNotation("g9"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * Tests if the {@link Pawn} has all possible attackable fields while facing {@link Direction#DIAGONALTOPLEFT}
     * @throws Exception
     */
    @Test
    public void testGetAttackableFields3() throws Exception {
        Pawn pawn = new Pawn(game.getPlayerList().get(0), Direction.DIAGONALTOPLEFT);
        game.getChessboard().getFigures().add(pawn.setPosition((Hexagon) game.getChessboard().getFieldByNotation("f7")));

        List<Hexagon> possibleMovements = pawn.getAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("d6"),
                (Hexagon) game.getChessboard().getFieldByNotation("e5"),
                (Hexagon) game.getChessboard().getFieldByNotation("g6"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * Tests if the {@link Pawn} has all possible attackable fields while facing {@link Direction#DIAGONALBOTTOM}
     * @throws Exception
     */
    @Test
    public void testGetHypotheticalAttackableFields1() throws Exception {
        Pawn pawn = new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM);
        game.getChessboard().getFigures().add(pawn.setPosition((Hexagon) game.getChessboard().getFieldByNotation("f7")));

        List<Hexagon> possibleMovements = pawn.getHypotheticalAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("h8"),
                (Hexagon) game.getChessboard().getFieldByNotation("g6"),
                (Hexagon) game.getChessboard().getFieldByNotation("g9"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * Tests if the {@link Pawn} has all possible attackable fields while facing {@link Direction#DIAGONALTOPRIGHT}
     * @throws Exception
     */
    @Test
    public void testGetHypotheticalAttackableFields2() throws Exception {
        Pawn pawn = new Pawn(game.getPlayerList().get(0), Direction.DIAGONALTOPRIGHT);
        game.getChessboard().getFigures().add(pawn.setPosition((Hexagon) game.getChessboard().getFieldByNotation("f7")));

        List<Hexagon> possibleMovements = pawn.getHypotheticalAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("d6"),
                (Hexagon) game.getChessboard().getFieldByNotation("e8"),
                (Hexagon) game.getChessboard().getFieldByNotation("g9"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * Tests if the {@link Pawn} has all possible attackable fields while facing {@link Direction#DIAGONALTOPLEFT}
     * @throws Exception
     */
    @Test
    public void testGetHypotheticalAttackableFields3() throws Exception {
        Pawn pawn = new Pawn(game.getPlayerList().get(0), Direction.DIAGONALTOPLEFT);
        game.getChessboard().getFigures().add(pawn.setPosition((Hexagon) game.getChessboard().getFieldByNotation("f7")));

        List<Hexagon> possibleMovements = pawn.getHypotheticalAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("d6"),
                (Hexagon) game.getChessboard().getFieldByNotation("e5"),
                (Hexagon) game.getChessboard().getFieldByNotation("g6"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }
}