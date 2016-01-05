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

import com.fasterxml.jackson.databind.ObjectMapper;
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
import jchess.client.models.Position;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Optional;

import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class responsible for the starts of new games, loading games,
 * saving it, and for ending it.
 * This class is also responsible for appoing player with have
 * a move at the moment
 */
public class Game extends JPanel implements MouseListener, ComponentListener {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Game.class);

    private ServerApi serverApi;
    private WebSocketClient webSocketClient;
    private Optional<jchess.client.models.Game> gameModel;
    private Optional<Client> clientModel;
    private smallHexboard chessboard;

    Game() {

        initializeHexboard();
        //this.setLayout(null);
        // ToDo: Initalize Chessboard & add MouseListener & add JPanel
        // chessboard.addMouseListener(this);
        // chessboard.setLocation(new Point(0, 0));
        // this.add(chessboard);

        //this.setLayout(null);
        this.addComponentListener(this);
        //this.setDoubleBuffered(true);
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
     * Method to swich active players after move
     */
    public void switchActive() {
    }

    public void initializeHexboard() {
        this.setLayout(null);
        this.setDoubleBuffered(false);

        java.util.List<Figure> figures;
        Figure figure1;
        Position position1;

        figure1 = new Figure();
        figure1.setId("yripqepbztt6nv5d8t2m");

        position1 = new Position();
        position1.setNotation("A1");

        figure1.setPositionObject(position1);
        figure1.setName("King");
        figure1.setPictureId("king-white");
        figure1.setRemoved(false);

        Figure figure2 = new Figure();
        figure2.setId("y2ripqepbztt6nv5d8t2m");

        Position position2 = new Position();
        position2.setNotation("B2");

        figure2.setPositionObject(position2);
        figure2.setName("Knight");
        figure2.setPictureId("knight-white");
        figure2.setRemoved(false);

        figures = new ArrayList<Figure>();
        figures.add(figure1);
        figures.add(figure2);

        try {
            chessboard = new smallHexboard();

            chessboard.setupBoard(figures);
            chessboard.addMouseListener(this);
            this.add(chessboard);
            chessboard.repaint();
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    // MouseListener:
    public void mouseClicked(MouseEvent arg0) {
    }


    public void mousePressed(MouseEvent event) {
        if (event.getButton() == MouseEvent.BUTTON1) //left button
        {
            // its your turn
            if (!chessboard.isBlocked()) {
                int x = event.getX();//get X position of mouse
                int y = event.getY();//get Y position of mouse

                // @Todo: Pass position to chessboard
                String clickedField = chessboard.getClickedField(x, y);

                logger.trace("Click on x={}, y={} which is field {}",
                        x,
                        y,
                        clickedField
                );

            } else {
                logger.trace("Chessboard is blocked");
            }
        }
        //chessboard.repaint();
    }

    public void mouseReleased(MouseEvent arg0) {
    }

    public void mouseEntered(MouseEvent arg0) {
    }

    public void mouseExited(MouseEvent arg0) {
    }

    public void componentResized(ComponentEvent e) {
        /*
        int height = this.getHeight() >= this.getWidth() ? this.getWidth() : this.getHeight();
        int chess_height = (int) Math.round((height * 0.8) / 8) * 8;
        this.chessboard.resizeChessboard((int) chess_height);
        chess_height = this.chessboard.getHeight();
        this.moves.getScrollPane().setLocation(new Point(chess_height + 5, 100));
        this.moves.getScrollPane().setSize(this.moves.getScrollPane().getWidth(), chess_height - 100);
        this.gameClock.setLocation(new Point(chess_height + 5, 0));
        */
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentShown(ComponentEvent e) {
    }

    public void componentHidden(ComponentEvent e) {
    }

    /**
     * Initialize a game on the server and join it
     *
     * @throws Exception
     */
    public void initiaizeAndJoinHostedGame(String host, int port, String nickname) throws Exception {
        serverApi = new ServerApi(host, port);
        gameModel = serverApi.hostGame("default-3-person-chess");

        joinGame(host, port, gameModel.get().getId(), nickname);
    }

    /**
     * Join a network game as player
     *
     * @param host
     * @param port
     * @param gameID
     * @param nickname
     * @throws Exception
     */
    public void joinGame(String host, int port, String gameID, String nickname) throws Exception {
        if (serverApi != null) {
            // setup Websockets
            webSocketClient = new WebSocketClient();
            webSocketClient.connect(host, port);

            // subscribe to Websocket destination url game/{gameid}
            webSocketClient.subscribe("/game/" + gameID, new StompFrameHandler() {
                @Override
                public Type getPayloadType(StompHeaders headers) {
                    return null;
                }

                @Override
                public void handleFrame(StompHeaders headers, Object payload) {
                    System.out.println(headers);
                    System.out.println(payload);
                }
            });

            // Connect to game
            clientModel = serverApi.connectToGame(nickname, gameID);

            // subscribe to Websocket destination url game/{gameid}/{clientid}
            webSocketClient.subscribe("/game/" + gameID + "/" + clientModel.get().getId(), new StompFrameHandler() {
                @Override
                public Type getPayloadType(StompHeaders headers) {
                    return null;
                }

                @Override
                public void handleFrame(StompHeaders headers, Object payload) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    String objectType = headers.getFirst("data-type");
                    String content = new String((byte[]) payload);

                    logger.trace("Event {} was triggered using websockets", objectType);

                    switch (objectType) {
                        case "PlayerChangedEvent":
                            Optional<PlayerChangedEvent> playerChangedEvent = null;
                            try {
                                playerChangedEvent = Optional.of(objectMapper.readValue(content, PlayerChangedEvent.class));
                            } catch (IOException e) {
                                logger.error("", e);
                            }
                            PlayerChangedAction(playerChangedEvent.get());
                            break;
                        case "HistoryEntry":
                            // at the moment the client is not able to handle the history-objects
                            ChessboardChangedAction();
                            break;
                        case "PlayerDefeatedEvent":
                            Optional<PlayerDefeatedEvent> playerDefeatedEvent = null;
                            try {
                                playerDefeatedEvent = Optional.of(objectMapper.readValue(content, PlayerDefeatedEvent.class));
                            } catch (IOException e) {
                                logger.error("", e);
                            }
                            PlayerDefeatedAction(playerDefeatedEvent.get());
                            break;
                        default:
                            break;
                    }
                }
            });

        } else {
            throw new Exception("Es wurde keine Verbindung zum Server aufgebaut.");
        }
    }

    /**
     * Get the id of the current game if running
     *
     * @return
     */
    public String getGameID() {
        return gameModel.get().getId();
    }

    /**
     * Action which is triggered when the player changes
     *
     * @param playerChangedEvent
     */
    private void PlayerChangedAction(PlayerChangedEvent playerChangedEvent) {
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
     * This action is triggered by the server when something on the chessboard changed. The chessboard gets reloaded
     */
    private void ChessboardChangedAction() {
        try {
            jchess.client.models.Game game = serverApi.getFullGame(getGameID()).get();

            Chessboard newChessboard = (Chessboard) game.getChessboard();
            chessboard.updateChessboard(newChessboard.getFigures());
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    /**
     * Action triggered by the server, when the player is defeated
     *
     * @param playerDefeatedEvent
     */
    private void PlayerDefeatedAction(PlayerDefeatedEvent playerDefeatedEvent) {
        if (playerDefeatedEvent.isAreYouDefeated()) {
            logger.trace("Player {} with ClientID {} playing Team {} defeated",
                    clientModel.get().getNickname(),
                    clientModel.get().getId(),
                    clientModel.get().getTeam()
            );

            JOptionPane.showMessageDialog(this, "Du wurdest besiegt!");
        }
    }
}