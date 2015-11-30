package de.mki.chessboard.implementation.threePersonChess;

import de.mki.chessboard.model.Chessboard;

import java.awt.*;
import java.util.ArrayList;

public class Hexboard extends Chessboard {

    /* default values for 600px x 600px hexagonal chessboard */
    int width;
    int height;
    int hexWidth;
    int hexHeight;
    int hexSize;  //TODO: hexSize has to be a Point.
    Point origin; //TODO: find the actual origin point in the gui
    Layout layout;
    public ArrayList<Hexagon> fields = new ArrayList<Hexagon>();

    public Hexboard() {

        this.generateFields();
        this.drawBoard();

    }


    public int getHexWidth() {
        return hexWidth;
    }

    public int getHexHeight() {
        return hexHeight;
    }

    public int getHexSize() {
        return hexSize;
    }

    public Point getOrigin() {
        return origin;
    }

    public Layout getChessboardLayout() {
        return layout;
    }

    @Override
    public void generateFields() {
        /* Generate Fields */
        // generate right half from x=0 to x=7
        for (int x=0, maxY=12; x<=7; x++, maxY-- ) {
            for (int y=0; y<=maxY; y++) {
                this.fields.add(new Hexagon(x, y));
            }
        }
        //generate left half from x=-1 to x=-5
        for(int x=-1, startY=1; x>=-5; x--,startY++ ) {
            for(int y=startY; y<=12; y++)
                fields.add(new Hexagon(x,y));
        }
    }

    @Override
    public void drawBoard() {
        //Todo: draw the Board.
    }

    @Override
    public Hexagon getClickedField(int x, int y) {
        System.out.println("You clicked on x=" + x + " and y=" + y);
        Hexagon clickedHexagon = this.layout.pixelToHexagon(new Point(x, y));
        System.out.println("You clicked on Hexagon: q=" + clickedHexagon.q + " and r=" + clickedHexagon.r);
        return clickedHexagon;
    }
}
