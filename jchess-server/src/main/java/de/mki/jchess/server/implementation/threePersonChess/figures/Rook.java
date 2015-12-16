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
        attackableFields.forEach(hexagon -> {
            // If the field is occupied, lets assume we beat this figure
            if (chessboard.areFieldsOccupied(Collections.singletonList(hexagon))) {
                chessboard.getFigures().stream()
                        .filter(o -> !((Figure) o).getClient().getId().equals(getClient().getId()))
                        .filter(o -> ((Figure) o).getPosition().getNotation().equals(hexagon.getNotation()))
                        .findFirst()
                        .ifPresent(o -> {
                            Figure figure = (Figure) o;
                            figure.setHypotheticalRemoved(true);

                            logger.trace("Found beatable figure (" + figure.getName() + ") at field " + hexagon.getNotation());

                            setHypotheticalPosition(hexagon);
                            try {
                                if (!chessboard.willKingBeChecked(getClient().getId()))
                                    output.add(hexagon);
                            } catch (Exception e) {
                                logger.error("", e);
                            }
                            setHypotheticalPosition(null);
                            figure.setHypotheticalRemoved(null);
                        });
            } else {
                setHypotheticalPosition(hexagon);
                try {
                    if (!chessboard.willKingBeChecked(getClient().getId()))
                        output.add(hexagon);
                } catch (Exception e) {
                    logger.error("", e);
                }
                setHypotheticalPosition(null);
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
                if (chessboard.areFieldsOccupied(Collections.singletonList(hexagonOptional.get()))) {
                    // Check if the occupied field has an enemy figure. If so, the field is indeed attackable
                    if (actualChessboard.isFigureOwnedByEnemy(hexagonOptional.get(), getClient())) {
                        // It's an enemy figure
                        output.add(hexagonOptional.get());
                    }
                    break;
                } else {
                    output.add(hexagonOptional.get());
                    hexagonOptional = hexagonOptional.get().getNeighbourByDirection(direction);
                }
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
                if (chessboard.willFieldsBeOccupied(Collections.singletonList(hexagonOptional.get()))) {
                    // Check if the occupied field has an enemy figure. If so, the field is indeed attackable
                    if (actualChessboard.isFigureOwnedByEnemy(hexagonOptional.get(), getClient())) {
                        // It's an enemy figure
                        output.add(hexagonOptional.get());
                    }
                    break;
                } else {
                    output.add(hexagonOptional.get());
                    hexagonOptional = hexagonOptional.get().getNeighbourByDirection(direction);
                }
            }
        });
        return output;
    }

}
