package de.mki.jchess.server.implementation.threePersonChess.figures;

import de.mki.jchess.server.Application;
import de.mki.jchess.server.implementation.threePersonChess.Direction;
import de.mki.jchess.server.implementation.threePersonChess.Hexagon;
import de.mki.jchess.commons.Figure;
import junitx.framework.ListAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Tests to check movements and behaviour of {@link King}s.
 * Created by Igor on 02.12.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class KingTest extends FigureTest {

    /**
     * The {@link King} is located on its starting position.
     * All bordering fields are free.
     * @throws Exception
     */
    @Test
    public void testGetPossibleMovements1() throws Exception {
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
                (Hexagon) game.getChessboard().getFieldByNotation("c6")
        );
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link King} is located on its starting position.
     * All bordering fields are free. An enemy {@link Queen} is checking the {@link King}.
     * @throws Exception
     */
    @Test
    public void testGetPossibleMovements2() throws Exception {
        King king = (King) getGame().getChessboard().getFigures().stream()
                .filter(o -> ((Figure) o).getClient().equals(getGame().getPlayerList().get(0)))
                .filter(o -> o instanceof King).findFirst().get();
        game.getChessboard().getFigures().add(new Queen(getGame().getPlayerList().get(1)).setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("i13")));
        List<Hexagon> possibleMovements = king.getPossibleMovements(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("b4"),
                (Hexagon) game.getChessboard().getFieldByNotation("b5"),
                (Hexagon) game.getChessboard().getFieldByNotation("b7"),
                (Hexagon) game.getChessboard().getFieldByNotation("a4"),
                (Hexagon) game.getChessboard().getFieldByNotation("a6"),
                (Hexagon) game.getChessboard().getFieldByNotation("c6")
        );
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link King} is located on its starting position.
     * All bordering fields are free. An enemy {@link Queen} is checking the {@link King}.
     * This {@link Queen} is beatable.
     * @throws Exception
     */
    @Test
    public void testGetPossibleMovements3() throws Exception {
        King king = (King) getGame().getChessboard().getFigures().stream()
                .filter(o -> ((Figure) o).getClient().equals(getGame().getPlayerList().get(0)))
                .filter(o -> o instanceof King).findFirst().get();
        game.getChessboard().getFigures().add(new Queen(getGame().getPlayerList().get(1)).setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("a4")));
        List<Hexagon> possibleMovements = king.getPossibleMovements(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("a4"),
                (Hexagon) game.getChessboard().getFieldByNotation("b7")
        );
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link King} is located on its starting position.
     * Check for castling, when a {@link Rook} exists and has not been moved yet.
     * @throws Exception
     */
    @Test
    public void testGetPossibleSpecialMovements1() throws Exception {
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

    /**
     * The {@link King} is located on its starting position.
     * Check for castling, when both {@link Rook}s exist and have not been moved yet.
     * @throws Exception
     */
    @Test
    public void testGetPossibleSpecialMovements2() throws Exception {
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

    /**
     * The {@link King} is located on its starting position.
     * Similar to {@link #testGetPossibleMovements1()}.
     * @throws Exception
     */
    @Test
    public void testGetAttackableFields1() throws Exception {
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
                (Hexagon) game.getChessboard().getFieldByNotation("c6")
        );
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link King} is located on its starting position.
     * Checking attackable fields when one diagonal path is blocked by bordering {@link Pawn}s.
     * @throws Exception
     */
    @Test
    public void testGetAttackableFields2() throws Exception {
        King king = (King) getGame().getChessboard().getFigures().stream()
                .filter(o -> ((Figure) o).getClient().equals(getGame().getPlayerList().get(0)))
                .filter(o -> o instanceof King).findFirst().get();
        game.getChessboard().getFigures().add(new Pawn(getGame().getPlayerList().get(0), Direction.DIAGONALBOTTOM, game.getChessboard()).setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("b5")));
        game.getChessboard().getFigures().add(new Pawn(getGame().getPlayerList().get(0), Direction.DIAGONALBOTTOM, game.getChessboard()).setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("b6")));
        List<Hexagon> possibleMovements = king.getAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("b4"),
                (Hexagon) game.getChessboard().getFieldByNotation("b7"),
                (Hexagon) game.getChessboard().getFieldByNotation("a4"),
                (Hexagon) game.getChessboard().getFieldByNotation("a6")
        );
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link King} is located on its starting position.
     * Similar to {@link #testGetAttackableFields1()}
     * @throws Exception
     */
    @Test
    public void testGetHypotheticalAttackableFields1() throws Exception {
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
                (Hexagon) game.getChessboard().getFieldByNotation("c6")
        );
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link King} is located on its starting position.
     * Similar to {@link #testGetAttackableFields2()}
     * @throws Exception
     */
    @Test
    public void testGetHypotheticalAttackableFields2() throws Exception {
        King king = (King) getGame().getChessboard().getFigures().stream()
                .filter(o -> ((Figure) o).getClient().equals(getGame().getPlayerList().get(0)))
                .filter(o -> o instanceof King).findFirst().get();
        game.getChessboard().getFigures().add(new Pawn(getGame().getPlayerList().get(0), Direction.DIAGONALBOTTOM, game.getChessboard()).setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("b5")));
        game.getChessboard().getFigures().add(new Pawn(getGame().getPlayerList().get(0), Direction.DIAGONALBOTTOM, game.getChessboard()).setPosition((Hexagon) getGame().getChessboard().getFieldByNotation("b6")));
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