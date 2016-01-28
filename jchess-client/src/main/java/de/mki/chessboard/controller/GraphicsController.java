package de.mki.chessboard.controller;

import jchess.JChessApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.net.URL;

/**
 * @author Kevin Lamshoeft
 *         Controller to draw images related to the chessboard (i.e. figures, the board itself..)
 */
public class GraphicsController {

    private static final Logger logger = LoggerFactory.getLogger(GraphicsController.class);

    /** load image by a given name with extension
     * @param name String of image to load for ex. "chessboard.jpg"
     * @return Image
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
