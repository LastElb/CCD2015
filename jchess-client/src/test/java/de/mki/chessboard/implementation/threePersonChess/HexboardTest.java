package de.mki.chessboard.implementation.threePersonChess;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class HexboardTest {

    Hexboard hexboard;

    @Before
    /**
     * set up an empty board
     */
    public void setUp() {
        hexboard = new smallHexboard();
        hexboard.setupBoard(new ArrayList<>());
    }

    @Test
    /**
     *  internal (axial) coordinates should match chess notation
     */
    public void testGetFieldByNotation() throws Exception {
        Hexagon hexagon = hexboard.getFieldByNotation("I8");
        String notation = hexagon.getNotation();
        int q = hexagon.getQ();
        int r = hexagon.getR();

        int[] expected = {-1, 8};
        int[] actual = {q, r};
        assertArrayEquals(expected, actual);
        assertEquals(notation, "I8");
    }
}