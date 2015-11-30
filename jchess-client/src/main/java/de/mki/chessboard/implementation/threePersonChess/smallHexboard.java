package de.mki.chessboard.implementation.threePersonChess;

import java.awt.*;

public class smallHexboard extends Hexboard {

    /**
     * default values for 600px x 600px hexagonal chessboard
     */

    int width = 600;
    int height = 600;
    int hexWidth = 46;
    int hexHeight = 54;
    int hexSize = hexHeight / 2;  //TODO: hexSize has to be a Point.
    Point origin = new Point(0, 0); //TODO: find the actual origin point in the gui
    Layout layout = new Layout(Layout.pointy, new Point(hexSize, hexSize), origin);
}
