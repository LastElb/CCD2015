package de.mki.chessboard.implementation.threePersonChess;

import java.awt.*;
import java.util.List;

import static de.mki.chessboard.controller.GraphicsController.loadImage;

/**
 * @author Kevin Lamshoeft
 *         Class for a (rather small) 600 x 600 pixels hexagonal chessboard
 */
public class smallHexboard extends Hexboard {

    /**
     * default constructor for 600px x 600px hexagonal chessboard
     *
     */
    public smallHexboard() {
        this.setWidth(600);
        this.setHeight(600);
        this.setHexSize(new Point(26, 26));
        this.setOrigin(new Point(138, 65));
        this.setLayout(new Layout(Layout.pointy, 26, 26.5, new Point(138, 65)));
        this.setImage(loadImage("chessboard600x600.png"));
        this.setPixelCorrection(new Point(22, 27));
        this.setGetPixelCorrectionMoves(new Point(11, 17));
    }

}
