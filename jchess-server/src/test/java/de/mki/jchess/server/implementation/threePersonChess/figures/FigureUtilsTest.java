package de.mki.jchess.server.implementation.threePersonChess.figures;

import de.mki.jchess.server.Application;
import de.mki.jchess.server.implementation.threePersonChess.Direction;
import de.mki.jchess.server.implementation.threePersonChess.Hexagon;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

/**
 * Created by Igor on 29.12.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class FigureUtilsTest extends FigureTest {
    @Test
    public void testFindPathWhiteLongCastling() throws Exception {
        Direction direction = FigureUtils.findDirection(
                (Hexagon) getGame().getChessboard().getFieldByNotation("a5"),
                (Hexagon) getGame().getChessboard().getFieldByNotation("a1")
        );
        assertEquals(direction, Direction.LEFT);
    }

    @Test
    public void testFindPathWhiteShortCastling() throws Exception {
        Direction direction = FigureUtils.findDirection(
                (Hexagon) getGame().getChessboard().getFieldByNotation("a5"),
                (Hexagon) getGame().getChessboard().getFieldByNotation("a8")
        );
        assertEquals(direction, Direction.RIGHT);
    }

    @Test
    public void testFindPathGreyLongCastling() throws Exception {
        Direction direction = FigureUtils.findDirection(
                (Hexagon) getGame().getChessboard().getFieldByNotation("j13"),
                (Hexagon) getGame().getChessboard().getFieldByNotation("f13")
        );
        assertEquals(direction, Direction.TOPRIGHT);
    }

    @Test
    public void testFindPathGreyShortCastling() throws Exception {
        Direction direction = FigureUtils.findDirection(
                (Hexagon) getGame().getChessboard().getFieldByNotation("j13"),
                (Hexagon) getGame().getChessboard().getFieldByNotation("m13")
        );
        assertEquals(direction, Direction.BOTTOMLEFT);
    }

    @Test
    public void testFindPathBlackLongCastling() throws Exception {
        Direction direction = FigureUtils.findDirection(
                (Hexagon) getGame().getChessboard().getFieldByNotation("i4"),
                (Hexagon) getGame().getChessboard().getFieldByNotation("m8")
        );
        assertEquals(direction, Direction.BOTTOMRIGHT);
    }

    @Test
    public void testFindPathBlackShortCastling() throws Exception {
        Direction direction = FigureUtils.findDirection(
                (Hexagon) getGame().getChessboard().getFieldByNotation("i4"),
                (Hexagon) getGame().getChessboard().getFieldByNotation("f1")
        );
        assertEquals(direction, Direction.TOPLEFT);
    }
}