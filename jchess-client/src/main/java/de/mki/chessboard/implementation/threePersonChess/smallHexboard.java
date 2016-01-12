package de.mki.chessboard.implementation.threePersonChess;

import java.awt.*;
import java.util.List;

import static de.mki.chessboard.controller.GraphicsController.loadImage;

public class smallHexboard extends Hexboard {

    /**
     * default constructor for 600px x 600px hexagonal chessboard
     *
     */
    public smallHexboard() {
        this.setWidth(600);
        this.setHeight(600);
        this.setHexWidth(46);
        this.setHexHeight(53);
        int vertDistance = 39; //52*3/4;
        this.setHexSize(new Point(26, 26));  //TODO: find fitting size.
        this.setOrigin(new Point(138, 65));     //TODO: find the actual origin point in the gui.
        this.setLayout(new Layout(Layout.pointy, 26, 26.5, new Point(138, 65)));
        this.setImage(loadImage("chessboard600x600.png"));
    }
}
