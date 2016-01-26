/*
#    This program is free software: you can redistribute it and/or modify
#    it under the terms of the GNU General Public License as published by
#    the Free Software Foundation, either version 3 of the License, or
#    (at your option) any later version.
#
#    This program is distributed in the hope that it will be useful,
#    but WITHOUT ANY WARRANTY; without even the implied warranty of
#    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#    GNU General Public License for more details.
#
#    You should have received a copy of the GNU General Public License
#    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * Authors:
 * Mateusz Sławomir Lach ( matlak, msl )
 * Damian Marciniak
 */
package jchess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.net.URL;

/**
 * Class representing the game interface which is seen by a player and
 * where are located available for player options, current games and where
 * can he start a new game
 */
public class GUI {

    static Logger logger = LoggerFactory.getLogger(GUI.class);
    public Game game;

    /**
     * Default constructor for the GUI
     */
    public GUI() {
        this.game = new Game();
    }

    /**
     * Method load image by a given name with extension
     * @param name string of image to load for ex. "chessboard.jpg"
     * @return image or null if cannot load
     */
    static Image loadImage(String name) {
        Image img = null;
        URL url = null;
        Toolkit tk = Toolkit.getDefaultToolkit();
        try {
            String imageLink = "theme/default/images/" + name;
            url = JChessApp.class.getResource(imageLink);
            img = tk.getImage(url);
        } catch (Exception e) {
            logger.error("Error loading image", e);
        }
        return img;
    }
}