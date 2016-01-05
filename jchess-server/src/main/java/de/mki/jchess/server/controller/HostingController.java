package de.mki.jchess.server.controller;

import de.mki.jchess.server.exception.HostedGameNotFoundException;
import de.mki.jchess.server.exception.InvalidGameModeException;
import de.mki.jchess.server.exception.TooManyPlayersException;
import de.mki.jchess.server.implementation.threePersonChess.ThreePersonGame;
import de.mki.jchess.commons.Client;
import de.mki.jchess.server.model.Game;
import de.mki.jchess.server.service.HostedGamesService;
import de.mki.jchess.commons.RandomStringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Igor on 11.11.2015.
 */
@RestController
@RequestMapping("/host")
public class HostingController {

    @Autowired
    GameModeController gameModeController;
    @Autowired
    HostedGamesService hostedGamesService;
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @RequestMapping("/byGameMode/{name}")
    public Game hostRequestByGameMode(@PathVariable String name) throws InvalidGameModeException {
        if (!gameModeController.availableGameModes.contains(name))
            throw new InvalidGameModeException(name);
        Game game;
        switch (name) {
            case "default-3-person-chess":
                game = new ThreePersonGame(RandomStringService.getRandomString(4));
                break;
            default:
                game = null;
        }
        hostedGamesService.addNewHostedGame(game);
        return game;
    }

    @RequestMapping(value = "/joinAsPlayer/{gameId}", method = RequestMethod.POST)
    public Client connectToHostedGameAsPlayer(@PathVariable String gameId, @RequestBody Client client) throws HostedGameNotFoundException, TooManyPlayersException {
        client.setId(RandomStringService.getRandomString());
        hostedGamesService.getHostedGameByID(gameId).addClientAsPlayer(client, simpMessagingTemplate);
        return client;
    }

    @RequestMapping(value = "/joinAsObserver/{gameId}", method = RequestMethod.POST)
    public Client connectToHostedGameAsObserver(@PathVariable String gameId, @RequestBody Client client) throws HostedGameNotFoundException {
        client.setId(RandomStringService.getRandomString());
        hostedGamesService.getHostedGameByID(gameId).addClientAsObserver(client);
        return client;
    }
}
