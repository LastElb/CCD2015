package de.mki.jchess.server.implementation.twoPersonChess;

import de.mki.jchess.server.exception.MoveNotAllowedException;
import de.mki.jchess.server.implementation.threePersonChess.Hexagon;
import de.mki.jchess.server.model.Figure;
import de.mki.jchess.server.model.Game;
import de.mki.jchess.server.model.HistoryEntry;
import de.mki.jchess.server.model.websocket.FigureEvent;
import de.mki.jchess.server.model.websocket.MovementEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;

/**
 * Created by Igor on 11.11.2015.
 */
public class Chessboard extends de.mki.jchess.server.model.Chessboard<Square> {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public Chessboard(Game parentGame) {
        super(parentGame);
    }

    public Square getFieldByNotation(int x, int y) throws Exception {
        return fields.stream().filter(field -> field.x == x && field.y == y).findFirst().orElseThrow(() -> new Exception("Notation not found"));
    }

    /**
     * Returns if the {@link de.mki.jchess.server.implementation.twoPersonChess.figures.King} of a {@link de.mki.jchess.server.model.Client} is checked with the current figure layout.
     * @param clientId The ID of the {@link de.mki.jchess.server.model.Client}.
     * @return Returns true if the {@link de.mki.jchess.server.implementation.twoPersonChess.figures.King} is checked.
     */
    public boolean isKingChecked(String clientId) {
        return false;
    }

    /**
     * Returns if the {@link de.mki.jchess.server.implementation.twoPersonChess.figures.King} of a
     * {@link de.mki.jchess.server.model.Client} is checked with a hypothetical figure layout. Used for predetermination if a movement is valid.
     * @param clientId The ID of the {@link de.mki.jchess.server.model.Client}.
     * @return Returns true if the {@link de.mki.jchess.server.implementation.twoPersonChess.figures.King} would be checked.
     */
    public boolean willKingBeChecked(String clientId) {
        return false;
    }

    @Override
    public List<Square> getPossibleFieldsToMove(String figureId) {
        return null;
    }

    @Override
    public void performMovement(String figureId, String clientId, String targetFieldNotation, SimpMessagingTemplate simpMessagingTemplate) throws MoveNotAllowedException {
        List<Square> possibleMovements = getPossibleFieldsToMove(figureId);
        //TODO implement special moves
        // Throw an exception when the client ID does not match the figures owner id or when the target field is not in the list of possible moves.
        if (possibleMovements.stream().filter(square -> square.getNotation().equals(targetFieldNotation)).count() == 0 ||
                getFigures().stream().filter(squareFigure -> squareFigure.getId().equals(figureId)).filter(squareFigure -> squareFigure.getClient().equals(clientId)).count() == 0)
            throw new MoveNotAllowedException();

        HistoryEntry historyEntry = new HistoryEntry();
        // Remove a figure from the chessboard if there is a figure on the target field
        getFigures().stream()
                .filter(squareFigure -> squareFigure.getPosition().getNotation().equals(targetFieldNotation))
                .findFirst().ifPresent(squareFigure -> {
            historyEntry.getChessboardEvents().add(new FigureEvent().setFigureId(squareFigure.getId()).setEvent(FigureEvent.Event.REMOVED));
            squareFigure.setRemoved(true);
        });

        // Move our figure to the target field
        getFigures().stream()
                .filter(squareFigure -> squareFigure.getId().equals(figureId))
                .findFirst().ifPresent(squareFigure -> {
            historyEntry.getChessboardEvents().add(new MovementEvent().setFigureId(figureId).setFromNotation(squareFigure.getPosition().getNotation()).setToNotation(targetFieldNotation));
            try {
                squareFigure.setPosition((Square) getFieldByNotation(targetFieldNotation));
            } catch (Exception e) {
                logger.error("", e);
            }
        });
        // TODO: Add history
        // TODO: Send event through websocket
        //historyEntry.getChessboardEvents().stream().forEach(chessboardEvent -> simpMessagingTemplate.convertAndSend("/websocket/" + getParentGame().getId(), chessboardEvent));
    }

    @Override
    public boolean areFieldsOccupied(List<Square> positions) {
        return false;
    }
}
