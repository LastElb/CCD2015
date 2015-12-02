package de.mki.chessboard.controller;

import jchess.JChessApp;

import java.awt.*;
import java.net.URL;

public class GraphicsController {

    /** load image by a given name with extension
    * @name     : string of image to load for ex. "chessboard.jpg"
    * @returns  : image or null if cannot load
    * */
    public static Image loadImage(String name) {
        Image img = null;
        URL url = null;
        Toolkit tk = Toolkit.getDefaultToolkit();
        try {
            String imageLink = "theme/default/images/" + name;
            url = JChessApp.class.getResource(imageLink);
            img = tk.getImage(url);
        } catch (Exception e) {
        }
        return img;
    }/*--endOf-loadImage--*/
}
