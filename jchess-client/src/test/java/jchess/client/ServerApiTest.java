package jchess.client;

import jchess.client.models.Client;
import jchess.client.models.Game;
import org.junit.Test;
import org.springframework.expression.ExpressionException;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by Igor on 28.11.2015.
 */
public class ServerApiTest {

    @Test
    public void testConnectToGame() throws Exception {
        ServerApi serverApi = new ServerApi("localhost", 8080);
        Optional<Game> game = serverApi.hostGame("default-3-person-chess");
        Optional<Client> client = serverApi.connectToGame("Foobar", game.orElseThrow(() -> new Exception("No valid game instance from object mapper")).getId());
        client.orElseThrow(() -> new Exception("No valid client instance from object mapper"));
    }

    @Test
    public void testGetAvailableGameModes() throws Exception {
        ServerApi serverApi = new ServerApi("localhost", 8080);
        serverApi.getAvailableGameModes();
    }

    @Test
    public void testHostGame() throws Exception {
        ServerApi serverApi = new ServerApi("localhost", 8080);
        Optional<Game> game = serverApi.hostGame("default-3-person-chess");
        game.orElseThrow(() -> new Exception("No valid game instance from object mapper"));
    }
}