package de.mki.jchess.server.implementation.threePersonChess;

import de.mki.jchess.server.exception.MoveNotAllowedException;
import de.mki.jchess.server.model.Figure;
import de.mki.jchess.server.model.Game;
import de.mki.jchess.server.model.HistoryEntry;
import de.mki.jchess.server.model.websocket.FigureEvent;
import de.mki.jchess.server.model.websocket.MovementEvent;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor on 13.11.2015.
 */
public class Chessboard extends de.mki.jchess.server.model.Chessboard<Hexagon> {

    public Chessboard(Game parentGame) {
        super(parentGame);
    }

    public Hexagon getFieldByNotation(int x, int y) throws Exception {
        return fields.stream().filter(field -> field.column == x && field.row == y).findFirst().orElseThrow(() -> new Exception("Notation not found"));
    }

    @Override
    public boolean isKingCheckedAtPosition(Figure<Hexagon> king, Hexagon field) {
        return false;
    }

    @Override
    public List<Hexagon> getPossibleFieldsToMove(String figureId) {
        List<Hexagon> hexagons = new ArrayList<>();
        getFigures().stream()
                .filter(hexagonFigure -> hexagonFigure.getId().equals(figureId))
                .findFirst().ifPresent(hexagonFigure -> {
            hexagons.addAll(hexagonFigure.getPossibleMovements(this));
            hexagons.addAll(hexagonFigure.getPossibleSpecialMovements(this));
        });
        return hexagons;
    }

    @Override
    public void performMovement(String figureId, String targetFieldNotation, SimpMessagingTemplate simpMessagingTemplate) throws MoveNotAllowedException {
        List<Hexagon> possibleMovements = getPossibleFieldsToMove(figureId);
        //TODO implement special moves
        if (possibleMovements.stream().filter(hexagon -> hexagon.getNotation().equals(targetFieldNotation)).count() == 0)
            throw new MoveNotAllowedException();

        HistoryEntry historyEntry = new HistoryEntry();
        // Remove a figure from the chessboard if there is a figure on the target field
        getFigures().stream()
                .filter(hexagonFigure -> hexagonFigure.getPosition().getNotation().equals(targetFieldNotation))
                .findFirst().ifPresent(hexagonFigure -> {
                    historyEntry.getChessboardEvents().add(new FigureEvent().setFigureId(hexagonFigure.getId()).setEvent(FigureEvent.Event.REMOVED));
                    hexagonFigure.setRemoved(true);
                });

        // Move our figure to the target field
        getFigures().stream()
                .filter(hexagonFigure -> hexagonFigure.getId().equals(figureId))
                .findFirst().ifPresent(hexagonFigure -> {
                    historyEntry.getChessboardEvents().add(new MovementEvent().setFigureId(figureId).setFromNotation(hexagonFigure.getPosition().getNotation()).setToNotation(targetFieldNotation));
                    try {
                        hexagonFigure.setPosition((Hexagon) getFieldByNotation(targetFieldNotation));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        // TODO: Add history
        historyEntry.getChessboardEvents().stream().forEach(chessboardEvent -> simpMessagingTemplate.convertAndSend("/websocket/" + getParentGame().getId(), chessboardEvent));
    }


}
