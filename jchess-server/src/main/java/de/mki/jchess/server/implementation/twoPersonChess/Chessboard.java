package de.mki.jchess.server.implementation.twoPersonChess;

import de.mki.jchess.server.exception.MoveNotAllowedException;
import de.mki.jchess.server.model.Figure;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;

/**
 * Created by Igor on 11.11.2015.
 */
public class Chessboard extends de.mki.jchess.server.model.Chessboard<Square> {

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
    }
}
