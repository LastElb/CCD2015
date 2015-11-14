package de.mki.jchess.server.implementation.threePersonChess;

import de.mki.jchess.server.model.Game;

import java.util.stream.IntStream;

/**
 * Created by Igor on 13.11.2015.
 */
public class ThreePersonGame extends Game {

    Chessboard chessboard;

    /**
     * Default constructor for creating a new three person chess game.
     *
     * @param id             A random string the game can be identified with
     */
    public ThreePersonGame(String id) {
        super(id, 3);
        setGameMode("default-3-person-chess");
    }

    @Override
    public void initializeGame() {
        chessboard = new Chessboard();
        IntStream.range(0, 13).forEach(row -> {
            // The if clause could be simplified to IntStream.range(Math.max(0, row - 5), Math.min(13, 8 + row)).
            // But for code understandability I'll just cut it half.
            if (row <= 5) {
                // Upper half
                IntStream.range(0, 8 + row).forEach(column -> {
                    Hexagon hexagon = new Hexagon(column, row);
                    for (Direction direction : Direction.values()) {
                        try {
                            int difX = 0;
                            int difY = 0;
                            switch (direction) {
                                case DIAGONALTOP: difY = -2; difX = -1; break;
                                case TOPRIGHT: difY = -1; break;
                                case DIAGONALTOPRIGHT: difY = -1; difX = 1; break;
                                case RIGHT: difX = 1; break;
                                case DIAGONALBOTTOMRIGHT: difY = 1; difX = 2; break;
                                case BOTTOMRIGHT: difY = 1; difX = 1; break;
                                case DIAGONALBOTTOM: difY = 2; difX = 1; break;
                                case BOTTOMLEFT: difY = 1; break;
                                case DIAGONALBOTTOMLEFT: difY = 1; difX = -1; break;
                                case LEFT: difX = -1; break;
                                case DIAGONALTOPLEFT: difY = -1; difX = -2; break;
                                case TOPLEFT: difY = -1; difX = -1; break;
                            }
                            Hexagon neighbour = chessboard.getFieldByNotation(column + difX, row + difY);
                            hexagon.addNeighbour(neighbour, direction);
                            neighbour.addNeighbour(hexagon, direction.getOppositeDirection());
                        } catch (Exception ignore) {};
                    }
                    chessboard.addField(hexagon);
                });
            } else {
                // Lower half
                IntStream.range(row - 5, 13).forEach(column -> {
                    Hexagon hexagon = new Hexagon(column, row);
                    for (Direction direction : Direction.values()) {
                        try {
                            int difX = 0;
                            int difY = 0;
                            switch (direction) {
                                case DIAGONALTOP: difY = -2; difX = -1; break;
                                case TOPRIGHT: difY = -1; break;
                                case DIAGONALTOPRIGHT: difY = -1; difX = 1; break;
                                case RIGHT: difX = 1; break;
                                case DIAGONALBOTTOMRIGHT: difY = 1; difX = 2; break;
                                case BOTTOMRIGHT: difY = 1; difX = 1; break;
                                case DIAGONALBOTTOM: difY = 2; difX = 1; break;
                                case BOTTOMLEFT: difY = 1; break;
                                case DIAGONALBOTTOMLEFT: difY = 1; difX = -1; break;
                                case LEFT: difX = -1; break;
                                case DIAGONALTOPLEFT: difY = -1; difX = -2; break;
                                case TOPLEFT: difY = -1; difX = -1; break;
                            }
                            Hexagon neighbour = chessboard.getFieldByNotation(column + difX, row + difY);
                            hexagon.addNeighbour(neighbour, direction);
                            neighbour.addNeighbour(hexagon, direction.getOppositeDirection());
                        } catch (Exception ignore) {};
                    }
                    chessboard.addField(hexagon);
                });
            }
        });
    }

    @Override
    public Chessboard getChessboard() {
        return chessboard;
    }
}
