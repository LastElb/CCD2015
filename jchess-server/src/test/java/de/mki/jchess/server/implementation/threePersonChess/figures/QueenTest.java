package de.mki.jchess.server.implementation.threePersonChess.figures;

import de.mki.jchess.server.Application;
import de.mki.jchess.server.implementation.threePersonChess.Direction;
import de.mki.jchess.server.implementation.threePersonChess.Hexagon;
import junitx.framework.ListAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Igor on 01.12.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class QueenTest extends FigureTest {

    @Test
    public void testGetPossibleMovements1() throws Exception {
        setUpGame();
        Queen queen = new Queen(game.getPlayerList().get(0));
        queen.setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("a4"));
        getGame().getChessboard().getFigures().add(queen);
        List<Hexagon> possibleMovements = queen.getPossibleMovements(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("a1"),
                (Hexagon) game.getChessboard().getFieldByNotation("a2"),
                (Hexagon) game.getChessboard().getFieldByNotation("a3"),
                (Hexagon) game.getChessboard().getFieldByNotation("b3"),
                (Hexagon) game.getChessboard().getFieldByNotation("c2"),
                (Hexagon) game.getChessboard().getFieldByNotation("d1"),
                (Hexagon) game.getChessboard().getFieldByNotation("b4"),
                (Hexagon) game.getChessboard().getFieldByNotation("c4"),
                (Hexagon) game.getChessboard().getFieldByNotation("d4"),
                (Hexagon) game.getChessboard().getFieldByNotation("e4"),
                (Hexagon) game.getChessboard().getFieldByNotation("f4"),
                (Hexagon) game.getChessboard().getFieldByNotation("g4"),
                (Hexagon) game.getChessboard().getFieldByNotation("h4"),
                (Hexagon) game.getChessboard().getFieldByNotation("i4"),
                (Hexagon) game.getChessboard().getFieldByNotation("c5"),
                (Hexagon) game.getChessboard().getFieldByNotation("e6"),
                (Hexagon) game.getChessboard().getFieldByNotation("g7"),
                (Hexagon) game.getChessboard().getFieldByNotation("i8"),
                (Hexagon) game.getChessboard().getFieldByNotation("k9"),
                (Hexagon) game.getChessboard().getFieldByNotation("m10"),
                (Hexagon) game.getChessboard().getFieldByNotation("b5"),
                (Hexagon) game.getChessboard().getFieldByNotation("c6"),
                (Hexagon) game.getChessboard().getFieldByNotation("d7"),
                (Hexagon) game.getChessboard().getFieldByNotation("e8"),
                (Hexagon) game.getChessboard().getFieldByNotation("f9"),
                (Hexagon) game.getChessboard().getFieldByNotation("g10"),
                (Hexagon) game.getChessboard().getFieldByNotation("h11"),
                (Hexagon) game.getChessboard().getFieldByNotation("i12"),
                (Hexagon) game.getChessboard().getFieldByNotation("j13"),
                (Hexagon) game.getChessboard().getFieldByNotation("b6"),
                (Hexagon) game.getChessboard().getFieldByNotation("c8"),
                (Hexagon) game.getChessboard().getFieldByNotation("d10"),
                (Hexagon) game.getChessboard().getFieldByNotation("e12")
        );
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetPossibleMovements2() throws Exception {
        setUpGame();
        Queen queen = new Queen(game.getPlayerList().get(0));
        queen.setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("a4"));
        getGame().getChessboard().getFigures().add(queen);
        getGame().getChessboard().getFigures().add(new Queen(getGame().getPlayerList().get(1)).setPosition((Hexagon) game.getChessboard().getFieldByNotation("i13")));
        List<Hexagon> possibleMovements = queen.getPossibleMovements(game.getChessboard());
        List<Hexagon> expectedMovements = new ArrayList<>();
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetPossibleSpecialMovements() throws Exception {
        setUpGame();
        Queen queen = new Queen(game.getPlayerList().get(0));
        queen.setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("a4"));
        getGame().getChessboard().getFigures().add(queen);
        List<Hexagon> possibleMovements = queen.getPossibleSpecialMovements(game.getChessboard());
        List<Hexagon> expectedMovements = new ArrayList<>();
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetAttackableFields1() throws Exception {
        setUpGame();
        Queen queen = new Queen(game.getPlayerList().get(0));
        queen.setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("a4"));
        getGame().getChessboard().getFigures().add(queen);
        List<Hexagon> possibleMovements = queen.getAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("a1"),
                (Hexagon) game.getChessboard().getFieldByNotation("a2"),
                (Hexagon) game.getChessboard().getFieldByNotation("a3"),
                (Hexagon) game.getChessboard().getFieldByNotation("b3"),
                (Hexagon) game.getChessboard().getFieldByNotation("c2"),
                (Hexagon) game.getChessboard().getFieldByNotation("d1"),
                (Hexagon) game.getChessboard().getFieldByNotation("b4"),
                (Hexagon) game.getChessboard().getFieldByNotation("c4"),
                (Hexagon) game.getChessboard().getFieldByNotation("d4"),
                (Hexagon) game.getChessboard().getFieldByNotation("e4"),
                (Hexagon) game.getChessboard().getFieldByNotation("f4"),
                (Hexagon) game.getChessboard().getFieldByNotation("g4"),
                (Hexagon) game.getChessboard().getFieldByNotation("h4"),
                (Hexagon) game.getChessboard().getFieldByNotation("i4"),
                (Hexagon) game.getChessboard().getFieldByNotation("c5"),
                (Hexagon) game.getChessboard().getFieldByNotation("e6"),
                (Hexagon) game.getChessboard().getFieldByNotation("g7"),
                (Hexagon) game.getChessboard().getFieldByNotation("i8"),
                (Hexagon) game.getChessboard().getFieldByNotation("k9"),
                (Hexagon) game.getChessboard().getFieldByNotation("m10"),
                (Hexagon) game.getChessboard().getFieldByNotation("b5"),
                (Hexagon) game.getChessboard().getFieldByNotation("c6"),
                (Hexagon) game.getChessboard().getFieldByNotation("d7"),
                (Hexagon) game.getChessboard().getFieldByNotation("e8"),
                (Hexagon) game.getChessboard().getFieldByNotation("f9"),
                (Hexagon) game.getChessboard().getFieldByNotation("g10"),
                (Hexagon) game.getChessboard().getFieldByNotation("h11"),
                (Hexagon) game.getChessboard().getFieldByNotation("i12"),
                (Hexagon) game.getChessboard().getFieldByNotation("j13"),
                (Hexagon) game.getChessboard().getFieldByNotation("b6"),
                (Hexagon) game.getChessboard().getFieldByNotation("c8"),
                (Hexagon) game.getChessboard().getFieldByNotation("d10"),
                (Hexagon) game.getChessboard().getFieldByNotation("e12")
        );
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetAttackableFields2() throws Exception {
        setUpGame();
        Queen queen = new Queen(game.getPlayerList().get(0));
        queen.setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("a4"));
        getGame().getChessboard().getFigures().add(queen);
        getGame().getChessboard().getFigures().add(new Bishop(getGame().getPlayerList().get(0)).setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("a3")));
        List<Hexagon> possibleMovements = queen.getAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("b3"),
                (Hexagon) game.getChessboard().getFieldByNotation("c2"),
                (Hexagon) game.getChessboard().getFieldByNotation("d1"),
                (Hexagon) game.getChessboard().getFieldByNotation("b4"),
                (Hexagon) game.getChessboard().getFieldByNotation("c4"),
                (Hexagon) game.getChessboard().getFieldByNotation("d4"),
                (Hexagon) game.getChessboard().getFieldByNotation("e4"),
                (Hexagon) game.getChessboard().getFieldByNotation("f4"),
                (Hexagon) game.getChessboard().getFieldByNotation("g4"),
                (Hexagon) game.getChessboard().getFieldByNotation("h4"),
                (Hexagon) game.getChessboard().getFieldByNotation("i4"),
                (Hexagon) game.getChessboard().getFieldByNotation("c5"),
                (Hexagon) game.getChessboard().getFieldByNotation("e6"),
                (Hexagon) game.getChessboard().getFieldByNotation("g7"),
                (Hexagon) game.getChessboard().getFieldByNotation("i8"),
                (Hexagon) game.getChessboard().getFieldByNotation("k9"),
                (Hexagon) game.getChessboard().getFieldByNotation("m10"),
                (Hexagon) game.getChessboard().getFieldByNotation("b5"),
                (Hexagon) game.getChessboard().getFieldByNotation("c6"),
                (Hexagon) game.getChessboard().getFieldByNotation("d7"),
                (Hexagon) game.getChessboard().getFieldByNotation("e8"),
                (Hexagon) game.getChessboard().getFieldByNotation("f9"),
                (Hexagon) game.getChessboard().getFieldByNotation("g10"),
                (Hexagon) game.getChessboard().getFieldByNotation("h11"),
                (Hexagon) game.getChessboard().getFieldByNotation("i12"),
                (Hexagon) game.getChessboard().getFieldByNotation("j13"),
                (Hexagon) game.getChessboard().getFieldByNotation("b6"),
                (Hexagon) game.getChessboard().getFieldByNotation("c8"),
                (Hexagon) game.getChessboard().getFieldByNotation("d10"),
                (Hexagon) game.getChessboard().getFieldByNotation("e12")
        );
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetAttackableFields3() throws Exception {
        setUpGame();
        Queen queen = new Queen(game.getPlayerList().get(0));
        queen.setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("a4"));
        getGame().getChessboard().getFigures().add(queen);
        getGame().getChessboard().getFigures().add(new Bishop(getGame().getPlayerList().get(0)).setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("a3")));
        getGame().getChessboard().getFigures().add(new Pawn(getGame().getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("b4")));
        List<Hexagon> possibleMovements = queen.getAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("c5"),
                (Hexagon) game.getChessboard().getFieldByNotation("e6"),
                (Hexagon) game.getChessboard().getFieldByNotation("g7"),
                (Hexagon) game.getChessboard().getFieldByNotation("i8"),
                (Hexagon) game.getChessboard().getFieldByNotation("k9"),
                (Hexagon) game.getChessboard().getFieldByNotation("m10"),
                (Hexagon) game.getChessboard().getFieldByNotation("b5"),
                (Hexagon) game.getChessboard().getFieldByNotation("c6"),
                (Hexagon) game.getChessboard().getFieldByNotation("d7"),
                (Hexagon) game.getChessboard().getFieldByNotation("e8"),
                (Hexagon) game.getChessboard().getFieldByNotation("f9"),
                (Hexagon) game.getChessboard().getFieldByNotation("g10"),
                (Hexagon) game.getChessboard().getFieldByNotation("h11"),
                (Hexagon) game.getChessboard().getFieldByNotation("i12"),
                (Hexagon) game.getChessboard().getFieldByNotation("j13"),
                (Hexagon) game.getChessboard().getFieldByNotation("b6"),
                (Hexagon) game.getChessboard().getFieldByNotation("c8"),
                (Hexagon) game.getChessboard().getFieldByNotation("d10"),
                (Hexagon) game.getChessboard().getFieldByNotation("e12")
        );
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetAttackableFields4() throws Exception {
        setUpGame();
        Queen queen = new Queen(game.getPlayerList().get(0));
        queen.setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("a4"));
        getGame().getChessboard().getFigures().add(queen);
        getGame().getChessboard().getFigures().add(new Bishop(getGame().getPlayerList().get(0)).setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("a3")));
        getGame().getChessboard().getFigures().add(new Pawn(getGame().getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("b3")));
        List<Hexagon> possibleMovements = queen.getAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("b4"),
                (Hexagon) game.getChessboard().getFieldByNotation("c4"),
                (Hexagon) game.getChessboard().getFieldByNotation("d4"),
                (Hexagon) game.getChessboard().getFieldByNotation("e4"),
                (Hexagon) game.getChessboard().getFieldByNotation("f4"),
                (Hexagon) game.getChessboard().getFieldByNotation("g4"),
                (Hexagon) game.getChessboard().getFieldByNotation("h4"),
                (Hexagon) game.getChessboard().getFieldByNotation("i4"),
                (Hexagon) game.getChessboard().getFieldByNotation("c5"),
                (Hexagon) game.getChessboard().getFieldByNotation("e6"),
                (Hexagon) game.getChessboard().getFieldByNotation("g7"),
                (Hexagon) game.getChessboard().getFieldByNotation("i8"),
                (Hexagon) game.getChessboard().getFieldByNotation("k9"),
                (Hexagon) game.getChessboard().getFieldByNotation("m10"),
                (Hexagon) game.getChessboard().getFieldByNotation("b5"),
                (Hexagon) game.getChessboard().getFieldByNotation("c6"),
                (Hexagon) game.getChessboard().getFieldByNotation("d7"),
                (Hexagon) game.getChessboard().getFieldByNotation("e8"),
                (Hexagon) game.getChessboard().getFieldByNotation("f9"),
                (Hexagon) game.getChessboard().getFieldByNotation("g10"),
                (Hexagon) game.getChessboard().getFieldByNotation("h11"),
                (Hexagon) game.getChessboard().getFieldByNotation("i12"),
                (Hexagon) game.getChessboard().getFieldByNotation("j13"),
                (Hexagon) game.getChessboard().getFieldByNotation("b6"),
                (Hexagon) game.getChessboard().getFieldByNotation("c8"),
                (Hexagon) game.getChessboard().getFieldByNotation("d10"),
                (Hexagon) game.getChessboard().getFieldByNotation("e12")
        );
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetAttackableFields5() throws Exception {
        setUpGame();
        Queen queen = new Queen(game.getPlayerList().get(0));
        queen.setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("a4"));
        getGame().getChessboard().getFigures().add(queen);
        getGame().getChessboard().getFigures().add(new Bishop(getGame().getPlayerList().get(0)).setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("a3")));
        getGame().getChessboard().getFigures().add(new Pawn(getGame().getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("b3")));
        getGame().getChessboard().getFigures().add(new Pawn(getGame().getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("b4")));
        getGame().getChessboard().getFigures().add(new Pawn(getGame().getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("b5")));
        List<Hexagon> possibleMovements = queen.getAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = new ArrayList<>();
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetHypotheticalAttackableFields1() throws Exception {
        setUpGame();
        Queen queen = new Queen(game.getPlayerList().get(0));
        queen.setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("a4"));
        getGame().getChessboard().getFigures().add(queen);
        List<Hexagon> possibleMovements = queen.getHypotheticalAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("a1"),
                (Hexagon) game.getChessboard().getFieldByNotation("a2"),
                (Hexagon) game.getChessboard().getFieldByNotation("a3"),
                (Hexagon) game.getChessboard().getFieldByNotation("b3"),
                (Hexagon) game.getChessboard().getFieldByNotation("c2"),
                (Hexagon) game.getChessboard().getFieldByNotation("d1"),
                (Hexagon) game.getChessboard().getFieldByNotation("b4"),
                (Hexagon) game.getChessboard().getFieldByNotation("c4"),
                (Hexagon) game.getChessboard().getFieldByNotation("d4"),
                (Hexagon) game.getChessboard().getFieldByNotation("e4"),
                (Hexagon) game.getChessboard().getFieldByNotation("f4"),
                (Hexagon) game.getChessboard().getFieldByNotation("g4"),
                (Hexagon) game.getChessboard().getFieldByNotation("h4"),
                (Hexagon) game.getChessboard().getFieldByNotation("i4"),
                (Hexagon) game.getChessboard().getFieldByNotation("c5"),
                (Hexagon) game.getChessboard().getFieldByNotation("e6"),
                (Hexagon) game.getChessboard().getFieldByNotation("g7"),
                (Hexagon) game.getChessboard().getFieldByNotation("i8"),
                (Hexagon) game.getChessboard().getFieldByNotation("k9"),
                (Hexagon) game.getChessboard().getFieldByNotation("m10"),
                (Hexagon) game.getChessboard().getFieldByNotation("b5"),
                (Hexagon) game.getChessboard().getFieldByNotation("c6"),
                (Hexagon) game.getChessboard().getFieldByNotation("d7"),
                (Hexagon) game.getChessboard().getFieldByNotation("e8"),
                (Hexagon) game.getChessboard().getFieldByNotation("f9"),
                (Hexagon) game.getChessboard().getFieldByNotation("g10"),
                (Hexagon) game.getChessboard().getFieldByNotation("h11"),
                (Hexagon) game.getChessboard().getFieldByNotation("i12"),
                (Hexagon) game.getChessboard().getFieldByNotation("j13"),
                (Hexagon) game.getChessboard().getFieldByNotation("b6"),
                (Hexagon) game.getChessboard().getFieldByNotation("c8"),
                (Hexagon) game.getChessboard().getFieldByNotation("d10"),
                (Hexagon) game.getChessboard().getFieldByNotation("e12")
        );
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetHypotheticalAttackableFields2() throws Exception {
        setUpGame();
        Queen queen = new Queen(game.getPlayerList().get(0));
        queen.setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("a4"));
        getGame().getChessboard().getFigures().add(queen);
        getGame().getChessboard().getFigures().add(new Bishop(getGame().getPlayerList().get(0)).setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("a3")));
        List<Hexagon> possibleMovements = queen.getHypotheticalAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("b3"),
                (Hexagon) game.getChessboard().getFieldByNotation("c2"),
                (Hexagon) game.getChessboard().getFieldByNotation("d1"),
                (Hexagon) game.getChessboard().getFieldByNotation("b4"),
                (Hexagon) game.getChessboard().getFieldByNotation("c4"),
                (Hexagon) game.getChessboard().getFieldByNotation("d4"),
                (Hexagon) game.getChessboard().getFieldByNotation("e4"),
                (Hexagon) game.getChessboard().getFieldByNotation("f4"),
                (Hexagon) game.getChessboard().getFieldByNotation("g4"),
                (Hexagon) game.getChessboard().getFieldByNotation("h4"),
                (Hexagon) game.getChessboard().getFieldByNotation("i4"),
                (Hexagon) game.getChessboard().getFieldByNotation("c5"),
                (Hexagon) game.getChessboard().getFieldByNotation("e6"),
                (Hexagon) game.getChessboard().getFieldByNotation("g7"),
                (Hexagon) game.getChessboard().getFieldByNotation("i8"),
                (Hexagon) game.getChessboard().getFieldByNotation("k9"),
                (Hexagon) game.getChessboard().getFieldByNotation("m10"),
                (Hexagon) game.getChessboard().getFieldByNotation("b5"),
                (Hexagon) game.getChessboard().getFieldByNotation("c6"),
                (Hexagon) game.getChessboard().getFieldByNotation("d7"),
                (Hexagon) game.getChessboard().getFieldByNotation("e8"),
                (Hexagon) game.getChessboard().getFieldByNotation("f9"),
                (Hexagon) game.getChessboard().getFieldByNotation("g10"),
                (Hexagon) game.getChessboard().getFieldByNotation("h11"),
                (Hexagon) game.getChessboard().getFieldByNotation("i12"),
                (Hexagon) game.getChessboard().getFieldByNotation("j13"),
                (Hexagon) game.getChessboard().getFieldByNotation("b6"),
                (Hexagon) game.getChessboard().getFieldByNotation("c8"),
                (Hexagon) game.getChessboard().getFieldByNotation("d10"),
                (Hexagon) game.getChessboard().getFieldByNotation("e12")
        );
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetHypotheticalAttackableFields3() throws Exception {
        setUpGame();
        Queen queen = new Queen(game.getPlayerList().get(0));
        queen.setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("a4"));
        getGame().getChessboard().getFigures().add(queen);
        getGame().getChessboard().getFigures().add(new Bishop(getGame().getPlayerList().get(0)).setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("a3")));
        getGame().getChessboard().getFigures().add(new Pawn(getGame().getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("b4")));
        List<Hexagon> possibleMovements = queen.getHypotheticalAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("c5"),
                (Hexagon) game.getChessboard().getFieldByNotation("e6"),
                (Hexagon) game.getChessboard().getFieldByNotation("g7"),
                (Hexagon) game.getChessboard().getFieldByNotation("i8"),
                (Hexagon) game.getChessboard().getFieldByNotation("k9"),
                (Hexagon) game.getChessboard().getFieldByNotation("m10"),
                (Hexagon) game.getChessboard().getFieldByNotation("b5"),
                (Hexagon) game.getChessboard().getFieldByNotation("c6"),
                (Hexagon) game.getChessboard().getFieldByNotation("d7"),
                (Hexagon) game.getChessboard().getFieldByNotation("e8"),
                (Hexagon) game.getChessboard().getFieldByNotation("f9"),
                (Hexagon) game.getChessboard().getFieldByNotation("g10"),
                (Hexagon) game.getChessboard().getFieldByNotation("h11"),
                (Hexagon) game.getChessboard().getFieldByNotation("i12"),
                (Hexagon) game.getChessboard().getFieldByNotation("j13"),
                (Hexagon) game.getChessboard().getFieldByNotation("b6"),
                (Hexagon) game.getChessboard().getFieldByNotation("c8"),
                (Hexagon) game.getChessboard().getFieldByNotation("d10"),
                (Hexagon) game.getChessboard().getFieldByNotation("e12")
        );
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetHypotheticalAttackableFields4() throws Exception {
        setUpGame();
        Queen queen = new Queen(game.getPlayerList().get(0));
        queen.setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("a4"));
        getGame().getChessboard().getFigures().add(queen);
        getGame().getChessboard().getFigures().add(new Bishop(getGame().getPlayerList().get(0)).setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("a3")));
        getGame().getChessboard().getFigures().add(new Pawn(getGame().getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("b3")));
        List<Hexagon> possibleMovements = queen.getHypotheticalAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("b4"),
                (Hexagon) game.getChessboard().getFieldByNotation("c4"),
                (Hexagon) game.getChessboard().getFieldByNotation("d4"),
                (Hexagon) game.getChessboard().getFieldByNotation("e4"),
                (Hexagon) game.getChessboard().getFieldByNotation("f4"),
                (Hexagon) game.getChessboard().getFieldByNotation("g4"),
                (Hexagon) game.getChessboard().getFieldByNotation("h4"),
                (Hexagon) game.getChessboard().getFieldByNotation("i4"),
                (Hexagon) game.getChessboard().getFieldByNotation("c5"),
                (Hexagon) game.getChessboard().getFieldByNotation("e6"),
                (Hexagon) game.getChessboard().getFieldByNotation("g7"),
                (Hexagon) game.getChessboard().getFieldByNotation("i8"),
                (Hexagon) game.getChessboard().getFieldByNotation("k9"),
                (Hexagon) game.getChessboard().getFieldByNotation("m10"),
                (Hexagon) game.getChessboard().getFieldByNotation("b5"),
                (Hexagon) game.getChessboard().getFieldByNotation("c6"),
                (Hexagon) game.getChessboard().getFieldByNotation("d7"),
                (Hexagon) game.getChessboard().getFieldByNotation("e8"),
                (Hexagon) game.getChessboard().getFieldByNotation("f9"),
                (Hexagon) game.getChessboard().getFieldByNotation("g10"),
                (Hexagon) game.getChessboard().getFieldByNotation("h11"),
                (Hexagon) game.getChessboard().getFieldByNotation("i12"),
                (Hexagon) game.getChessboard().getFieldByNotation("j13"),
                (Hexagon) game.getChessboard().getFieldByNotation("b6"),
                (Hexagon) game.getChessboard().getFieldByNotation("c8"),
                (Hexagon) game.getChessboard().getFieldByNotation("d10"),
                (Hexagon) game.getChessboard().getFieldByNotation("e12")
        );
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetHypotheticalAttackableFields5() throws Exception {
        setUpGame();
        Queen queen = new Queen(game.getPlayerList().get(0));
        queen.setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("a4"));
        getGame().getChessboard().getFigures().add(queen);
        getGame().getChessboard().getFigures().add(new Bishop(getGame().getPlayerList().get(0)).setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("a3")));
        getGame().getChessboard().getFigures().add(new Pawn(getGame().getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("b3")));
        getGame().getChessboard().getFigures().add(new Pawn(getGame().getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("b4")));
        getGame().getChessboard().getFigures().add(new Pawn(getGame().getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("b5")));
        List<Hexagon> possibleMovements = queen.getHypotheticalAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = new ArrayList<>();
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }
}