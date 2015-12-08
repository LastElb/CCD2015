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
 * Created by Igor on 30.11.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class RookTest extends FigureTest {

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

    @Test
    public void testGetPossibleMovements5() throws Exception {
        Rook rook = new Rook(game.getPlayerList().get(0));
        game.getChessboard().getFigures().add(rook.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a1")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(1), Direction.DIAGONALTOPRIGHT).setPosition((Hexagon) game.getChessboard().getFieldByNotation("b4")));
        List<Hexagon> possibleMovements = rook.getPossibleMovements(game.getChessboard());
        List<Hexagon> expectedMovements = new ArrayList<>();
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

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


    @Test
    public void testGetPossibleSpecialMovements1() throws Exception {
        Rook rook = new Rook(game.getPlayerList().get(0));
        game.getChessboard().getFigures().add(rook.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a1")));
        List<Hexagon> possibleMovements = rook.getPossibleSpecialMovements(game.getChessboard());
        List<Hexagon> expectedMovements = Collections.singletonList(
                (Hexagon) game.getChessboard().getFieldByNotation("a5"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetPossibleSpecialMovements2() throws Exception {
        Rook rook = new Rook(game.getPlayerList().get(0));
        game.getChessboard().getFigures().add(rook.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a8")));
        List<Hexagon> possibleMovements = rook.getPossibleSpecialMovements(game.getChessboard());
        List<Hexagon> expectedMovements = Collections.singletonList(
                (Hexagon) game.getChessboard().getFieldByNotation("a5"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetPossibleSpecialMovements3() throws Exception {
        Rook rook = new Rook(game.getPlayerList().get(0));
        game.getGameHistory().add(new HistoryEntry().setChessboardEvents(Collections.singletonList(new MovementEvent().setFigureId(rook.getId()).setFromNotation("a1").setToNotation("a2"))));
        game.getChessboard().getFigures().add(rook.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a2")));
        List<Hexagon> possibleMovements = rook.getPossibleSpecialMovements(game.getChessboard());
        List<Hexagon> expectedMovements = new ArrayList<>();
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetPossibleSpecialMovements4() throws Exception {
        Rook rook = new Rook(game.getPlayerList().get(0));
        game.getGameHistory().add(new HistoryEntry().setChessboardEvents(Collections.singletonList(new MovementEvent().setFigureId(rook.getId()).setFromNotation("a8").setToNotation("a7"))));
        game.getChessboard().getFigures().add(rook.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a7")));
        List<Hexagon> possibleMovements = rook.getPossibleSpecialMovements(game.getChessboard());
        List<Hexagon> expectedMovements = new ArrayList<>();
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

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