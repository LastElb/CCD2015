package de.mki.jchess.server.service;

import de.mki.jchess.server.exception.HostedGameNotFoundException;
import de.mki.jchess.server.model.Game;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor on 11.11.2015.
 */
@Service
public class HostedGamesService {
    List<Game> hostedGames;

    /**
     * Default constructor.
     */
    public HostedGamesService() {
        hostedGames = new ArrayList<>();
    }

    /**
     * Add a new hosted game to our {@link List}
     * @param game The {@link Game} you want to host.
     */
    public void addNewHostedGame(Game game) {
        hostedGames.add(game);
    }

    /**
     * Returns a {@link Game} by its id.
     * @param id The id of the {@link Game} as {@link String}
     * @return Returns a {@link Game} by its id.
     * @throws HostedGameNotFoundException
     */
    public Game getHostedGameByID(String id) throws HostedGameNotFoundException {
        return hostedGames.stream().filter(game -> game.getId().equals(id)).findFirst().orElseThrow(() -> new HostedGameNotFoundException(id));
    }
}
