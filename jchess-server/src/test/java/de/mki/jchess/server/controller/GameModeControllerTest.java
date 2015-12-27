package de.mki.jchess.server.controller;

import de.mki.jchess.server.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

/**
 * Created by Igor on 17.12.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class GameModeControllerTest {

    @Autowired
    GameModeController gameModeController;

    /**
     * Check if the {@link java.util.List} of game modes is not empty.
     * @throws Exception
     */
    @Test
    public void testGetAvailableGameModes() throws Exception {
        assertFalse(gameModeController.getAvailableGameModes().isEmpty());
    }
}