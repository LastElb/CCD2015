package de.mki.jchess.server.implementation.threePersonChess.figures;

import de.mki.jchess.server.implementation.threePersonChess.Direction;
import de.mki.jchess.server.implementation.threePersonChess.Hexagon;
import de.mki.jchess.server.model.Chessboard;
import de.mki.jchess.server.model.Client;
import de.mki.jchess.server.model.Figure;
import de.mki.jchess.server.model.websocket.MovementEvent;
import de.mki.jchess.server.service.RandomStringService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Implementation of moves with a three person rook.
 * Created by Igor on 12.11.2015.
 */
public class Rook extends Figure<Hexagon> {

    private static final Logger logger = LoggerFactory.getLogger(Rook.class);
    List<Direction> directions;

    /**
     * Constructor with the possibility to pass an own id.
     * @param id The figures id.
     * @param client The owner of the {@link Rook}.
     */
    public Rook(String id, Client client) {
        super(client);
        setId(id);
        setName("Rook");
        directions = Arrays.asList(Direction.TOPRIGHT, Direction.RIGHT, Direction.BOTTOMRIGHT, Direction.BOTTOMLEFT, Direction.LEFT, Direction.TOPLEFT);
    }

    /**
     * Default constructor.
     * @param client The owner of the {@link Rook}.
     */
    public Rook(Client client) {
        this(RandomStringService.getRandomString(), client);
    }

    /**
     * {@inheritDoc}
     * @param chessboard The instance of the {@link Chessboard} of the current {@link de.mki.jchess.server.model.Game}
     * @return Returns a {@link List} of {@link Hexagon}s.
     */
    @Override
    public List<Hexagon> getPossibleMovements(Chessboard chessboard) {
        List<Hexagon> attackableFields = getAttackableFields(chessboard);
        List<Hexagon> output = new ArrayList<>();
        FigureUtils.evaluatePossibleMovements(this, attackableFields, (de.mki.jchess.server.implementation.threePersonChess.Chessboard) chessboard, getClient(), output);
        return output;
    }

    /**
     * {@inheritDoc}
     * @param chessboard The current {@link Chessboard} instance for checking purposes.
     * @return Returns a {@link List} of {@link Hexagon}s.
     */
    @Override
    public List<Hexagon> getPossibleSpecialMovements(Chessboard chessboard) {
        //TODO Castling (Rochade)
        Optional<King> king = chessboard.getFigures().stream().filter(o -> o instanceof King).filter(o1 -> ((King) o1).getClient().getId().equals(getClient().getId())).findFirst();
        if (king.isPresent()) {
            boolean wasMoved = chessboard.getParentGame().getGameHistory().stream()
                    .filter(historyEntry -> historyEntry.getChessboardEvents().stream()
                            .filter(chessboardEvent -> chessboardEvent instanceof MovementEvent)
                            .map(chessboardEvent -> (MovementEvent) chessboardEvent)
                            .filter(movementEvent -> movementEvent.getFigureId().equals(getId()) || movementEvent.getFigureId().equals(king.get().getId())).count() != 0).findAny().isPresent();
            logger.trace("Figure {} (id {}) was moved: {}", getName(), getId(), wasMoved);
            logger.trace("Figure {} (id {}) was moved: {}", king.get().getName(), king.get().getId(), wasMoved);
            if (!wasMoved) {
                // Both figures were not moved.
                // Also all fields can not be attacked by enemies figures.
            }
        }

        return new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     * @param chessboard The current {@link Chessboard} instance for checking purposes.
     * @return Returns a {@link List} of {@link Hexagon}s.
     */
    @Override
    public List<Hexagon> getAttackableFields(Chessboard chessboard) {
        de.mki.jchess.server.implementation.threePersonChess.Chessboard actualChessboard = (de.mki.jchess.server.implementation.threePersonChess.Chessboard) chessboard;
        List<Hexagon> output = new ArrayList<>();
        directions.forEach(direction -> {
            Optional<Hexagon> hexagonOptional = getPosition().getNeighbourByDirection(direction);
            while (hexagonOptional.isPresent()) {
                logger.trace("Checking attackable fields for direction {} from {} to {}", direction, getPosition().getNotation(), hexagonOptional.get().getNotation());
                hexagonOptional = FigureUtils.addHexagonToListIfFreeOrEnemy(actualChessboard, hexagonOptional, output, getClient(), direction);
            }
        });
        return output;
    }

    /**
     * {@inheritDoc}
     * @param chessboard The current {@link Chessboard} instance for checking purposes.
     * @return Returns a {@link List} of {@link Hexagon}s.
     */
    @Override
    public List<Hexagon> getHypotheticalAttackableFields(Chessboard chessboard) {
        de.mki.jchess.server.implementation.threePersonChess.Chessboard actualChessboard = (de.mki.jchess.server.implementation.threePersonChess.Chessboard) chessboard;
        List<Hexagon> output = new ArrayList<>();
        directions.forEach(direction -> {
            Optional<Hexagon> hexagonOptional = getHypotheticalPosition().getNeighbourByDirection(direction);
            while (hexagonOptional.isPresent()) {
                logger.trace("Checking attackable fields for direction {} from {} to {}", direction, getHypotheticalPosition().getNotation(), hexagonOptional.get());
                hexagonOptional = FigureUtils.addHexagonToListIfFreeOrEnemyHypothetical(actualChessboard, hexagonOptional, output, getClient(), direction);
            }
        });
        return output;
    }

}
