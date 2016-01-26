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
 * Malte Müns
 */
package jchess;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.mki.chessboard.implementation.threePersonChess.Hexagon;
import de.mki.chessboard.implementation.threePersonChess.smallHexboard;
import de.mki.jchess.commons.Client;
import de.mki.jchess.commons.websocket.PlayerChangedEvent;
import de.mki.jchess.commons.websocket.PlayerDefeatedEvent;
import jchess.client.ServerApi;

import jchess.client.WebSocketClient;
import jchess.client.models.Chessboard;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;

import jchess.client.models.Figure;


import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Optional;

import java.util.*;
import java.util.List;

/**
 * Class responsible for starting a new game.
 * This class is also responsible for binding to APIs (Server, WebSocket) and to display the chessboard.
 */
public class Game extends JPanel implements MouseListener, ComponentListener {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Game.class);

    private transient ServerApi serverApi;
    private transient WebSocketClient webSocketClient;
    private transient Optional<jchess.client.models.Game> gameModel;
    private transient Optional<Client> clientModel;
    private transient smallHexboard chessboard;
    private transient Hexagon lastClickedField;
    private transient ArrayList<String> highlightedFields;
    private transient String gameID;

    /**
     * Default constructor for game
     */
    Game() {

        // initialize lastClickedField as A0
        lastClickedField = new Hexagon(0, -1);
        highlightedFields = new ArrayList<>();

        // initialize Hexboard
        initializeHexboard();

        this.addMouseListener(this);
        this.addComponentListener(this);
    }


    /**
     * Method to Start new game
     */
    public void newGame() {

        JChessView jChessView = JChessApp.getjChessView();
        Game activeGame = jChessView.getActiveTabGame();

        if (activeGame != null && jChessView.getNumberOfOpenedTabs() == 0) {
            activeGame.repaint();
        }

        this.repaint();
        this.updateUI();
    }

    /**
     * Method to end game
     *
     * @param message what to show player(s) at end of the game (for example "draw", "black wins" etc.)
     */
    public void endGame(String message) {
        // block chessboard
        chessboard.block();

        logger.trace("endGame method was called with message: {}", message);
        JOptionPane.showMessageDialog(null, message);
    }

    /**
     * Initialize the Chessboard in Hexagon-style
     */
    public void initializeHexboard() {
        this.setLayout(null);
        this.setDoubleBuffered(false);


        try {
            chessboard = new smallHexboard();

            this.addMouseListener(this);
            this.add(chessboard);
            this.repaint();
            this.updateUI();
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    /**
     * Method triggered when mouse is released
     *
     * @param arg0 MouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent arg0) {
        // Not implemented yet
    }

    /**
     * Method handling click-Events on the Chessboard
     *
     * @param event
     */
    @Override
    public void mousePressed(MouseEvent event) {
        if (event.getButton() == MouseEvent.BUTTON1) //left button
        {
            // its your turn
            if (!chessboard.isBlocked()) {
                // get X position of mouse
                int x = event.getX();
                // get Y position of mouse
                int y = event.getY();

                // Get clicked field form chessboard by passing coordinates
                String clickedField = chessboard.getClickedField(x, y);

                logger.trace("Click on x={}, y={} which is field {}",
                        x,
                        y,
                        clickedField
                );

                // try move
                // clicked field is already marked
                if (highlightedFields.contains(clickedField)) {
                    try {
                        // send request for movement to the server
                        serverApi.performMovement(getGameID(), lastClickedField.getNotation(), clickedField, clientModel.get().getId());
                        // reset highlighted fields
                        chessboard.clearHighlightedFields();
                        highlightedFields.clear();
                    } catch (Exception e) {
                        logger.error("", e);
                    }
                } else {
                    // mark possible moves
                    List<Figure> figures = chessboard.getFigures();

                    // get figure on the clicked field
                    for (Figure figure : figures) {
                        if (figure.getPositionObject().getNotation().equals(clickedField)) {
                            try {
                                // request possible moves from server
                                highlightedFields = serverApi.getPossibleMoves(getGameID(), clickedField);
                                // highlight fields
                                chessboard.highlightFieldsByNotation(highlightedFields);
                                break;
                            } catch (Exception e) {
                                logger.error("", e);
                            }

                        }
                    }
                }
                // repaint gui
                this.repaint();
                this.updateUI();

                // save last clicked field
                lastClickedField = chessboard.getFieldByNotation(clickedField);

                /* Save position for Debugging */
                logger.trace("The center of {} is at ({},{})",
                        clickedField,
                        lastClickedField.getX(),
                        lastClickedField.getY()
                );

            } else {
                logger.trace("Chessboard is blocked");
            }
        }
    }

    /**
     * Method triggered when mouse is released:
     *
     * @param arg0 MouseEvent
     */
    @Override
    public void mouseReleased(MouseEvent arg0) {
        // Not implemented
    }

    /**
     * Method triggered when mouse enters
     *
     * @param arg0 MouseEvent
     */
    @Override
    public void mouseEntered(MouseEvent arg0) {
        // Not implemented
    }

    /**
     * Method triggered when mouse exits
     *
     * @param arg0 MouseEvent
     */
    @Override
    public void mouseExited(MouseEvent arg0) {
        // Not implemented
    }

    /**
     * Method triggered when window gets resized: Repaint all components
     *
     * @param e ComponentEvent
     */
    @Override
    public void componentResized(ComponentEvent e) {
        this.repaint();
        this.updateUI();
    }

    /**
     * Method triggered when component is moved
     *
     * @param e ComponentEvent
     */
    @Override
    public void componentMoved(ComponentEvent e) {
        // Not implemented
    }

    /**
     * Method triggered when component is shown
     *
     * @param e ComponentEvent
     */
    @Override
    public void componentShown(ComponentEvent e) {
        // Not implemented
    }

    /**
     * Method triggered when component is hidden
     *
     * @param e ComponentEvent
     */
    @Override
    public void componentHidden(ComponentEvent e) {
        // Not implemented
    }

    /**
     * Initialize a game on the server and join it
     *
     * @throws Exception
     */
    public void initiaizeAndJoinHostedGame(String host, int port, String nickname) throws Exception {
        // Initialize Server-API
        serverApi = new ServerApi(host, port);
        // Start new 3-person-chess game on server
        gameModel = serverApi.hostGame("default-3-person-chess");

        // Join the hosted game
        joinGame(host, port, gameID, nickname);
    }

    /**
     * Join an existing network game as player
     *
     * @param host     Hostname of the server
     * @param port     Port of the server
     * @param gameID   Identifier of the hosted Game
     * @param nickname Nickname of the player
     * @throws Exception
     */
    public void joinGame(String host, int port, String gameID, String nickname) throws Exception {

        // save gameID
        this.setGameID(gameID);

        // Fixes CCD2015-58 [Client] Es wurde keine Verbindung zum Server aufgebaut.
        // Initialize Server-API if not done before
        if (serverApi == null) {
            serverApi = new ServerApi(host, port);
        }

        // Connect to existing game
        clientModel = serverApi.connectToGame(nickname, gameID);

        // Setup Websockets
        webSocketClient = new WebSocketClient();
        webSocketClient.connect(host, port);

        // subscribe to Websocket destination url game/{gameid}/{clientid}
        webSocketClient.subscribe("/game/" + gameID + "/" + clientModel.get().getId(), new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return null;
            }

            /**
             * Handle incoming events (PlayerChangedEvent & PlayerDefeatedEvent) and trigger actions.
             * @param headers Headers of the Request
             * @param payload Content of the Request
             */
            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                ObjectMapper objectMapper = new ObjectMapper();
                // Get type of triggered event by special Header
                String objectType = headers.getFirst("data-type");
                String content = new String((byte[]) payload);

                logger.trace("Event {} was triggered using websockets", objectType);

                // Object-Mapping
                switch (objectType) {
                    case "PlayerChangedEvent":
                        Optional<PlayerChangedEvent> playerChangedEvent = null;
                        try {
                            playerChangedEvent = Optional.of(objectMapper.readValue(content, PlayerChangedEvent.class));
                        } catch (IOException e) {
                            logger.error("", e);
                        }
                        // trigger Action
                        playerChangedAction(playerChangedEvent.get());
                        chessboardChangedAction(false);
                        break;
                    case "PlayerDefeatedEvent":
                        Optional<PlayerDefeatedEvent> playerDefeatedEvent = null;
                        try {
                            playerDefeatedEvent = Optional.of(objectMapper.readValue(content, PlayerDefeatedEvent.class));
                        } catch (IOException e) {
                            logger.error("", e);
                        }
                        // trigger Action
                        playerDefeatedAction(playerDefeatedEvent.get());
                        break;
                    default:
                        // no Event found, we are going to log it
                        logger.trace("Unhandeled Event {} was triggered using websockets", objectType);
                        break;
                }
            }
        });
    }

    /**
     * Get the id of the current game if running
     *
     * @return Identifier to the current game
     */
    public String getGameID() {
        return gameID;
    }

    /**
     * Action which is triggered when the player changes. Responsible for blocking the Chessboard.
     *
     * @param playerChangedEvent
     */
    private void playerChangedAction(PlayerChangedEvent playerChangedEvent) {
        // is the game initialized?
        if (chessboard.getFigures().size() == 0) {
            // initialize figures
            chessboardChangedAction(true);
        }

        if (playerChangedEvent.isItYouTurn()) {
            // unblock chessboard, it is your turn
            chessboard.unblock();
            // show hint
            JOptionPane.showMessageDialog(this, "Du bist dran!");
        } else {
            // block chessboard, it is not your turn
            chessboard.block();
        }

    }

    /**
     * Action which is called when the Chessboard changes. Responsible for updating the figures on the Chessboard.
     * @param setup Does the Chessboard needs to be setup? Is it the first call?
     */
    private void chessboardChangedAction(boolean setup) {
        try {
            // pull game information from the server
            jchess.client.models.Game game = serverApi.getFullGame(getGameID()).get();

            Chessboard newChessboard = game.getChessboard();
            if(setup){
                // initial setup of the Chessboard
                chessboard.setupBoard(newChessboard.getFigures());
            } else {
                // update figures
                chessboard.updateChessboard(newChessboard.getFigures());
            }

            this.repaint();
            this.updateUI();

        } catch (Exception e) {
            logger.error("", e);
        }
    }

    /**
     * Action triggered by the server, when the player is defeated, a hint is shown.
     *
     * @param playerDefeatedEvent
     */
    private void playerDefeatedAction(PlayerDefeatedEvent playerDefeatedEvent) {
        if (playerDefeatedEvent.isAreYouDefeated()) {
            logger.trace("Player {} with ClientID {} playing Team {} defeated",
                    clientModel.get().getNickname(),
                    clientModel.get().getId(),
                    clientModel.get().getTeam()
            );

            JOptionPane.showMessageDialog(this, "Du wurdest besiegt!");
        }
    }

    /**
     * Setter for the identifier of the current game
     * @param gameID
     */
    public void setGameID(String gameID) {
        this.gameID = gameID;
    }


}