package de.mki.jchess.server.controller;

import de.mki.jchess.server.exception.HostedGameNotFoundException;
import de.mki.jchess.server.exception.InvalidGameModeException;
import de.mki.jchess.server.exception.TooManyPlayersException;
import de.mki.jchess.server.implementation.twoPersonChess.TwoPersonGame;
import de.mki.jchess.server.model.Client;
import de.mki.jchess.server.model.Game;
import de.mki.jchess.server.service.HostedGamesService;
import de.mki.jchess.server.service.RandomStringService;
import org.springframework.beans.factory.annotation.Autowired;
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
    RandomStringService randomStringService;
    @Autowired
    HostedGamesService hostedGamesService;

    @RequestMapping("/byGameMode")
    public Game hostRequestByGameMode(@RequestParam("name") String name) throws InvalidGameModeException {
        if (!gameModeController.availableGameModes.contains(name))
            throw new InvalidGameModeException(name);
        Game game;
        switch (name) {
            case "default-2-person-chess":
                game = new TwoPersonGame(randomStringService.getRandomString());
                break;
            default:
                game = null;
        }
        hostedGamesService.addNewHostedGame(game);
        return game;
    }

    @RequestMapping(value = "/joinAsPlayer", method = RequestMethod.POST)
    public Client connectToHostedGameAsPlayer(@RequestParam("hostedID") String hostedID, @RequestBody Client client) throws HostedGameNotFoundException, TooManyPlayersException {
        client.setId(randomStringService.getRandomString());
        hostedGamesService.getHostedGameByID(hostedID).addClientAsPlayer(client);
        return client;
    }

    @RequestMapping("/joinAsObserver")
    public Client connectToHostedGameAsObserver(@RequestParam("hostedID") String hostedID, @RequestBody Client client) throws HostedGameNotFoundException {
        client.setId(randomStringService.getRandomString());
        hostedGamesService.getHostedGameByID(hostedID).addClientAsObserver(client);
        return client;
    }
}
