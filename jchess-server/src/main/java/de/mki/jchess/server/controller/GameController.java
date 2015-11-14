package de.mki.jchess.server.controller;

import de.mki.jchess.server.exception.HostedGameNotFoundException;
import de.mki.jchess.server.exception.MoveNotAllowedException;
import de.mki.jchess.server.model.Game;
import de.mki.jchess.server.service.HostedGamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping("/poll")
    public PollInformation pollGameAction(@PathVariable String gameId) throws HostedGameNotFoundException {
        Game game = hostedGamesService.getHostedGameByID(gameId);
        if (!game.hasSufficientPlayers())
            return new PollInformation().setInfo("Waiting for more players.").addAction("poll");
        return new PollInformation();
    }

    @RequestMapping("/full")
    public Game getFullGame(@PathVariable String gameId) throws HostedGameNotFoundException {
        return hostedGamesService.getHostedGameByID(gameId);
    }

    @RequestMapping("/possibleMoves/{figureId}")
    public List getPossibleMoves(@PathVariable String gameId, @PathVariable String figureId) throws HostedGameNotFoundException {
        Game game = hostedGamesService.getHostedGameByID(gameId);
        return game.getChessboard().getPossibleFieldsToMove(figureId);
    }

    @RequestMapping("/performMove/{figureId}/{targetNotation}")
    public void performMove(@PathVariable String gameId, @PathVariable String figureId, @PathVariable String targetNotation) throws HostedGameNotFoundException, MoveNotAllowedException {
        Game game = hostedGamesService.getHostedGameByID(gameId);
        game.getChessboard().performMovement(figureId, targetNotation, simpMessagingTemplate);
    }


    public static class PollInformation {
        List<String> actions;
        String info;

        public PollInformation() {
            actions = new ArrayList<>();
        }

        public PollInformation addAction(String action) {
            actions.add(action);
            return this;
        }

        public PollInformation removeAction(String action) {
            actions.remove(action);
            return this;
        }

        public String getInfo() {
            return info;
        }

        public PollInformation setInfo(String info) {
            this.info = info;
            return this;
        }

        public List<String> getActions() {
            return actions;
        }

        public PollInformation setActions(List<String> actions) {
            this.actions = actions;
            return this;
        }
    }
}
