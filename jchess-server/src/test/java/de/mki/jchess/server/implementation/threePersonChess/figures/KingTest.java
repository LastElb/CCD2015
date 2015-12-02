package de.mki.jchess.server.implementation.threePersonChess.figures;

import de.mki.jchess.server.implementation.threePersonChess.Direction;
import de.mki.jchess.server.implementation.threePersonChess.Hexagon;
import de.mki.jchess.server.model.Figure;
import junitx.framework.ListAssert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Igor on 02.12.2015.
 */
public class KingTest extends FigureTest {

    @Test
    public void testGetPossibleMovements1() throws Exception {
        setUpGame();
        King king = (King) getGame().getChessboard().getFigures().stream()
                .filter(o -> ((Figure) o).getClient().equals(getGame().getPlayerList().get(0)))
                .filter(o -> o instanceof King).findFirst().get();
        List<Hexagon> possibleMovements = king.getPossibleMovements(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("b4"),
                (Hexagon) game.getChessboard().getFieldByNotation("b5"),
                (Hexagon) game.getChessboard().getFieldByNotation("b6"),
                (Hexagon) game.getChessboard().getFieldByNotation("b7"),
                (Hexagon) game.getChessboard().getFieldByNotation("a4"),
                (Hexagon) game.getChessboard().getFieldByNotation("a6"),
                (Hexagon) game.getChessboard().getFieldByNotation("c5")
        );
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetPossibleMovements2() throws Exception {
        setUpGame();
        King king = (King) getGame().getChessboard().getFigures().stream()
                .filter(o -> ((Figure) o).getClient().equals(getGame().getPlayerList().get(0)))
                .filter(o -> o instanceof King).findFirst().get();
        game.getChessboard().getFigures().add(new Queen(getGame().getPlayerList().get(1)).setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("i13")));
        List<Hexagon> possibleMovements = king.getPossibleMovements(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("b4"),
                (Hexagon) game.getChessboard().getFieldByNotation("b5"),
                (Hexagon) game.getChessboard().getFieldByNotation("b6"),
                (Hexagon) game.getChessboard().getFieldByNotation("b7"),
                (Hexagon) game.getChessboard().getFieldByNotation("a4"),
                (Hexagon) game.getChessboard().getFieldByNotation("a6")
        );
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetPossibleSpecialMovements1() throws Exception {
        setUpGame();
        King king = (King) getGame().getChessboard().getFigures().stream()
                .filter(o -> ((Figure) o).getClient().equals(getGame().getPlayerList().get(0)))
                .filter(o -> o instanceof King).findFirst().get();
        game.getChessboard().getFigures().add(new Rook(getGame().getPlayerList().get(0)).setPosition((Hexagon) game.getChessboard().getFieldByNotation("a1")));
        List<Hexagon> possibleMovements = king.getPossibleSpecialMovements(game.getChessboard());
        List<Hexagon> expectedMovements = Collections.singletonList(
                (Hexagon) game.getChessboard().getFieldByNotation("a3")
        );
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetPossibleSpecialMovements2() throws Exception {
        setUpGame();
        King king = (King) getGame().getChessboard().getFigures().stream()
                .filter(o -> ((Figure) o).getClient().equals(getGame().getPlayerList().get(0)))
                .filter(o -> o instanceof King).findFirst().get();
        game.getChessboard().getFigures().add(new Rook(getGame().getPlayerList().get(0)).setPosition((Hexagon) game.getChessboard().getFieldByNotation("a1")));

        game.getChessboard().getFigures().add(new Rook(getGame().getPlayerList().get(0)).setPosition((Hexagon) game.getChessboard().getFieldByNotation("a8")));
        List<Hexagon> possibleMovements = king.getPossibleSpecialMovements(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("a3"),
                (Hexagon) game.getChessboard().getFieldByNotation("a7")
        );
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetAttackableFields1() throws Exception {
        setUpGame();
        King king = (King) getGame().getChessboard().getFigures().stream()
                .filter(o -> ((Figure) o).getClient().equals(getGame().getPlayerList().get(0)))
                .filter(o -> o instanceof King).findFirst().get();
        List<Hexagon> possibleMovements = king.getAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("b4"),
                (Hexagon) game.getChessboard().getFieldByNotation("b5"),
                (Hexagon) game.getChessboard().getFieldByNotation("b6"),
                (Hexagon) game.getChessboard().getFieldByNotation("b7"),
                (Hexagon) game.getChessboard().getFieldByNotation("a4"),
                (Hexagon) game.getChessboard().getFieldByNotation("a6"),
                (Hexagon) game.getChessboard().getFieldByNotation("c5")
        );
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetAttackableFields2() throws Exception {
        setUpGame();
        King king = (King) getGame().getChessboard().getFigures().stream()
                .filter(o -> ((Figure) o).getClient().equals(getGame().getPlayerList().get(0)))
                .filter(o -> o instanceof King).findFirst().get();
        game.getChessboard().getFigures().add(new Pawn(getGame().getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("b5")));
        game.getChessboard().getFigures().add(new Pawn(getGame().getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("b6")));
        List<Hexagon> possibleMovements = king.getAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("b4"),
                (Hexagon) game.getChessboard().getFieldByNotation("b7"),
                (Hexagon) game.getChessboard().getFieldByNotation("a4"),
                (Hexagon) game.getChessboard().getFieldByNotation("a6")
        );
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetHypotheticalAttackableFields1() throws Exception {
        setUpGame();
        King king = (King) getGame().getChessboard().getFigures().stream()
                .filter(o -> ((Figure) o).getClient().equals(getGame().getPlayerList().get(0)))
                .filter(o -> o instanceof King).findFirst().get();
        List<Hexagon> possibleMovements = king.getHypotheticalAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("b4"),
                (Hexagon) game.getChessboard().getFieldByNotation("b5"),
                (Hexagon) game.getChessboard().getFieldByNotation("b6"),
                (Hexagon) game.getChessboard().getFieldByNotation("b7"),
                (Hexagon) game.getChessboard().getFieldByNotation("a4"),
                (Hexagon) game.getChessboard().getFieldByNotation("a6"),
                (Hexagon) game.getChessboard().getFieldByNotation("c5")
        );
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    @Test
    public void testGetHypotheticalAttackableFields2() throws Exception {
        setUpGame();
        King king = (King) getGame().getChessboard().getFigures().stream()
                .filter(o -> ((Figure) o).getClient().equals(getGame().getPlayerList().get(0)))
                .filter(o -> o instanceof King).findFirst().get();
        game.getChessboard().getFigures().add(new Pawn(getGame().getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("b5")));
        game.getChessboard().getFigures().add(new Pawn(getGame().getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("b6")));
        List<Hexagon> possibleMovements = king.getHypotheticalAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("b4"),
                (Hexagon) game.getChessboard().getFieldByNotation("b7"),
                (Hexagon) game.getChessboard().getFieldByNotation("a4"),
                (Hexagon) game.getChessboard().getFieldByNotation("a6")
        );
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }
}