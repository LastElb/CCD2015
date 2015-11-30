package de.mki.chessboard.implementation.threePersonChess;

import jchess.GUI;

import java.awt.*;

public class smallHexboard extends Hexboard {

    /**
     * default values for 600px x 600px hexagonal chessboard
     */

    public smallHexboard() {
        this.setWidth(600);
        this.setHeight(600);
        this.setHexHeight(46);
        this.setHexSize(new Point(27, 27));  //TODO: find fitting size.
        this.setOrigin(new Point(0, 0));     //TODO: find the actual origin point in the gui
        this.setLayout(new Layout(Layout.pointy, getHexSize(), getOrigin()));
        //TODO: set image
    }

}
