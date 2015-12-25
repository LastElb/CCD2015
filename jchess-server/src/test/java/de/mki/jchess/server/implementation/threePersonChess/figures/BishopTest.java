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
 * Test cases to check movements and attack vectors for {@link Bishop}s
 * Created by Igor on 01.12.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class BishopTest extends FigureTest {

    /**
     * The {@link Bishop} is located on one of its starting positions. No figures are blocking its way.
     * @throws Exception
     */
    @Test
    public void testGetPossibleMovements1() throws Exception {
        Bishop bishop = new Bishop(game.getPlayerList().get(0));
        bishop.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a3"));
        game.getChessboard().getFigures().add(bishop);

        List<Hexagon> possibleMovements = bishop.getPossibleMovements(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("c4"),
                (Hexagon) game.getChessboard().getFieldByNotation("e5"),
                (Hexagon) game.getChessboard().getFieldByNotation("g6"),
                (Hexagon) game.getChessboard().getFieldByNotation("i7"),
                (Hexagon) game.getChessboard().getFieldByNotation("k8"),
                (Hexagon) game.getChessboard().getFieldByNotation("m9"),
                (Hexagon) game.getChessboard().getFieldByNotation("b2"),
                (Hexagon) game.getChessboard().getFieldByNotation("c1"),
                (Hexagon) game.getChessboard().getFieldByNotation("b5"),
                (Hexagon) game.getChessboard().getFieldByNotation("c7"),
                (Hexagon) game.getChessboard().getFieldByNotation("d9"),
                (Hexagon) game.getChessboard().getFieldByNotation("e11"),
                (Hexagon) game.getChessboard().getFieldByNotation("f13"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link Bishop} is located on one of its starting positions.
     * An enemy {@link Pawn} is attacking the {@link Bishop}s {@link King}.
     * The {@link Bishop} can not resolve the check of the king, so he can not any move
     * @throws Exception
     */
    @Test
    public void testGetPossibleMovements2() throws Exception {
        Bishop bishop = new Bishop(game.getPlayerList().get(0));
        bishop.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a3"));
        game.getChessboard().getFigures().add(bishop);
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(1), Direction.DIAGONALTOPRIGHT).setPosition((Hexagon) game.getChessboard().getFieldByNotation("b4")));

        List<Hexagon> possibleMovements = bishop.getPossibleMovements(game.getChessboard());
        List<Hexagon> expectedMovements = new ArrayList<>();
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link Bishop} is located on one of its starting positions.
     * An enemy {@link Pawn} is standing on a diagonal field. The {@link Bishop} can beat the {@link Pawn},
     * but not access the fields behind it.
     * @throws Exception
     */
    @Test
    public void testGetPossibleMovements3() throws Exception {
        Bishop bishop = new Bishop(game.getPlayerList().get(0));
        bishop.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a3"));
        game.getChessboard().getFigures().add(bishop);
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(1), Direction.DIAGONALTOPRIGHT).setPosition((Hexagon) game.getChessboard().getFieldByNotation("b5")));

        List<Hexagon> possibleMovements = bishop.getPossibleMovements(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("c4"),
                (Hexagon) game.getChessboard().getFieldByNotation("e5"),
                (Hexagon) game.getChessboard().getFieldByNotation("g6"),
                (Hexagon) game.getChessboard().getFieldByNotation("i7"),
                (Hexagon) game.getChessboard().getFieldByNotation("k8"),
                (Hexagon) game.getChessboard().getFieldByNotation("m9"),
                (Hexagon) game.getChessboard().getFieldByNotation("b2"),
                (Hexagon) game.getChessboard().getFieldByNotation("c1"),
                (Hexagon) game.getChessboard().getFieldByNotation("b5"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link Bishop} is located on one of its starting positions.
     * An enemy {@link Pawn} is standing on a diagonal field. The {@link Bishop} can beat the {@link Pawn},
     * but not access the fields behind it. Similar to {@link #testGetPossibleMovements3()}.
     * @throws Exception
     */
    @Test
    public void testGetPossibleMovements4() throws Exception {
        Bishop bishop = new Bishop(game.getPlayerList().get(0));
        bishop.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a3"));
        game.getChessboard().getFigures().add(bishop);
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(1), Direction.DIAGONALTOPRIGHT).setPosition((Hexagon) game.getChessboard().getFieldByNotation("c7")));

        List<Hexagon> possibleMovements = bishop.getPossibleMovements(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("c4"),
                (Hexagon) game.getChessboard().getFieldByNotation("e5"),
                (Hexagon) game.getChessboard().getFieldByNotation("g6"),
                (Hexagon) game.getChessboard().getFieldByNotation("i7"),
                (Hexagon) game.getChessboard().getFieldByNotation("k8"),
                (Hexagon) game.getChessboard().getFieldByNotation("m9"),
                (Hexagon) game.getChessboard().getFieldByNotation("b2"),
                (Hexagon) game.getChessboard().getFieldByNotation("c1"),
                (Hexagon) game.getChessboard().getFieldByNotation("b5"),
                (Hexagon) game.getChessboard().getFieldByNotation("c7"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link Bishop} is located on one of its starting positions.
     * Check for special movements. {@link Bishop} has none. Checking for empty list.
     * @throws Exception
     */
    @Test
    public void testGetPossibleSpecialMovements() throws Exception {
        Bishop bishop = new Bishop(game.getPlayerList().get(0));
        List<Hexagon> possibleMovements = bishop.getPossibleSpecialMovements(game.getChessboard());
        List<Hexagon> expectedMovements = new ArrayList<>();
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link Bishop} is located on one of its starting positions.
     * No figure is blocking the way. Checking for attackable fields.
     * @throws Exception
     */
    @Test
    public void testGetAttackableFields1() throws Exception {
        Bishop bishop = new Bishop(game.getPlayerList().get(0));
        bishop.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a3"));
        game.getChessboard().getFigures().add(bishop);

        List<Hexagon> possibleMovements = bishop.getAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("c4"),
                (Hexagon) game.getChessboard().getFieldByNotation("e5"),
                (Hexagon) game.getChessboard().getFieldByNotation("g6"),
                (Hexagon) game.getChessboard().getFieldByNotation("i7"),
                (Hexagon) game.getChessboard().getFieldByNotation("k8"),
                (Hexagon) game.getChessboard().getFieldByNotation("m9"),
                (Hexagon) game.getChessboard().getFieldByNotation("b2"),
                (Hexagon) game.getChessboard().getFieldByNotation("c1"),
                (Hexagon) game.getChessboard().getFieldByNotation("b5"),
                (Hexagon) game.getChessboard().getFieldByNotation("c7"),
                (Hexagon) game.getChessboard().getFieldByNotation("d9"),
                (Hexagon) game.getChessboard().getFieldByNotation("e11"),
                (Hexagon) game.getChessboard().getFieldByNotation("f13"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link Bishop} is located on one of its starting positions.
     * One {@link Pawn} (same faction) is blocking a diagonal path.
     * @throws Exception
     */
    @Test
    public void testGetAttackableFields2() throws Exception {
        Bishop bishop = new Bishop(game.getPlayerList().get(0));
        bishop.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a3"));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("b2")));
        game.getChessboard().getFigures().add(bishop);

        List<Hexagon> possibleMovements = bishop.getAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("c4"),
                (Hexagon) game.getChessboard().getFieldByNotation("e5"),
                (Hexagon) game.getChessboard().getFieldByNotation("g6"),
                (Hexagon) game.getChessboard().getFieldByNotation("i7"),
                (Hexagon) game.getChessboard().getFieldByNotation("k8"),
                (Hexagon) game.getChessboard().getFieldByNotation("m9"),
                (Hexagon) game.getChessboard().getFieldByNotation("b5"),
                (Hexagon) game.getChessboard().getFieldByNotation("c7"),
                (Hexagon) game.getChessboard().getFieldByNotation("d9"),
                (Hexagon) game.getChessboard().getFieldByNotation("e11"),
                (Hexagon) game.getChessboard().getFieldByNotation("f13"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link Bishop} is located on one of its starting positions.
     * Two {@link Pawn}s a blocking a diagonal path by occupying bordering fields.
     * @throws Exception
     */
    @Test
    public void testGetAttackableFields3() throws Exception {
        Bishop bishop = new Bishop(game.getPlayerList().get(0));
        bishop.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a3"));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("b3")));
        game.getChessboard().getFigures().add(new Knight(game.getPlayerList().get(0)).setPosition((Hexagon) game.getChessboard().getFieldByNotation("a2")));
        game.getChessboard().getFigures().add(bishop);

        List<Hexagon> possibleMovements = bishop.getAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("c4"),
                (Hexagon) game.getChessboard().getFieldByNotation("e5"),
                (Hexagon) game.getChessboard().getFieldByNotation("g6"),
                (Hexagon) game.getChessboard().getFieldByNotation("i7"),
                (Hexagon) game.getChessboard().getFieldByNotation("k8"),
                (Hexagon) game.getChessboard().getFieldByNotation("m9"),
                (Hexagon) game.getChessboard().getFieldByNotation("b5"),
                (Hexagon) game.getChessboard().getFieldByNotation("c7"),
                (Hexagon) game.getChessboard().getFieldByNotation("d9"),
                (Hexagon) game.getChessboard().getFieldByNotation("e11"),
                (Hexagon) game.getChessboard().getFieldByNotation("f13"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link Bishop} is located on one of its starting positions.
     * All diagonal paths are free.
     * @throws Exception
     */
    @Test
    public void testGetAttackableFields4() throws Exception {
        Bishop bishop = new Bishop(game.getPlayerList().get(0));
        bishop.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a3"));
        game.getChessboard().getFigures().add(new Knight(game.getPlayerList().get(0)).setPosition((Hexagon) game.getChessboard().getFieldByNotation("a2")));
        game.getChessboard().getFigures().add(bishop);

        List<Hexagon> possibleMovements = bishop.getAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("c4"),
                (Hexagon) game.getChessboard().getFieldByNotation("e5"),
                (Hexagon) game.getChessboard().getFieldByNotation("g6"),
                (Hexagon) game.getChessboard().getFieldByNotation("i7"),
                (Hexagon) game.getChessboard().getFieldByNotation("k8"),
                (Hexagon) game.getChessboard().getFieldByNotation("m9"),
                (Hexagon) game.getChessboard().getFieldByNotation("b5"),
                (Hexagon) game.getChessboard().getFieldByNotation("c7"),
                (Hexagon) game.getChessboard().getFieldByNotation("d9"),
                (Hexagon) game.getChessboard().getFieldByNotation("e11"),
                (Hexagon) game.getChessboard().getFieldByNotation("f13"),
                (Hexagon) game.getChessboard().getFieldByNotation("b2"),
                (Hexagon) game.getChessboard().getFieldByNotation("c1"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link Bishop} is located on one of its starting positions.
     * Two diagonal paths are occupied
     * @throws Exception
     */
    @Test
    public void testGetAttackableFields5() throws Exception {
        Bishop bishop = new Bishop(game.getPlayerList().get(0));
        bishop.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a3"));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("b4")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("b3")));
        game.getChessboard().getFigures().add(new Knight(game.getPlayerList().get(0)).setPosition((Hexagon) game.getChessboard().getFieldByNotation("a2")));
        game.getChessboard().getFigures().add(bishop);

        List<Hexagon> possibleMovements = bishop.getAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("b5"),
                (Hexagon) game.getChessboard().getFieldByNotation("c7"),
                (Hexagon) game.getChessboard().getFieldByNotation("d9"),
                (Hexagon) game.getChessboard().getFieldByNotation("e11"),
                (Hexagon) game.getChessboard().getFieldByNotation("f13"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link Bishop} is located on one of its starting positions.
     * All diagonal paths are occupied
     * @throws Exception
     */
    @Test
    public void testGetAttackableFields6() throws Exception {
        Bishop bishop = new Bishop(game.getPlayerList().get(0));
        bishop.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a3"));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("b4")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("b3")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("b5")));
        game.getChessboard().getFigures().add(new Knight(game.getPlayerList().get(0)).setPosition((Hexagon) game.getChessboard().getFieldByNotation("a2")));
        game.getChessboard().getFigures().add(bishop);

        List<Hexagon> possibleMovements = bishop.getAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = new ArrayList<>();
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link Bishop} is located on one of its starting positions.
     * Similar to {@link #testGetAttackableFields1()}
     * @throws Exception
     */
    @Test
    public void testGetHypotheticalAttackableFields1() throws Exception {
        Bishop bishop = new Bishop(game.getPlayerList().get(0));
        bishop.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a3"));
        game.getChessboard().getFigures().add(bishop);

        List<Hexagon> possibleMovements = bishop.getHypotheticalAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("c4"),
                (Hexagon) game.getChessboard().getFieldByNotation("e5"),
                (Hexagon) game.getChessboard().getFieldByNotation("g6"),
                (Hexagon) game.getChessboard().getFieldByNotation("i7"),
                (Hexagon) game.getChessboard().getFieldByNotation("k8"),
                (Hexagon) game.getChessboard().getFieldByNotation("m9"),
                (Hexagon) game.getChessboard().getFieldByNotation("b2"),
                (Hexagon) game.getChessboard().getFieldByNotation("c1"),
                (Hexagon) game.getChessboard().getFieldByNotation("b5"),
                (Hexagon) game.getChessboard().getFieldByNotation("c7"),
                (Hexagon) game.getChessboard().getFieldByNotation("d9"),
                (Hexagon) game.getChessboard().getFieldByNotation("e11"),
                (Hexagon) game.getChessboard().getFieldByNotation("f13"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link Bishop} is located on one of its starting positions.
     * Similar to {@link #testGetAttackableFields2()}
     * @throws Exception
     */
    @Test
    public void testGetHypotheticalAttackableFields2() throws Exception {
        Bishop bishop = new Bishop(game.getPlayerList().get(0));
        bishop.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a3"));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("b2")));
        game.getChessboard().getFigures().add(bishop);

        List<Hexagon> possibleMovements = bishop.getHypotheticalAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("c4"),
                (Hexagon) game.getChessboard().getFieldByNotation("e5"),
                (Hexagon) game.getChessboard().getFieldByNotation("g6"),
                (Hexagon) game.getChessboard().getFieldByNotation("i7"),
                (Hexagon) game.getChessboard().getFieldByNotation("k8"),
                (Hexagon) game.getChessboard().getFieldByNotation("m9"),
                (Hexagon) game.getChessboard().getFieldByNotation("b5"),
                (Hexagon) game.getChessboard().getFieldByNotation("c7"),
                (Hexagon) game.getChessboard().getFieldByNotation("d9"),
                (Hexagon) game.getChessboard().getFieldByNotation("e11"),
                (Hexagon) game.getChessboard().getFieldByNotation("f13"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link Bishop} is located on one of its starting positions.
     * Similar to {@link #testGetAttackableFields3()}
     * @throws Exception
     */
    @Test
    public void testGetHypotheticalAttackableFields3() throws Exception {
        Bishop bishop = new Bishop(game.getPlayerList().get(0));
        bishop.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a3"));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("b3")));
        game.getChessboard().getFigures().add(new Knight(game.getPlayerList().get(0)).setPosition((Hexagon) game.getChessboard().getFieldByNotation("a2")));
        game.getChessboard().getFigures().add(bishop);

        List<Hexagon> possibleMovements = bishop.getHypotheticalAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("c4"),
                (Hexagon) game.getChessboard().getFieldByNotation("e5"),
                (Hexagon) game.getChessboard().getFieldByNotation("g6"),
                (Hexagon) game.getChessboard().getFieldByNotation("i7"),
                (Hexagon) game.getChessboard().getFieldByNotation("k8"),
                (Hexagon) game.getChessboard().getFieldByNotation("m9"),
                (Hexagon) game.getChessboard().getFieldByNotation("b5"),
                (Hexagon) game.getChessboard().getFieldByNotation("c7"),
                (Hexagon) game.getChessboard().getFieldByNotation("d9"),
                (Hexagon) game.getChessboard().getFieldByNotation("e11"),
                (Hexagon) game.getChessboard().getFieldByNotation("f13"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link Bishop} is located on one of its starting positions.
     * Similar to {@link #testGetAttackableFields4()}
     * @throws Exception
     */
    @Test
    public void testGetHypotheticalAttackableFields4() throws Exception {
        Bishop bishop = new Bishop(game.getPlayerList().get(0));
        bishop.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a3"));
        game.getChessboard().getFigures().add(new Knight(game.getPlayerList().get(0)).setPosition((Hexagon) game.getChessboard().getFieldByNotation("a2")));
        game.getChessboard().getFigures().add(bishop);

        List<Hexagon> possibleMovements = bishop.getHypotheticalAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("c4"),
                (Hexagon) game.getChessboard().getFieldByNotation("e5"),
                (Hexagon) game.getChessboard().getFieldByNotation("g6"),
                (Hexagon) game.getChessboard().getFieldByNotation("i7"),
                (Hexagon) game.getChessboard().getFieldByNotation("k8"),
                (Hexagon) game.getChessboard().getFieldByNotation("m9"),
                (Hexagon) game.getChessboard().getFieldByNotation("b5"),
                (Hexagon) game.getChessboard().getFieldByNotation("c7"),
                (Hexagon) game.getChessboard().getFieldByNotation("d9"),
                (Hexagon) game.getChessboard().getFieldByNotation("e11"),
                (Hexagon) game.getChessboard().getFieldByNotation("f13"),
                (Hexagon) game.getChessboard().getFieldByNotation("b2"),
                (Hexagon) game.getChessboard().getFieldByNotation("c1"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link Bishop} is located on one of its starting positions.
     * Similar to {@link #testGetAttackableFields5()}
     * @throws Exception
     */
    @Test
    public void testGetHypotheticalAttackableFields5() throws Exception {
        Bishop bishop = new Bishop(game.getPlayerList().get(0));
        bishop.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a3"));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("b4")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("b3")));
        game.getChessboard().getFigures().add(new Knight(game.getPlayerList().get(0)).setPosition((Hexagon) game.getChessboard().getFieldByNotation("a2")));
        game.getChessboard().getFigures().add(bishop);

        List<Hexagon> possibleMovements = bishop.getHypotheticalAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("b5"),
                (Hexagon) game.getChessboard().getFieldByNotation("c7"),
                (Hexagon) game.getChessboard().getFieldByNotation("d9"),
                (Hexagon) game.getChessboard().getFieldByNotation("e11"),
                (Hexagon) game.getChessboard().getFieldByNotation("f13"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link Bishop} is located on one of its starting positions.
     * Similar to {@link #testGetAttackableFields6()}
     * @throws Exception
     */
    @Test
    public void testGetHypotheticalAttackableFields6() throws Exception {
        Bishop bishop = new Bishop(game.getPlayerList().get(0));
        bishop.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a3"));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("b4")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("b3")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("b5")));
        game.getChessboard().getFigures().add(new Knight(game.getPlayerList().get(0)).setPosition((Hexagon) game.getChessboard().getFieldByNotation("a2")));
        game.getChessboard().getFigures().add(bishop);

        List<Hexagon> possibleMovements = bishop.getHypotheticalAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = new ArrayList<>();
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }

    /**
     * The {@link Bishop} is located on one of its starting positions.
     * @throws Exception
     */
    @Test
    public void testGetHypotheticalAttackableFields7() throws Exception {
        Bishop bishop = new Bishop(game.getPlayerList().get(0));
        bishop.setPosition((Hexagon) game.getChessboard().getFieldByNotation("a3"));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("b1")));
        game.getChessboard().getFigures().add(new Pawn(game.getPlayerList().get(0), Direction.DIAGONALBOTTOM).setPosition((Hexagon) game.getChessboard().getFieldByNotation("c2")));
        game.getChessboard().getFigures().add(bishop);

        List<Hexagon> possibleMovements = bishop.getHypotheticalAttackableFields(game.getChessboard());
        List<Hexagon> expectedMovements = Arrays.asList(
                (Hexagon) game.getChessboard().getFieldByNotation("b2"),
                (Hexagon) game.getChessboard().getFieldByNotation("c4"),
                (Hexagon) game.getChessboard().getFieldByNotation("e5"),
                (Hexagon) game.getChessboard().getFieldByNotation("g6"),
                (Hexagon) game.getChessboard().getFieldByNotation("i7"),
                (Hexagon) game.getChessboard().getFieldByNotation("k8"),
                (Hexagon) game.getChessboard().getFieldByNotation("m9"),
                (Hexagon) game.getChessboard().getFieldByNotation("b5"),
                (Hexagon) game.getChessboard().getFieldByNotation("c7"),
                (Hexagon) game.getChessboard().getFieldByNotation("d9"),
                (Hexagon) game.getChessboard().getFieldByNotation("e11"),
                (Hexagon) game.getChessboard().getFieldByNotation("f13"));
        ListAssert.assertEquals(expectedMovements, possibleMovements);
    }
}