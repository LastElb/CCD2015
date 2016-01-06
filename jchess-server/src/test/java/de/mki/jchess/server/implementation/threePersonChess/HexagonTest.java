package de.mki.jchess.server.implementation.threePersonChess;

import de.mki.jchess.server.implementation.threePersonChess.figures.FigureTest;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests neighbour function of {@link Hexagon}
 * Created by Igor on 04.01.2016.
 */
public class HexagonTest extends FigureTest {

    /**
     * Test positive neighbours of a {@link Hexagon}.
     * @throws Exception
     */
    @Test
    public void testIsHexagonNeighbourPositive() throws Exception {
        Hexagon hexagon = (Hexagon) getGame().getChessboard().getFieldByNotation("a1");
        assertTrue(hexagon.isHexagonNeighbour((Hexagon) getGame().getChessboard().getFieldByNotation("a2")));
        assertTrue(hexagon.isHexagonNeighbour((Hexagon) getGame().getChessboard().getFieldByNotation("b1")));
    }

    /**
     * Test negative neighbours of a {@link Hexagon}.
     * @throws Exception
     */
    @Test
    public void testIsHexagonNeighbourNegative() throws Exception {
        Hexagon hexagon = (Hexagon) getGame().getChessboard().getFieldByNotation("a1");
        assertFalse(hexagon.isHexagonNeighbour((Hexagon) getGame().getChessboard().getFieldByNotation("m8")));
        assertFalse(hexagon.isHexagonNeighbour((Hexagon) getGame().getChessboard().getFieldByNotation("g12")));
    }

    /**
     * Generates a {@link Hexagon} with out of bounds values
     */
    @Test
    public void testOutOfBoundsHexagon() throws Exception {
        Hexagon hexagon = new Hexagon(13, 13);
        hexagon.getNotation();
    }
}