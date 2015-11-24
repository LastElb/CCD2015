package jchess.de.mki.jchess.hexboard;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 *
 */
public class Hexboard extends JPanel {

    public int width = 1482;
    public int height = 1314;
    public int hexWidth = 115;
    public int hexHeight = 132;
    public int hexSize = hexHeight / 2;
    public Point origin = new Point(0,0); //TODO: find the actual origin point in the gui

    Layout layout = new Layout(Layout.pointy, new Point(hexSize, hexSize), origin);




}
