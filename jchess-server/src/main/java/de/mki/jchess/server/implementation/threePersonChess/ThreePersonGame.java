package de.mki.jchess.server.implementation.threePersonChess;

import de.mki.jchess.server.exception.TooManyPlayersException;
import de.mki.jchess.server.implementation.threePersonChess.figures.*;
import de.mki.jchess.server.model.Client;
import de.mki.jchess.server.model.Game;
import de.mki.jchess.server.service.RandomStringService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.stream.IntStream;

/**
 * Created by Igor on 13.11.2015.
 */
public class ThreePersonGame extends Game {

    Chessboard chessboard;
    private static Logger logger = LoggerFactory.getLogger(ThreePersonGame.class);

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
        chessboard = new Chessboard(this);
        IntStream.range(0, 13).forEach(row -> {
            // The if clause could be simplified to IntStream.range(Math.max(0, row - 5), Math.min(13, 8 + row)).
            // But for code understandability I'll just cut it half.
            if (row <= 5) {
                // Upper half
                IntStream.range(0, 8 + row).forEach(column -> {
                    chessboard.addField(generateNeighbours(new Hexagon(column, row)));
                });
            } else {
                // Lower half
                IntStream.range(row - 5, 13).forEach(column -> {
                    chessboard.addField(generateNeighbours(new Hexagon(column, row)));
                });
            }
        });

        // White figures
        Client playerWhite = getPlayerList().get(0);
        try {
            getChessboard().getFigures().add(new Rook(RandomStringService.getRandomString(), playerWhite).setPosition(chessboard.getFieldByNotation(0, 0)).setPictureId("rook-white"));
            getChessboard().getFigures().add(new Knight(RandomStringService.getRandomString(), playerWhite).setPosition(chessboard.getFieldByNotation(1, 0)).setPictureId("knight-white"));
            getChessboard().getFigures().add(new Bishop(RandomStringService.getRandomString(), playerWhite).setPosition(chessboard.getFieldByNotation(2, 0)).setPictureId("bishop-white"));
            getChessboard().getFigures().add(new Queen(RandomStringService.getRandomString(), playerWhite).setPosition(chessboard.getFieldByNotation(3, 0)).setPictureId("queen-white"));
            getChessboard().getFigures().add(new King(RandomStringService.getRandomString(), playerWhite).setPosition(chessboard.getFieldByNotation(4, 0)).setPictureId("king-white"));
            getChessboard().getFigures().add(new Knight(RandomStringService.getRandomString(), playerWhite).setPosition(chessboard.getFieldByNotation(5, 0)).setPictureId("knight-white"));
            getChessboard().getFigures().add(new Bishop(RandomStringService.getRandomString(), playerWhite).setPosition(chessboard.getFieldByNotation(6, 0)).setPictureId("bishop-white"));
            getChessboard().getFigures().add(new Rook(RandomStringService.getRandomString(), playerWhite).setPosition(chessboard.getFieldByNotation(7, 0)).setPictureId("rook-white"));
            IntStream.range(0, 9).forEach(column -> {
                try {
                    getChessboard().getFigures().add(new Pawn(RandomStringService.getRandomString(), playerWhite, Direction.DIAGONALBOTTOM).setPosition(chessboard.getFieldByNotation(column, 1)).setPictureId("pawn-white"));
                } catch (Exception e) {
                    logger.error("", e);
                }
            });
        } catch (Exception e) {
            logger.error("", e);
        }

        // Grey figures
        Client playerGrey = getPlayerList().get(1);
        try {
            getChessboard().getFigures().add(new Rook(RandomStringService.getRandomString(), playerGrey).setPosition(chessboard.getFieldByNotation(12, 5)).setPictureId("rook-grey"));
            getChessboard().getFigures().add(new Knight(RandomStringService.getRandomString(), playerGrey).setPosition(chessboard.getFieldByNotation(12, 6)).setPictureId("knight-grey"));
            getChessboard().getFigures().add(new Bishop(RandomStringService.getRandomString(), playerGrey).setPosition(chessboard.getFieldByNotation(12, 7)).setPictureId("bishop-grey"));
            getChessboard().getFigures().add(new Queen(RandomStringService.getRandomString(), playerGrey).setPosition(chessboard.getFieldByNotation(12, 8)).setPictureId("queen-grey"));
            getChessboard().getFigures().add(new King(RandomStringService.getRandomString(), playerGrey).setPosition(chessboard.getFieldByNotation(12, 9)).setPictureId("king-grey"));
            getChessboard().getFigures().add(new Knight(RandomStringService.getRandomString(), playerGrey).setPosition(chessboard.getFieldByNotation(12, 10)).setPictureId("knight-grey"));
            getChessboard().getFigures().add(new Bishop(RandomStringService.getRandomString(), playerGrey).setPosition(chessboard.getFieldByNotation(12, 11)).setPictureId("bishop-grey"));
            getChessboard().getFigures().add(new Rook(RandomStringService.getRandomString(), playerGrey).setPosition(chessboard.getFieldByNotation(12, 12)).setPictureId("rook-grey"));
            IntStream.range(4, 13).forEach(row -> {
                try {
                    getChessboard().getFigures().add(new Pawn(RandomStringService.getRandomString(), playerGrey, Direction.DIAGONALTOPLEFT).setPosition(chessboard.getFieldByNotation(11, row)).setPictureId("pawn-grey"));
                } catch (Exception e) {
                    logger.error("", e);
                }
            });
        } catch (Exception e) {
            logger.error("", e);
        }

        // Black figures
        Client playerBlack = getPlayerList().get(2);
        try {
            getChessboard().getFigures().add(new Rook(RandomStringService.getRandomString(), playerBlack).setPosition(chessboard.getFieldByNotation(12, 12)).setPictureId("rook-black"));
            getChessboard().getFigures().add(new Knight(RandomStringService.getRandomString(), playerBlack).setPosition(chessboard.getFieldByNotation(12, 11)).setPictureId("knight-black"));
            getChessboard().getFigures().add(new Bishop(RandomStringService.getRandomString(), playerBlack).setPosition(chessboard.getFieldByNotation(12, 10)).setPictureId("bishop-black"));
            getChessboard().getFigures().add(new Queen(RandomStringService.getRandomString(), playerBlack).setPosition(chessboard.getFieldByNotation(12, 9)).setPictureId("queen-black"));
            getChessboard().getFigures().add(new King(RandomStringService.getRandomString(), playerBlack).setPosition(chessboard.getFieldByNotation(12, 8)).setPictureId("king-black"));
            getChessboard().getFigures().add(new Knight(RandomStringService.getRandomString(), playerBlack).setPosition(chessboard.getFieldByNotation(12, 7)).setPictureId("knight-black"));
            getChessboard().getFigures().add(new Bishop(RandomStringService.getRandomString(), playerBlack).setPosition(chessboard.getFieldByNotation(12, 6)).setPictureId("bishop-black"));
            getChessboard().getFigures().add(new Rook(RandomStringService.getRandomString(), playerBlack).setPosition(chessboard.getFieldByNotation(12, 5)).setPictureId("rook-black"));
            IntStream.range(4, 13).forEach(row -> {
                try {
                    getChessboard().getFigures().add(new Pawn(RandomStringService.getRandomString(), playerBlack, Direction.DIAGONALTOPRIGHT).setPosition(chessboard.getFieldByNotation(row - 4, row)).setPictureId("pawn-grey"));
                } catch (Exception e) {
                    logger.error("", e);
                }
            });
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    @Override
    public Chessboard getChessboard() {
        return chessboard;
    }

    @Override
    public Client addClientAsPlayer(Client client, SimpMessagingTemplate simpMessagingTemplate) throws TooManyPlayersException {
        switch (getPlayerList().size()) {
            case 0:
                client.setTeam("white");
                break;
            case 1:
                client.setTeam("grey");
                break;
            case 2:
                client.setTeam("black");
                break;
            default:
                throw new TooManyPlayersException();
        }
        return super.addClientAsPlayer(client, simpMessagingTemplate);
    }

    private Hexagon generateNeighbours(Hexagon hexagon) {
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
                Hexagon neighbour = chessboard.getFieldByNotation(hexagon.column + difX, hexagon.row + difY);
                hexagon.addNeighbour(neighbour, direction);
                neighbour.addNeighbour(hexagon, direction.getOppositeDirection());
            } catch (Exception e) {
                logger.trace("", e);
            };
        }
        return hexagon;
    }
}
