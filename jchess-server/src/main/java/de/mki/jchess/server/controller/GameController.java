package de.mki.jchess.server.controller;

import de.mki.jchess.server.exception.HostedGameNotFoundException;
import de.mki.jchess.server.exception.MoveNotAllowedException;
import de.mki.jchess.server.model.Figure;
import de.mki.jchess.server.model.Game;
import de.mki.jchess.server.service.HostedGamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Igor on 11.11.2015.
 */
@RestController
@RequestMapping("/game/{gameId}")
public class GameController {

    @Autowired
    HostedGamesService hostedGamesService;
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @RequestMapping("/full")
    public Game getFullGame(@PathVariable String gameId) throws HostedGameNotFoundException {
        return hostedGamesService.getHostedGameByID(gameId);
    }

    @RequestMapping("/possibleMoves/{figureId}")
    public List getPossibleMoves(@PathVariable String gameId, @PathVariable String figureId) throws HostedGameNotFoundException {
        Game game = hostedGamesService.getHostedGameByID(gameId);
        return game.getChessboard().getPossibleFieldsToMove(figureId);
    }

    @RequestMapping("/possibleMovesByField/{fieldNotation}")
    public List getPossibleMovesByField(@PathVariable String gameId, @PathVariable String fieldNotation) throws HostedGameNotFoundException {
        Game game = hostedGamesService.getHostedGameByID(gameId);
        Optional<Figure> figure = game.getChessboard().getFigures().stream()
                .filter(o -> !((Figure) o).isRemoved())
                .filter(o -> ((Figure) o).getPosition().getNotation().equals(fieldNotation))
                .findAny();
        if (figure.isPresent()) {
            return game.getChessboard().getPossibleFieldsToMove(figure.get().getId());
        }
        return new ArrayList<>();
    }

    @RequestMapping("/performMove/{figureId}/{clientId}/{targetNotation}")
    public void performMove(@PathVariable String gameId, @PathVariable String figureId, @PathVariable String clientId, @PathVariable String targetNotation) throws HostedGameNotFoundException, MoveNotAllowedException {
        Game game = hostedGamesService.getHostedGameByID(gameId);
        game.getChessboard().performMovement(figureId, clientId, targetNotation, simpMessagingTemplate);
    }

    @RequestMapping("/performMoveByField/{sourceNotation}/{clientId}/{targetNotation}")
    public void performMoveByField(@PathVariable String gameId, @PathVariable String sourceNotation, @PathVariable String clientId, @PathVariable String targetNotation) throws HostedGameNotFoundException, MoveNotAllowedException {
        Game game = hostedGamesService.getHostedGameByID(gameId);
        Optional<Figure> figure = game.getChessboard().getFigures().stream()
                .filter(o -> !((Figure) o).isRemoved())
                .filter(o -> ((Figure) o).getPosition().getNotation().equals(sourceNotation))
                .findAny();
        game.getChessboard().performMovement(figure.orElseThrow(MoveNotAllowedException::new).getId(), clientId, targetNotation, simpMessagingTemplate);
    }
}
