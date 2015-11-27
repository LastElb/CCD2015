package jchess.client;

import org.junit.Test;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;

import java.lang.reflect.Type;

import static org.junit.Assert.*;

/**
 * Created by Igor on 27.11.2015.
 * This test needs a running websocket server
 */
public class WebSocketClientTest {

    @Test
    public void testIsConnected() throws Exception {
        WebSocketClient webSocketClient = new WebSocketClient();
        assertFalse(webSocketClient.isConnected());
    }

//    @Test
    public void testConnect() throws Exception {
        WebSocketClient webSocketClient = new WebSocketClient();
        webSocketClient.connect("localhost", 8080);
        assertTrue(webSocketClient.isConnected());
        webSocketClient.close();
    }

//    @Test
    public void testSubscribe() throws Exception {
        WebSocketClient webSocketClient = new WebSocketClient();
        webSocketClient.connect("localhost", 8080);
        webSocketClient.subscribe("/game", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return null;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {

            }
        });
        webSocketClient.close();
    }

//    @Test
    public void testClose() throws Exception {
        WebSocketClient webSocketClient = new WebSocketClient();
        webSocketClient.connect("localhost", 8080);
        webSocketClient.close();
        assertFalse(webSocketClient.isConnected());
    }
}