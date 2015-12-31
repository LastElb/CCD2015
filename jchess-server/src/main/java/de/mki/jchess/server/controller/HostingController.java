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
 * The Hosting controller can create new games and join {@link Client}s as Player or Observer
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

    /**
     * Hosts a new {@link Game} that represents the game mode specified in <code>name</code>.
     * @param name    The name of the game mode.
     * @return Returns the new instance of the {@link Game}. The {@link Game} will not be initialized at this state!
     * @throws InvalidGameModeException
     */
    @RequestMapping("/byGameMode/{name}")
    public Game hostRequestByGameMode(@PathVariable String name) throws InvalidGameModeException {
        if (!gameModeController.availableGameModes.contains(name))
            throw new InvalidGameModeException(name);
        Game game;
        switch (name) {
            case "default-3-person-chess":
                game = new ThreePersonGame(RandomStringService.getRandomString());
                break;
            default:
                game = null;
        }
        hostedGamesService.addNewHostedGame(game);
        return game;
    }

    /**
     * Connects a new {@link Client} as Player to a specified {@link Game}. If the {@link Game} does not exist, a
     * {@link HostedGameNotFoundException} is thrown. If there are too many players connect to the game, the
     * {@link TooManyPlayersException} is thrown.
     * @param gameId    The ID of the {@link Game} to connect to.
     * @param client    An empty {@link Client} just containing the {@link Client#nickname} field.
     * @return Returns a new {@link Client} instance containing the ID.
     * @throws HostedGameNotFoundException
     * @throws TooManyPlayersException
     */
    @RequestMapping(value = "/joinAsPlayer/{gameId}", method = RequestMethod.POST)
    public Client connectToHostedGameAsPlayer(@PathVariable String gameId, @RequestBody Client client) throws HostedGameNotFoundException, TooManyPlayersException {
        client.setId(RandomStringService.getRandomString());
        hostedGamesService.getHostedGameByID(gameId).addClientAsPlayer(client, simpMessagingTemplate);
        return client;
    }

    /**
     * Connects a new {@link Client} as Observer to a specified {@link Game}. If the {@link Game} does not exist, a
     * {@link HostedGameNotFoundException} is thrown.
     * @param gameId    The ID of the {@link Game} to connect to.
     * @param client    An empty {@link Client} just containing the {@link Client#nickname} field.
     * @return Returns a new {@link Client} instance containing the ID.
     * @throws HostedGameNotFoundException
     */
    @RequestMapping(value = "/joinAsObserver/{gameId}", method = RequestMethod.POST)
    public Client connectToHostedGameAsObserver(@PathVariable String gameId, @RequestBody Client client) throws HostedGameNotFoundException {
        client.setId(RandomStringService.getRandomString());
        hostedGamesService.getHostedGameByID(gameId).addClientAsObserver(client);
        return client;
    }
}
