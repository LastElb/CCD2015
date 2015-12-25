package de.mki.jchess.server.implementation.threePersonChess.figures;

import de.mki.jchess.server.Application;
import de.mki.jchess.server.implementation.threePersonChess.Direction;
import de.mki.jchess.server.implementation.threePersonChess.Hexagon;
import de.mki.jchess.server.model.HistoryEntry;
import de.mki.jchess.server.model.websocket.MovementEvent;
import junitx.framework.ListAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This tests check correct movement and behaviour of {@link Rook}s.
 * Created by Igor on 30.11.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class RookTest extends FigureTest {

    /**
     * The {@link Rook} is on one of its starting positions.
     * The {@link King} is blocking the path on its origin position.
     * @throws Exception
     */
    @Test
    public void testGetPossibleMovements1() throws Exception {
        Rook rook = new Rook(game.getPlayerList().get(0));
        game.getChessboard().getFigures().add(rook.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a1")));
        List<Hexagon> possibleMovements = rook.getPossibleMovements(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("b1"),
                (Hexagon) game.getChessboard().getFieldByNotation("c1"),
                (Hexagon) game.getChessboard().getFieldByNotation("d1"),
                (Hexagon) game.getChessboard().getFieldByNotation("e1"),
                (Hexagon) game.getChessboard().getFieldByNotation("f1"),
                (Hexagon) game.getChessboard().getFieldByNotation("b2"),
                (Hexagon) game.getChessboard().getFieldByNotation("c3"),
                (Hexagon) game.getChessboard().getFieldByNotation("d4"),
                (Hexagon) game.getChessboard().getFieldByNotation("e5"),
                (Hexagon) game.getChessboard().getFieldByNotation("f6"),
                (Hexagon) game.getChessboard().getFieldByNotation("g7"),
                (Hexagon) game.getChessboard().getFieldByNotation("h8"),
                (Hexagon) game.getChessboard().getFieldByNotation("i9"),
                (Hexagon) game.getChessboard().getFieldByNotation("j10"),
                (Hexagon) game.getChessboard().getFieldByNotation("k11"),
                (Hexagon) game.getChessboard().getFieldByNotation("l12"),
                (Hexagon) game.getChessboard().getFieldByNotation("m13"),
                (Hexagon) game.getChessboard().getFieldByNotation("a2"),
                (Hexagon) game.getChessboard().getFieldByNotation("a3"),
                (Hexagon) game.getChessboard().getFieldByNotation("a4"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link Rook} is on one of its starting positions.
     * A {@link Pawn} is blocking the path directly next to the {@link Rook}.
     * @throws Exception
     */
    @Test
    public void testGetPossibleMovements2() throws Exception {
        Rook rook = new Rook(game.getPlayerList().get(0));
        game.getChessboard().getFigures().add(rook.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a1")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("a2")));
        List<Hexagon> possibleMovements = rook.getPossibleMovements(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("b1"),
                (Hexagon) game.getChessboard().getFieldByNotation("c1"),
                (Hexagon) game.getChessboard().getFieldByNotation("d1"),
                (Hexagon) game.getChessboard().getFieldByNotation("e1"),
                (Hexagon) game.getChessboard().getFieldByNotation("f1"),
                (Hexagon) game.getChessboard().getFieldByNotation("b2"),
                (Hexagon) game.getChessboard().getFieldByNotation("c3"),
                (Hexagon) game.getChessboard().getFieldByNotation("d4"),
                (Hexagon) game.getChessboard().getFieldByNotation("e5"),
                (Hexagon) game.getChessboard().getFieldByNotation("f6"),
                (Hexagon) game.getChessboard().getFieldByNotation("g7"),
                (Hexagon) game.getChessboard().getFieldByNotation("h8"),
                (Hexagon) game.getChessboard().getFieldByNotation("i9"),
                (Hexagon) game.getChessboard().getFieldByNotation("j10"),
                (Hexagon) game.getChessboard().getFieldByNotation("k11"),
                (Hexagon) game.getChessboard().getFieldByNotation("l12"),
                (Hexagon) game.getChessboard().getFieldByNotation("m13"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link Rook} is on one of its starting positions.
     * Two {@link Pawn}s are blocking two paths.
     * @throws Exception
     */
    @Test
    public void testGetPossibleMovements3() throws Exception {
        Rook rook = new Rook(game.getPlayerList().get(0));
        game.getChessboard().getFigures().add(rook.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a1")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("a2")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("b2")));
        List<Hexagon> possibleMovements = rook.getPossibleMovements(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("b1"),
                (Hexagon) game.getChessboard().getFieldByNotation("c1"),
                (Hexagon) game.getChessboard().getFieldByNotation("d1"),
                (Hexagon) game.getChessboard().getFieldByNotation("e1"),
                (Hexagon) game.getChessboard().getFieldByNotation("f1"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link Rook} is on one of its starting positions.
     * All paths are blocked by three {@link Pawn}s.
     * @throws Exception
     */
    @Test
    public void testGetPossibleMovements4() throws Exception {
        Rook rook = new Rook(game.getPlayerList().get(0));
        game.getChessboard().getFigures().add(rook.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a1")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("a2")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("b2")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("b1")));
        List<Hexagon> possibleMovements = rook.getPossibleMovements(game.getChessboard());
        List<Hexagon> expectedMovements = new ArrayList<>();
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link Rook} is on one of its starting positions.
     * An enemy {@link Pawn} is checking the {@link Rook}'s {@link King}.
     * @throws Exception
     */
    @Test
    public void testGetPossibleMovements5() throws Exception {
        Rook rook = new Rook(game.getPlayerList().get(0));
        game.getChessboard().getFigures().add(rook.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a1")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(1), Direction.DIAGONALTOPRIGHT).setPosition((Hexagon) game.getChessboard().getFieldByNotation("b4")));
        List<Hexagon> possibleMovements = rook.getPossibleMovements(game.getChessboard());
        List<Hexagon> expectedMovements = new ArrayList<>();
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link Rook} is on one of its starting positions.
     * An enemy figure is next to the {@link Rook} and beatable.
     * @throws Exception
     */
    @Test
    public void testGetPossibleMovements6() throws Exception {
        Rook rook = new Rook(game.getPlayerList().get(0));
        game.getChessboard().getFigures().add(rook.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a1")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(1), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("a2")));
        List<Hexagon> possibleMovements = rook.getPossibleMovements(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("b1"),
                (Hexagon) game.getChessboard().getFieldByNotation("c1"),
                (Hexagon) game.getChessboard().getFieldByNotation("d1"),
                (Hexagon) game.getChessboard().getFieldByNotation("e1"),
                (Hexagon) game.getChessboard().getFieldByNotation("f1"),
                (Hexagon) game.getChessboard().getFieldByNotation("b2"),
                (Hexagon) game.getChessboard().getFieldByNotation("c3"),
                (Hexagon) game.getChessboard().getFieldByNotation("d4"),
                (Hexagon) game.getChessboard().getFieldByNotation("e5"),
                (Hexagon) game.getChessboard().getFieldByNotation("f6"),
                (Hexagon) game.getChessboard().getFieldByNotation("g7"),
                (Hexagon) game.getChessboard().getFieldByNotation("h8"),
                (Hexagon) game.getChessboard().getFieldByNotation("i9"),
                (Hexagon) game.getChessboard().getFieldByNotation("j10"),
                (Hexagon) game.getChessboard().getFieldByNotation("k11"),
                (Hexagon) game.getChessboard().getFieldByNotation("l12"),
                (Hexagon) game.getChessboard().getFieldByNotation("m13"),
                (Hexagon) game.getChessboard().getFieldByNotation("a2"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link Rook} is on one of its starting positions.
     * Check for castling.
     * @throws Exception
     */
    @Test
    public void testGetPossibleSpecialMovements1() throws Exception {
        Rook rook = new Rook(game.getPlayerList().get(0));
        game.getChessboard().getFigures().add(rook.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a1")));
        List<Hexagon> possibleMovements = rook.getPossibleSpecialMovements(game.getChessboard());
        List<Hexagon> expectedMovements = Collections.singletonList(
                (Hexagon) game.getChessboard().getFieldByNotation("a5"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link Rook} is on one of its starting positions.
     * Check for castling.
     * @throws Exception
     */
    @Test
    public void testGetPossibleSpecialMovements2() throws Exception {
        Rook rook = new Rook(game.getPlayerList().get(0));
        game.getChessboard().getFigures().add(rook.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a8")));
        List<Hexagon> possibleMovements = rook.getPossibleSpecialMovements(game.getChessboard());
        List<Hexagon> expectedMovements = Collections.singletonList(
                (Hexagon) game.getChessboard().getFieldByNotation("a5"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link Rook} is on one of its starting positions.
     * Check for castling when a {@link Rook} was already moved.
     * @throws Exception
     */
    @Test
    public void testGetPossibleSpecialMovements3() throws Exception {
        Rook rook = new Rook(game.getPlayerList().get(0));
        //FIXME
//        game.getGameHistory().add(new HistoryEntry().setChessboardEvents(Collections.singletonList(new MovementEvent().setFigureId(rook.getId()).setFromNotation("a1").setToNotation("a2"))));
        game.getChessboard().getFigures().add(rook.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a2")));
        List<Hexagon> possibleMovements = rook.getPossibleSpecialMovements(game.getChessboard());
        List<Hexagon> expectedMovements = new ArrayList<>();
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link Rook} is on one of its starting positions.
     * Check for castling when a {@link Rook} was already moved.
     * @throws Exception
     */
    @Test
    public void testGetPossibleSpecialMovements4() throws Exception {
        Rook rook = new Rook(game.getPlayerList().get(0));
        //FIXME
//        game.getGameHistory().add(new HistoryEntry().setChessboardEvents(Collections.singletonList(new MovementEvent().setFigureId(rook.getId()).setFromNotation("a8").setToNotation("a7"))));
        game.getChessboard().getFigures().add(rook.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a7")));
        List<Hexagon> possibleMovements = rook.getPossibleSpecialMovements(game.getChessboard());
        List<Hexagon> expectedMovements = new ArrayList<>();
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link Rook} is on one of its starting positions.
     * Similar to {@link #testGetPossibleMovements1()}
     * @throws Exception
     */
    @Test
    public void testGetAttackableFields1() throws Exception {
        Rook rook = new Rook(game.getPlayerList().get(0));
        game.getChessboard().getFigures().add(rook.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a1")));
        List<Hexagon> possibleMovements = rook.getAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("b1"),
                (Hexagon) game.getChessboard().getFieldByNotation("c1"),
                (Hexagon) game.getChessboard().getFieldByNotation("d1"),
                (Hexagon) game.getChessboard().getFieldByNotation("e1"),
                (Hexagon) game.getChessboard().getFieldByNotation("f1"),
                (Hexagon) game.getChessboard().getFieldByNotation("b2"),
                (Hexagon) game.getChessboard().getFieldByNotation("c3"),
                (Hexagon) game.getChessboard().getFieldByNotation("d4"),
                (Hexagon) game.getChessboard().getFieldByNotation("e5"),
                (Hexagon) game.getChessboard().getFieldByNotation("f6"),
                (Hexagon) game.getChessboard().getFieldByNotation("g7"),
                (Hexagon) game.getChessboard().getFieldByNotation("h8"),
                (Hexagon) game.getChessboard().getFieldByNotation("i9"),
                (Hexagon) game.getChessboard().getFieldByNotation("j10"),
                (Hexagon) game.getChessboard().getFieldByNotation("k11"),
                (Hexagon) game.getChessboard().getFieldByNotation("l12"),
                (Hexagon) game.getChessboard().getFieldByNotation("m13"),
                (Hexagon) game.getChessboard().getFieldByNotation("a2"),
                (Hexagon) game.getChessboard().getFieldByNotation("a3"),
                (Hexagon) game.getChessboard().getFieldByNotation("a4"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link Rook} is on one of its starting positions.
     * @throws Exception
     */
    @Test
    public void testGetAttackableFields2() throws Exception {
        Rook rook = new Rook(game.getPlayerList().get(0));
        game.getChessboard().getFigures().add(rook.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a1")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("a2")));
        List<Hexagon> possibleMovements = rook.getAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("b1"),
                (Hexagon) game.getChessboard().getFieldByNotation("c1"),
                (Hexagon) game.getChessboard().getFieldByNotation("d1"),
                (Hexagon) game.getChessboard().getFieldByNotation("e1"),
                (Hexagon) game.getChessboard().getFieldByNotation("f1"),
                (Hexagon) game.getChessboard().getFieldByNotation("b2"),
                (Hexagon) game.getChessboard().getFieldByNotation("c3"),
                (Hexagon) game.getChessboard().getFieldByNotation("d4"),
                (Hexagon) game.getChessboard().getFieldByNotation("e5"),
                (Hexagon) game.getChessboard().getFieldByNotation("f6"),
                (Hexagon) game.getChessboard().getFieldByNotation("g7"),
                (Hexagon) game.getChessboard().getFieldByNotation("h8"),
                (Hexagon) game.getChessboard().getFieldByNotation("i9"),
                (Hexagon) game.getChessboard().getFieldByNotation("j10"),
                (Hexagon) game.getChessboard().getFieldByNotation("k11"),
                (Hexagon) game.getChessboard().getFieldByNotation("l12"),
                (Hexagon) game.getChessboard().getFieldByNotation("m13"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link Rook} is on one of its starting positions.
     * @throws Exception
     */
    @Test
    public void testGetAttackableFields3() throws Exception {
        Rook rook = new Rook(game.getPlayerList().get(0));
        game.getChessboard().getFigures().add(rook.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a1")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("a2")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("b2")));
        List<Hexagon> possibleMovements = rook.getAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("b1"),
                (Hexagon) game.getChessboard().getFieldByNotation("c1"),
                (Hexagon) game.getChessboard().getFieldByNotation("d1"),
                (Hexagon) game.getChessboard().getFieldByNotation("e1"),
                (Hexagon) game.getChessboard().getFieldByNotation("f1"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link Rook} is on one of its starting positions.
     * @throws Exception
     */
    @Test
    public void testGetAttackableFields4() throws Exception {
        Rook rook = new Rook(game.getPlayerList().get(0));
        game.getChessboard().getFigures().add(rook.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a1")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("a2")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("b2")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("b1")));
        List<Hexagon> possibleMovements = rook.getAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = new ArrayList<>();
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link Rook} is on one of its starting positions.
     * @throws Exception
     */
    @Test
    public void testGetHypotheticalAttackableFields1() throws Exception {
        Rook rook = new Rook(game.getPlayerList().get(0));
        game.getChessboard().getFigures().add(rook.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a1")));
        List<Hexagon> possibleMovements = rook.getHypotheticalAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("b1"),
                (Hexagon) game.getChessboard().getFieldByNotation("c1"),
                (Hexagon) game.getChessboard().getFieldByNotation("d1"),
                (Hexagon) game.getChessboard().getFieldByNotation("e1"),
                (Hexagon) game.getChessboard().getFieldByNotation("f1"),
                (Hexagon) game.getChessboard().getFieldByNotation("b2"),
                (Hexagon) game.getChessboard().getFieldByNotation("c3"),
                (Hexagon) game.getChessboard().getFieldByNotation("d4"),
                (Hexagon) game.getChessboard().getFieldByNotation("e5"),
                (Hexagon) game.getChessboard().getFieldByNotation("f6"),
                (Hexagon) game.getChessboard().getFieldByNotation("g7"),
                (Hexagon) game.getChessboard().getFieldByNotation("h8"),
                (Hexagon) game.getChessboard().getFieldByNotation("i9"),
                (Hexagon) game.getChessboard().getFieldByNotation("j10"),
                (Hexagon) game.getChessboard().getFieldByNotation("k11"),
                (Hexagon) game.getChessboard().getFieldByNotation("l12"),
                (Hexagon) game.getChessboard().getFieldByNotation("m13"),
                (Hexagon) game.getChessboard().getFieldByNotation("a2"),
                (Hexagon) game.getChessboard().getFieldByNotation("a3"),
                (Hexagon) game.getChessboard().getFieldByNotation("a4"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link Rook} is on one of its starting positions.
     * @throws Exception
     */
    @Test
    public void testGetHypotheticalAttackableFields2() throws Exception {
        Rook rook = new Rook(game.getPlayerList().get(0));
        game.getChessboard().getFigures().add(rook.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a1")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("a2")));
        List<Hexagon> possibleMovements = rook.getHypotheticalAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("b1"),
                (Hexagon) game.getChessboard().getFieldByNotation("c1"),
                (Hexagon) game.getChessboard().getFieldByNotation("d1"),
                (Hexagon) game.getChessboard().getFieldByNotation("e1"),
                (Hexagon) game.getChessboard().getFieldByNotation("f1"),
                (Hexagon) game.getChessboard().getFieldByNotation("b2"),
                (Hexagon) game.getChessboard().getFieldByNotation("c3"),
                (Hexagon) game.getChessboard().getFieldByNotation("d4"),
                (Hexagon) game.getChessboard().getFieldByNotation("e5"),
                (Hexagon) game.getChessboard().getFieldByNotation("f6"),
                (Hexagon) game.getChessboard().getFieldByNotation("g7"),
                (Hexagon) game.getChessboard().getFieldByNotation("h8"),
                (Hexagon) game.getChessboard().getFieldByNotation("i9"),
                (Hexagon) game.getChessboard().getFieldByNotation("j10"),
                (Hexagon) game.getChessboard().getFieldByNotation("k11"),
                (Hexagon) game.getChessboard().getFieldByNotation("l12"),
                (Hexagon) game.getChessboard().getFieldByNotation("m13"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link Rook} is on one of its starting positions.
     * @throws Exception
     */
    @Test
    public void testGetHypotheticalAttackableFields3() throws Exception {
        Rook rook = new Rook(game.getPlayerList().get(0));
        game.getChessboard().getFigures().add(rook.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a1")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("a2")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("b2")));
        List<Hexagon> possibleMovements = rook.getHypotheticalAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("b1"),
                (Hexagon) game.getChessboard().getFieldByNotation("c1"),
                (Hexagon) game.getChessboard().getFieldByNotation("d1"),
                (Hexagon) game.getChessboard().getFieldByNotation("e1"),
                (Hexagon) game.getChessboard().getFieldByNotation("f1"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link Rook} is on one of its starting positions.
     * @throws Exception
     */
    @Test
    public void testGetHypotheticalAttackableFields4() throws Exception {
        Rook rook = new Rook(game.getPlayerList().get(0));
        game.getChessboard().getFigures().add(rook.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a1")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("a2")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("b2")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("b1")));
        List<Hexagon> possibleMovements = rook.getHypotheticalAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = new ArrayList<>();
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }
}