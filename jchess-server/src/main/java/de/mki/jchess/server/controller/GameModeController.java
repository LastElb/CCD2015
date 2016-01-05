package de.mki.jchess.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * The game mode controller specifies the game modes that can be played.
 * Created by Igor on 11.11.2015.
 */
@RestController
@RequestMapping("/gamemodes")
public class GameModeController {

    List<String> availableGameModes;

    /**
     * Constructor initializing the available game modes.
     */
    public GameModeController() {
        availableGameModes = new ArrayList<>();
        availableGameModes.add("default-3-person-chess");
    }

    /**
     * Lists all available game modes.
     * @return Returns a {@link List} of all available game modes.
     */
    @RequestMapping("/available")
    public List<String> getAvailableGameModes() {
        return availableGameModes;
    }
}
