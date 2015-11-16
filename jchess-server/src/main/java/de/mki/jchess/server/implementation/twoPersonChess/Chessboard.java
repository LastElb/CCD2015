package de.mki.jchess.server.implementation.twoPersonChess;

import de.mki.jchess.server.exception.MoveNotAllowedException;
import de.mki.jchess.server.implementation.threePersonChess.Hexagon;
import de.mki.jchess.server.model.Figure;
import de.mki.jchess.server.model.Game;
import de.mki.jchess.server.model.HistoryEntry;
import de.mki.jchess.server.model.websocket.FigureEvent;
import de.mki.jchess.server.model.websocket.MovementEvent;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;

/**
 * Created by Igor on 11.11.2015.
 */
public class Chessboard extends de.mki.jchess.server.model.Chessboard<Square> {

    public Chessboard(Game parentGame) {
        super(parentGame);
    }

    public Square getFieldByNotation(int x, int y) throws Exception {
        return fields.stream().filter(field -> field.x == x && field.y == y).findFirst().orElseThrow(() -> new Exception("Notation not found"));
    }

    @Override
    public boolean isKingCheckedAtPosition(Figure<Square> king, Square field) {
        return false;
    }

    @Override
    public List<Square> getPossibleFieldsToMove(String figureId) {
        return null;
    }

    @Override
    public void performMovement(String figureId, String targetFieldNotation, SimpMessagingTemplate simpMessagingTemplate) throws MoveNotAllowedException {
        List<Square> possibleMovements = getPossibleFieldsToMove(figureId);
        //TODO implement special moves
        if (possibleMovements.stream().filter(square -> square.getNotation().equals(targetFieldNotation)).count() == 0)
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
                e.printStackTrace();
            }
        });
        // TODO: Add history
        historyEntry.getChessboardEvents().stream().forEach(chessboardEvent -> simpMessagingTemplate.convertAndSend("/websocket/" + getParentGame().getId(), chessboardEvent));
    }
}
