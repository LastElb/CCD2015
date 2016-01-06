package de.mki.chessboard.controller;

import jchess.JChessApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.net.URL;

public class GraphicsController {

    private static final Logger logger = LoggerFactory.getLogger(GraphicsController.class);

    /** load image by a given name with extension
    * @param name     : string of image to load for ex. "chessboard.jpg"
    * @return  : image or null if cannot load
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
            logger.error("", e);
        }
        return img;
    }/*--endOf-loadImage--*/
}
