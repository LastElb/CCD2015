package de.mki.jchess.server.controller;

import de.mki.jchess.commons.Client;
import de.mki.jchess.server.exception.HostedGameNotFoundException;
import de.mki.jchess.server.exception.MoveNotAllowedException;
import de.mki.jchess.commons.Figure;
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
 * The game controller receives all commands that are bound to specific game
 * Created by Igor on 11.11.2015.
 */
@RestController
@RequestMapping("/game/{gameId}")
public class GameController {

    @Autowired
    HostedGamesService hostedGamesService;
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    /**
     * Returns all information about the specified {@link Game}.
     * @param gameId    The ID of the {@link Game}.
     * @return Returns all information about the specified {@link Game}.
     * @throws HostedGameNotFoundException
     */
    @RequestMapping("/full")
    public Game getFullGame(@PathVariable String gameId) throws HostedGameNotFoundException {
        return hostedGamesService.getHostedGameByID(gameId);
    }

    /**
     * Returns a {@link List} of {@link de.mki.jchess.commons.Field}s a specified {@link Figure} can move to.
     * @param gameId      The ID of the {@link Game}.
     * @param figureId    The ID of the {@link Figure}.
     * @return Returns a {@link List} of {@link de.mki.jchess.commons.Field}s a specified {@link Figure} can move to.
     * @throws HostedGameNotFoundException
     */
    @RequestMapping("/possibleMoves/{figureId}")
    public List getPossibleMoves(@PathVariable String gameId, @PathVariable String figureId) throws HostedGameNotFoundException {
        Game game = hostedGamesService.getHostedGameByID(gameId);
        return game.getChessboard().getPossibleFieldsToMove(figureId);
    }

    /**
     * Returns a {@link List} of {@link de.mki.jchess.commons.Field}s a specified {@link Figure}
     * on a {@link de.mki.jchess.commons.Field} can move to.
     * @param gameId           The ID of the {@link Game}.
     * @param fieldNotation    The notation of the target {@link de.mki.jchess.commons.Field}.
     * @return Returns a {@link List} of {@link de.mki.jchess.commons.Field}s a specified {@link Figure}
     * on a {@link de.mki.jchess.commons.Field} can move to.
     * @throws HostedGameNotFoundException
     */
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

    /**
     * Performs a movement for a specified {@link Figure}.
     * @param gameId            The ID of the {@link Game}.
     * @param figureId          The ID of the {@link Figure}.
     * @param clientId          The ID of the {@link Client}.
     * @param targetNotation    The notation of the target {@link de.mki.jchess.commons.Field}.
     * @throws HostedGameNotFoundException
     * @throws MoveNotAllowedException
     */
    @RequestMapping("/performMove/{figureId}/{clientId}/{targetNotation}")
    public void performMove(@PathVariable String gameId, @PathVariable String figureId, @PathVariable String clientId, @PathVariable String targetNotation) throws HostedGameNotFoundException, MoveNotAllowedException {
        Game game = hostedGamesService.getHostedGameByID(gameId);
        game.getChessboard().performMovement(figureId, clientId, targetNotation, simpMessagingTemplate);
    }

    /**
     * Performs a movement for a {@link Figure} on specified {@link de.mki.jchess.commons.Field}.
     * @param gameId            The ID of the {@link Game}.
     * @param sourceNotation    The notation of the {@link de.mki.jchess.commons.Field} the {@link Figure} stands on.
     * @param clientId          he ID of the {@link Client}.
     * @param targetNotation    The notation of the target {@link de.mki.jchess.commons.Field}.
     * @throws HostedGameNotFoundException
     * @throws MoveNotAllowedException
     */
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
