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
#    along with this program.  If not, see <http://www.gnu.org/licenses/>..
 */

/*
 * Authors:
 * Mateusz Sławomir Lach ( matlak, msl )
 * Damian Marciniak
 */
package jchess;

import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;

/**
 * Crazy swing stuff to add Tabs to GUI.
 */
public class JChessTabbedPane extends JTabbedPane implements MouseListener, ImageObserver {

    transient private TabbedPaneIcon closeIcon;
    transient private Image addIcon = null;
    transient private Image clickedAddIcon = null;
    transient private Image unclickedAddIcon = null;
    private Rectangle addIconRect = null;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(JChessTabbedPane.class);

    /**
     * Initialize Tabbed Pane
     */
    JChessTabbedPane() {
        super();
        this.closeIcon = new TabbedPaneIcon(this.closeIcon);
        this.unclickedAddIcon = GUI.loadImage("add-tab-icon.png");
        this.clickedAddIcon = GUI.loadImage("clicked-add-tab-icon.png");
        this.addIcon = this.unclickedAddIcon;
        this.setDoubleBuffered(true);
        super.addMouseListener(this);
    }

    /**
     * Method to add a Tab to pane
     *
     * @param title     Title of the tab
     * @param component
     */
    @Override
    public void addTab(String title, Component component) {
        this.addTab(title, component, null);
    }

    /**
     * Add a tab with specified icon to pane
     *
     * @param title     Title of the tab
     * @param component
     * @param closeIcon Icon for closing the tab
     */
    public void addTab(String title, Component component, Icon closeIcon) {
        super.addTab(title, new TabbedPaneIcon(closeIcon), component);
        logger.trace("Present number of tabs: {}", this.getTabCount());
        this.updateAddIconRect();
    }

    /**
     * Method triggered when mouse is released: Not implemented
     *
     * @param e MouseEvent
     */
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * Method triggered when mouse is pressed: Not implemented
     *
     * @param e MouseEvent
     */
    public void mousePressed(MouseEvent e) {
    }

    /**
     * Add a new Game Window and show it
     */
    private void showNewGameWindow() {
        JChessView jChessView = JChessApp.getjChessView();
        if (jChessView.newGameFrame == null) {
            jChessView.newGameFrame = new NewGameWindow();
        }
        JChessApp.getApplication().show(jChessView.newGameFrame);
    }

    /**
     * Click-Handler to add / remove tabs
     *
     * @param e MouseEvent
     */
    public void mouseClicked(MouseEvent e) {
        Rectangle rect;
        int tabNumber = getUI().tabForCoordinate(this, e.getX(), e.getY());
        if (tabNumber >= 0) {
            rect = ((TabbedPaneIcon) getIconAt(tabNumber)).getBounds();
            if (rect.contains(e.getX(), e.getY())) {
                logger.trace("Removing tabe with tabNumber: {}", tabNumber);
                this.removeTabAt(tabNumber);//remove tab
                this.updateAddIconRect();
            }
            if (this.getTabCount() == 0) {
                this.showNewGameWindow();
            }
        } else if (this.addIconRect != null && this.addIconRect.contains(e.getX(), e.getY())) {
            logger.trace("New Game started by + button");
            this.showNewGameWindow();
        }
    }

    /**
     * Method triggered when mouse enters: Not implemented
     *
     * @param e MouseEvent
     */
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Method triggered when mouse exits: Not implemented
     *
     * @param e MouseEvent
     */
    public void mouseExited(MouseEvent e) {
    }

    /**
     * Update the addIcon Rectangle
     */
    private void updateAddIconRect() {
        if (this.getTabCount() > 0) {
            Rectangle rect = this.getBoundsAt(this.getTabCount() - 1);
            this.addIconRect = new Rectangle(rect.x + rect.width + 5, rect.y, this.addIcon.getWidth(this), this.addIcon.getHeight(this));
        } else {
            this.addIconRect = null;
        }
    }

    /**
     * Get AddIcon Rectangle
     *
     * @return Rectangle
     */
    private Rectangle getAddIconRect() {
        return this.addIconRect;
    }

    /**
     * Update icon on tab
     *
     * @param img       Image diaplayed
     * @param infoflags
     * @param x         x-position
     * @param y         y-position
     * @param width     width of the image
     * @param height    height of the image
     * @return true
     */
    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        super.imageUpdate(img, infoflags, x, y, width, height);
        this.updateAddIconRect();
        return true;
    }

    /**
     * {@inheritDoc}
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Rectangle rect = this.getAddIconRect();
        if (rect != null) {
            g.drawImage(this.addIcon, rect.x, rect.y, null);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param g
     */
    @Override
    public void update(Graphics g) {
        this.repaint();
    }
}

/**
 * Class for implementing the icons
 */
class TabbedPaneIcon implements Icon {

    private int x_pos;
    private int y_pos;
    private int width;
    private int height;
    private Icon fileIcon;

    /**
     * Create Icon
     *
     * @param fileIcon
     */
    public TabbedPaneIcon(Icon fileIcon) {
        this.fileIcon = fileIcon;
        width = 16;
        height = 16;
    }

    /**
     * {@inheritDoc}
     *
     * @param c
     * @param g
     * @param x
     * @param y
     */
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        this.x_pos = x;
        this.y_pos = y;

        Color col = g.getColor();

        g.setColor(Color.black);
        int y_p = y + 2;
        g.drawLine(x + 3, y_p + 3, x + 10, y_p + 10);
        g.drawLine(x + 3, y_p + 4, x + 9, y_p + 10);
        g.drawLine(x + 4, y_p + 3, x + 10, y_p + 9);
        g.drawLine(x + 10, y_p + 3, x + 3, y_p + 10);
        g.drawLine(x + 10, y_p + 4, x + 4, y_p + 10);
        g.drawLine(x + 9, y_p + 3, x + 3, y_p + 9);
        g.setColor(col);
        if (fileIcon != null) {
            fileIcon.paintIcon(c, g, x + width, y_p);
        }
    }

    /**
     * Returns the width of the icon, if no file is set 0.
     *
     * @return Width of the icon
     */
    public int getIconWidth() {
        return width + (fileIcon != null ? fileIcon.getIconWidth() : 0);
    }

    /**
     * Returns the height of the icon
     *
     * @return icon height
     */
    public int getIconHeight() {
        return height;
    }

    /**
     * Get the Rectangle containing the icon
     *
     * @return Rectangle
     */
    public Rectangle getBounds() {
        return new Rectangle(x_pos, y_pos, width, height);
    }
}