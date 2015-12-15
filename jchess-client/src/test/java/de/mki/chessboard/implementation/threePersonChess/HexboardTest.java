package de.mki.chessboard.implementation.threePersonChess;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class HexboardTest {

    Hexboard hexboard;

    @Before
    public void setUp() {
        hexboard = new Hexboard(new ArrayList());
    }

    @Test
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