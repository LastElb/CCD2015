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

/**
 * Created by Igor on 02.12.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class KnightTest extends FigureTest {

    @Test
    public void testGetPossibleMovements1() throws Exception {
        setUpGame();
        Knight knight = new Knight(game.getPlayerList().get(0));
        game.getChessboard().getFigures().add(knight.setPosition((Hexagon) game.getChessboard().getFieldByNotation("g8")));
        List<Hexagon> possibleMovements = knight.getPossibleMovements(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("d7"),
                (Hexagon) game.getChessboard().getFieldByNotation("e9"),
                (Hexagon) game.getChessboard().getFieldByNotation("f10"),
                (Hexagon) game.getChessboard().getFieldByNotation("h11"),
                (Hexagon) game.getChessboard().getFieldByNotation("i11"),
                (Hexagon) game.getChessboard().getFieldByNotation("j10"),
                (Hexagon) game.getChessboard().getFieldByNotation("j9"),
                (Hexagon) game.getChessboard().getFieldByNotation("i7"),
                (Hexagon) game.getChessboard().getFieldByNotation("h6"),
                (Hexagon) game.getChessboard().getFieldByNotation("f5"),
                (Hexagon) game.getChessboard().getFieldByNotation("e5"),
                (Hexagon) game.getChessboard().getFieldByNotation("d6")
        );
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetPossibleMovements2() throws Exception {
        setUpGame();
        Knight knight = new Knight(game.getPlayerList().get(0));
        game.getChessboard().getFigures().add(knight.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a2")));
        List<Hexagon> possibleMovements = knight.getPossibleMovements(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("c1"),
                (Hexagon) game.getChessboard().getFieldByNotation("d3"),
                (Hexagon) game.getChessboard().getFieldByNotation("d4"),
                (Hexagon) game.getChessboard().getFieldByNotation("c5"),
                (Hexagon) game.getChessboard().getFieldByNotation("b5")
        );
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetPossibleMovements3() throws Exception {
        setUpGame();
        Knight knight = new Knight(game.getPlayerList().get(0));
        game.getChessboard().getFigures().add(knight.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a2")));
        game.getChessboard().getFigures().add(new Rook(game.getPlayerList().get(1)).setPosition((Hexagon) game.getChessboard().getFieldByNotation("c5")));
        List<Hexagon> possibleMovements = knight.getPossibleMovements(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("c5"),
                (Hexagon) game.getChessboard().getFieldByNotation("b5")
        );
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetPossibleSpecialMovements1() throws Exception {
        Knight knight = new Knight(game.getPlayerList().get(0));
        game.getChessboard().getFigures().add(knight.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a2")));
        List<Hexagon> possibleMovements = knight.getPossibleSpecialMovements(game.getChessboard());
        List<Hexagon> expectedMovements = new ArrayList<>();
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetAttackableFields1() throws Exception {
        Knight knight = new Knight(game.getPlayerList().get(0));
        game.getChessboard().getFigures().add(knight.setPosition((Hexagon) game.getChessboard().getFieldByNotation("g8")));
        List<Hexagon> possibleMovements = knight.getAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("d7"),
                (Hexagon) game.getChessboard().getFieldByNotation("e9"),
                (Hexagon) game.getChessboard().getFieldByNotation("f10"),
                (Hexagon) game.getChessboard().getFieldByNotation("h11"),
                (Hexagon) game.getChessboard().getFieldByNotation("i11"),
                (Hexagon) game.getChessboard().getFieldByNotation("j10"),
                (Hexagon) game.getChessboard().getFieldByNotation("j9"),
                (Hexagon) game.getChessboard().getFieldByNotation("i7"),
                (Hexagon) game.getChessboard().getFieldByNotation("h6"),
                (Hexagon) game.getChessboard().getFieldByNotation("f5"),
                (Hexagon) game.getChessboard().getFieldByNotation("e5"),
                (Hexagon) game.getChessboard().getFieldByNotation("d6")
        );
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetAttackableFields2() throws Exception {
        Knight knight = new Knight(game.getPlayerList().get(0));
        game.getChessboard().getFigures().add(knight.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a2")));
        List<Hexagon> possibleMovements = knight.getAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("c1"),
                (Hexagon) game.getChessboard().getFieldByNotation("d3"),
                (Hexagon) game.getChessboard().getFieldByNotation("d4"),
                (Hexagon) game.getChessboard().getFieldByNotation("c5"),
                (Hexagon) game.getChessboard().getFieldByNotation("b5")
        );
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetAttackableFields3() throws Exception {
        Knight knight = new Knight(game.getPlayerList().get(0));
        game.getChessboard().getFigures().add(knight.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a2")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(1), Direction.DIAGONALBOTTOM, game.getChessboard()).setPosition((Hexagon) game.getChessboard().getFieldByNotation("b5")));
        List<Hexagon> possibleMovements = knight.getAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("c1"),
                (Hexagon) game.getChessboard().getFieldByNotation("d3"),
                (Hexagon) game.getChessboard().getFieldByNotation("d4"),
                (Hexagon) game.getChessboard().getFieldByNotation("c5"),
                (Hexagon) game.getChessboard().getFieldByNotation("b5")
        );
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetAttackableFields4() throws Exception {
        Knight knight = new Knight(game.getPlayerList().get(0));
        game.getChessboard().getFigures().add(knight.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a2")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM, game.getChessboard()).setPosition((Hexagon) game.getChessboard().getFieldByNotation("b5")));
        List<Hexagon> possibleMovements = knight.getAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("c1"),
                (Hexagon) game.getChessboard().getFieldByNotation("d3"),
                (Hexagon) game.getChessboard().getFieldByNotation("d4"),
                (Hexagon) game.getChessboard().getFieldByNotation("c5")
        );
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }


    @Test
    public void testGetHypotheticalAttackableFields1() throws Exception {
        Knight knight = new Knight(game.getPlayerList().get(0));
        game.getChessboard().getFigures().add(knight.setPosition((Hexagon) game.getChessboard().getFieldByNotation("g8")));
        List<Hexagon> possibleMovements = knight.getHypotheticalAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("d7"),
                (Hexagon) game.getChessboard().getFieldByNotation("e9"),
                (Hexagon) game.getChessboard().getFieldByNotation("f10"),
                (Hexagon) game.getChessboard().getFieldByNotation("h11"),
                (Hexagon) game.getChessboard().getFieldByNotation("i11"),
                (Hexagon) game.getChessboard().getFieldByNotation("j10"),
                (Hexagon) game.getChessboard().getFieldByNotation("j9"),
                (Hexagon) game.getChessboard().getFieldByNotation("i7"),
                (Hexagon) game.getChessboard().getFieldByNotation("h6"),
                (Hexagon) game.getChessboard().getFieldByNotation("f5"),
                (Hexagon) game.getChessboard().getFieldByNotation("e5"),
                (Hexagon) game.getChessboard().getFieldByNotation("d6")
        );
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetHypotheticalAttackableFields2() throws Exception {
        Knight knight = new Knight(game.getPlayerList().get(0));
        game.getChessboard().getFigures().add(knight.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a2")));
        List<Hexagon> possibleMovements = knight.getHypotheticalAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("c1"),
                (Hexagon) game.getChessboard().getFieldByNotation("d3"),
                (Hexagon) game.getChessboard().getFieldByNotation("d4"),
                (Hexagon) game.getChessboard().getFieldByNotation("c5"),
                (Hexagon) game.getChessboard().getFieldByNotation("b5")
        );
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }
}