package jchess.client;

import de.mki.jchess.commons.Client;
import jchess.client.models.Game;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.expression.ExpressionException;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;

import java.lang.reflect.Type;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by Igor on 28.11.2015.
 */
public class ServerApiTest {

    @Ignore("Does not work as we have no running server.")
    @Test
    public void testConnectToGame() throws Exception {
        ServerApi serverApi = new ServerApi("localhost", 8080);
        Optional<Game> game = serverApi.hostGame("default-3-person-chess");
        Optional<Client> client = serverApi.connectToGame("Foobar", game.orElseThrow(() -> new Exception("No valid game instance from object mapper")).getId());
        client.orElseThrow(() -> new Exception("No valid client instance from object mapper"));

        Thread thread = new Thread(() -> {
            try {
                TimeUnit.HOURS.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        WebSocketClient webSocketClient = new WebSocketClient();
        webSocketClient.connect("localhost", 8080);
        webSocketClient.subscribe("/game/" + game.get().getId() + "/" + client.get().getId(), new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return null;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                String content = new String((byte[])payload);
                System.out.println("content = " + content);
            }
        });

        serverApi.connectToGame("Foobar", game.orElseThrow(() -> new Exception("No valid game instance from object mapper")).getId());
        serverApi.connectToGame("Foobar", game.orElseThrow(() -> new Exception("No valid game instance from object mapper")).getId());
        thread.start();
    }

    @Ignore("Does not work as we have no running server.")
    @Test
    public void testGetAvailableGameModes() throws Exception {
        ServerApi serverApi = new ServerApi("localhost", 8080);
        serverApi.getAvailableGameModes();
    }

    @Ignore("Does not work as we have no running server.")
    @Test
    public void testHostGame() throws Exception {
        ServerApi serverApi = new ServerApi("localhost", 8080);
        Optional<Game> game = serverApi.hostGame("default-3-person-chess");
        game.orElseThrow(() -> new Exception("No valid game instance from object mapper"));
    }
}