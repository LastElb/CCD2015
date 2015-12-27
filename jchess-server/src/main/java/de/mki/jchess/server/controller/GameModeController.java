package de.mki.jchess.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor on 11.11.2015.
 */
@RestController
@RequestMapping("/gamemodes")
public class GameModeController {

    List<String> availableGameModes;

    public GameModeController() {
        availableGameModes = new ArrayList<>();
        availableGameModes.add("default-3-person-chess");
    }

    @RequestMapping("/available")
    public List<String> getAvailableGameModes() {
        return availableGameModes;
    }
}
