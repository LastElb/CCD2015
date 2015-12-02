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

    public HostedGamesService() {
        hostedGames = new ArrayList<>();
    }

    public void addNewHostedGame(Game game) {
        hostedGames.add(game);
    }

    public Game getHostedGameByID(String id) throws HostedGameNotFoundException {
        return hostedGames.stream().filter(game -> game.getId().equals(id)).findFirst().orElseThrow(() -> new HostedGameNotFoundException(id));
    }
}
