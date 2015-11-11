package de.mki.jchess.server.implementation.twoPersonChess;

import de.mki.jchess.server.model.Game;

/**
 * Created by Igor on 11.11.2015.
 */
public class TwoPersonGame extends Game {

    Chessboard chessboard;

    public TwoPersonGame(String id) {
        super(id, 2);
        setGameMode("default-2-person-chess");
    }

    @Override
    public void initializeGame() {
        chessboard = new Chessboard();
        for (int row = 0; row <= 7; row++)
            for (int column = 0; column <= 7; column++) {
                Square square = new Square(column, row);
                for (Direction direction : Direction.values())
                    try {
                        int difX = 0;
                        int difY = 0;
                        Direction oppositeDirection = direction;
                        switch (direction) {
                            case BOTTOM: difY = 1; oppositeDirection = Direction.TOP; break;
                            case BOTTOMLEFT: difY = 1; difX = -1; oppositeDirection = Direction.TOPRIGHT; break;
                            case BOTTOMRIGHT: difY = 1; difX = 1; oppositeDirection = Direction.TOPLEFT; break;
                            case LEFT: difX = -1; oppositeDirection = Direction.RIGHT; break;
                            case RIGHT: difX = 1; oppositeDirection = Direction.LEFT; break;
                            case TOP: difY = -1; oppositeDirection = Direction.BOTTOM; break;
                            case TOPLEFT: difY = -1; difX = -1; oppositeDirection = Direction.BOTTOMRIGHT; break;
                            case TOPRIGHT: difY = -1; difX = 1; oppositeDirection = Direction.BOTTOMLEFT; break;
                        }
                        Square neighbour = chessboard.getFieldByNotation(column + difX, row + difY);
                        square.addNeighbour(neighbour, direction);
                        neighbour.addNeighbour(square, oppositeDirection);
                    } catch (Exception ignore) {}
                chessboard.addField(square);
            }

    }

    public Chessboard getChessboard() {
        return chessboard;
    }

    public static void main(String[] args) throws Exception {
        TwoPersonGame twoPersonGame = new TwoPersonGame("foobar");
        twoPersonGame.initializeGame();
        System.out.println("chessboard.getFieldByNotation(\"b7\") = " + twoPersonGame.getChessboard().getFieldByNotation("b7"));
    }
}
