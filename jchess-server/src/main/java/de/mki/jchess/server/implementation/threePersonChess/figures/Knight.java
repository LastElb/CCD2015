package de.mki.jchess.server.implementation.threePersonChess.figures;

import de.mki.jchess.server.implementation.threePersonChess.Direction;
import de.mki.jchess.server.implementation.threePersonChess.Hexagon;
import de.mki.jchess.server.model.Chessboard;
import de.mki.jchess.commons.Client;
import de.mki.jchess.server.model.Figure;
import de.mki.jchess.commons.RandomStringService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Implementation of moves with a three person knight.
 * Created by Igor on 12.11.2015.
 */
public class Knight extends Figure<Hexagon> {

    private static final Logger logger = LoggerFactory.getLogger(Knight.class);
    Map<Direction, List<Direction>> directionListMap;

    /**
     * Constructor with the possibility to pass an own id.
     * @param id The figures id.
     * @param client The owner of the {@link Knight}.
     */
    public Knight(String id, Client client) {
        super(client);
        setId(id);
        setName("Knight");
        directionListMap = new HashMap<>();
        directionListMap.put(Direction.TOPRIGHT, Arrays.asList(Direction.DIAGONALTOP, Direction.DIAGONALTOPRIGHT));
        directionListMap.put(Direction.RIGHT, Arrays.asList(Direction.DIAGONALTOPRIGHT, Direction.DIAGONALBOTTOMRIGHT));
        directionListMap.put(Direction.BOTTOMRIGHT, Arrays.asList(Direction.DIAGONALBOTTOMRIGHT, Direction.DIAGONALBOTTOM));
        directionListMap.put(Direction.BOTTOMLEFT, Arrays.asList(Direction.DIAGONALBOTTOM, Direction.DIAGONALBOTTOMLEFT));
        directionListMap.put(Direction.LEFT, Arrays.asList(Direction.DIAGONALBOTTOMLEFT, Direction.DIAGONALTOPLEFT));
        directionListMap.put(Direction.TOPLEFT, Arrays.asList(Direction.DIAGONALTOPLEFT, Direction.DIAGONALTOP));
    }

    /**
     * Default constructor.
     * @param client The owner of the {@link Knight}.
     */
    public Knight(Client client) {
        this(RandomStringService.getRandomString(), client);
    }

    /**
     * {@inheritDoc}
     * @param chessboard The instance of the {@link Chessboard} of the current {@link de.mki.jchess.server.model.Game}
     * @return Returns {@link List} of {@link Hexagon}.
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
     * {@link Knight}s have no special moves.
     * @param chessboard The current {@link Chessboard} instance for checking purposes.
     * @return Returns an empty {@link List} of {@link Hexagon}.
     */
    @Override
    public List<Hexagon> getPossibleSpecialMovements(Chessboard chessboard) {
        // Knight has no special moves
        return new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     * @param chessboard The current {@link Chessboard} instance for checking purposes.
     * @return Returns {@link List} of {@link Hexagon}.
     */
    @Override
    public List<Hexagon> getAttackableFields(Chessboard chessboard) {
        de.mki.jchess.server.implementation.threePersonChess.Chessboard actualChessboard = (de.mki.jchess.server.implementation.threePersonChess.Chessboard) chessboard;
        List<Hexagon> output = new ArrayList<>();
        directionListMap.forEach((direction, directions) -> getPosition().getNeighbourByDirection(direction)
                .ifPresent(hexagon -> directions.forEach(secondDirection -> hexagon.getNeighbourByDirection(secondDirection).ifPresent(targetHexagon -> {
                    if (chessboard.areFieldsOccupied(Collections.singletonList(targetHexagon))) {
                        if (actualChessboard.isFigureOwnedByEnemy(targetHexagon, getClient()))
                            output.add(targetHexagon);
                    } else
                        output.add(targetHexagon);
                }))));
        return output;
    }

    /**
     * {@inheritDoc}
     * @param chessboard The current {@link Chessboard} instance for checking purposes.
     * @return Returns {@link List} of {@link Hexagon}.
     */
    @Override
    public List<Hexagon> getHypotheticalAttackableFields(Chessboard chessboard) {
        de.mki.jchess.server.implementation.threePersonChess.Chessboard actualChessboard = (de.mki.jchess.server.implementation.threePersonChess.Chessboard) chessboard;
        List<Hexagon> output = new ArrayList<>();
        directionListMap.forEach((direction, directions) -> getHypotheticalPosition().getNeighbourByDirection(direction)
                .ifPresent(hexagon -> directions.forEach(secondDirection -> hexagon.getNeighbourByDirection(secondDirection).ifPresent(targetHexagon -> {
                    if (chessboard.areFieldsOccupied(Collections.singletonList(targetHexagon))) {
                        if (actualChessboard.isFigureOwnedByEnemy(targetHexagon, getClient()))
                            output.add(targetHexagon);
                    } else
                        output.add(targetHexagon);
                }))));
        return output;
    }

}
