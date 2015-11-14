package de.mki.jchess.server.implementation.twoPersonChess;

import de.mki.jchess.server.implementation.twoPersonChess.figures.*;
import de.mki.jchess.server.model.Client;
import de.mki.jchess.server.model.Figure;
import de.mki.jchess.server.model.Game;
import de.mki.jchess.server.service.RandomStringService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by Igor on 11.11.2015.
 */
public class TwoPersonGame extends Game {

    Chessboard chessboard;
    List<Figure<Square>> playerWhiteFigures;
    List<Figure<Square>> playerBlackFigures;

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
                        switch (direction) {
                            case BOTTOM: difY = -1; break;
                            case BOTTOMLEFT: difY = -1; difX = -1; break;
                            case BOTTOMRIGHT: difY = -1; difX = 1; break;
                            case LEFT: difX = -1; break;
                            case RIGHT: difX = 1; break;
                            case TOP: difY = 1; break;
                            case TOPLEFT: difY = 1; difX = -1; break;
                            case TOPRIGHT: difY = 1; difX = 1; break;
                        }
                        Square neighbour = chessboard.getFieldByNotation(column + difX, row + difY);
                        square.addNeighbour(neighbour, direction);
                        neighbour.addNeighbour(square, direction.getOppositeDirection());
                    } catch (Exception ignore) {}
                chessboard.addField(square);
            }
        playerWhiteFigures = new ArrayList<>();
        playerBlackFigures = new ArrayList<>();

        // Player White \\
        try {
            Client playerWhite = getPlayerList().get(0);
            playerWhiteFigures.add(new King(RandomStringService.getRandomString(), playerWhite).setPictureId("king-white").setPosition(chessboard.getFieldByNotation("e1")));
            playerWhiteFigures.add(new Queen(RandomStringService.getRandomString(), playerWhite).setPictureId("queen-white").setPosition(chessboard.getFieldByNotation("d1")));
            playerWhiteFigures.add(new Rook(RandomStringService.getRandomString(), playerWhite).setPictureId("rook-white").setPosition(chessboard.getFieldByNotation("a1")));
            playerWhiteFigures.add(new Rook(RandomStringService.getRandomString(), playerWhite).setPictureId("rook-white").setPosition(chessboard.getFieldByNotation("h1")));
            playerWhiteFigures.add(new Knight(RandomStringService.getRandomString(), playerWhite).setPictureId("knight-white").setPosition(chessboard.getFieldByNotation("b1")));
            playerWhiteFigures.add(new Knight(RandomStringService.getRandomString(), playerWhite).setPictureId("knight-white").setPosition(chessboard.getFieldByNotation("g1")));
            playerWhiteFigures.add(new Bishop(RandomStringService.getRandomString(), playerWhite).setPictureId("bishop-white").setPosition(chessboard.getFieldByNotation("f1")));
            playerWhiteFigures.add(new Bishop(RandomStringService.getRandomString(), playerWhite).setPictureId("bishop-white").setPosition(chessboard.getFieldByNotation("c1")));
            IntStream.range(0, 8).forEach(value -> {
                Pawn pawn = new Pawn(RandomStringService.getRandomString(), playerWhite, Direction.TOP);
                pawn.setPictureId("pawn-white");
                try {
                    pawn.setPosition(chessboard.getFieldByNotation(value, 1));
                    playerWhiteFigures.add(pawn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            chessboard.getFigures().addAll(playerWhiteFigures);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Player Black \\
        try {
            Client playerBlack = getPlayerList().get(1);
            playerBlackFigures.add(new King(RandomStringService.getRandomString(), playerBlack).setPictureId("king-black").setPosition(chessboard.getFieldByNotation("e8")));
            playerBlackFigures.add(new Queen(RandomStringService.getRandomString(), playerBlack).setPictureId("queen-black").setPosition(chessboard.getFieldByNotation("d8")));
            playerBlackFigures.add(new Rook(RandomStringService.getRandomString(), playerBlack).setPictureId("rook-black").setPosition(chessboard.getFieldByNotation("a8")));
            playerBlackFigures.add(new Rook(RandomStringService.getRandomString(), playerBlack).setPictureId("rook-black").setPosition(chessboard.getFieldByNotation("h8")));
            playerBlackFigures.add(new Knight(RandomStringService.getRandomString(), playerBlack).setPictureId("knight-black").setPosition(chessboard.getFieldByNotation("b8")));
            playerBlackFigures.add(new Knight(RandomStringService.getRandomString(), playerBlack).setPictureId("knight-black").setPosition(chessboard.getFieldByNotation("g8")));
            playerBlackFigures.add(new Bishop(RandomStringService.getRandomString(), playerBlack).setPictureId("bishop-black").setPosition(chessboard.getFieldByNotation("f8")));
            playerBlackFigures.add(new Bishop(RandomStringService.getRandomString(), playerBlack).setPictureId("bishop-black").setPosition(chessboard.getFieldByNotation("c8")));
            IntStream.range(0, 8).forEach(value -> {
                Pawn pawn = new Pawn(RandomStringService.getRandomString(), playerBlack, Direction.TOP);
                pawn.setPictureId("pawn-black");
                try {
                    pawn.setPosition(chessboard.getFieldByNotation(value, 6));
                    playerBlackFigures.add(pawn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            chessboard.getFigures().addAll(playerBlackFigures);
        } catch (Exception e) {
            e.printStackTrace();
        }
        chessboard.setCurrentPlayer(getPlayerList().get(0));
    }

    @Override
    public Chessboard getChessboard() {
        return chessboard;
    }
}

