package jchess.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Igor on 26.11.2015.
 */
public class WebsocketTest {

    private static final Log logger = LogFactory.getLog(WebsocketTest.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Thread thread = new Thread(() -> {
            while (true)
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        });
        thread.start();

        Transport webSocketTransport = new WebSocketTransport(new StandardWebSocketClient());
        List<Transport> transports = Collections.singletonList(webSocketTransport);

        SockJsClient sockJsClient = new SockJsClient(transports);

        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);

        String url = "ws://{host}:{port}/stomp";

        ListenableFuture<StompSession> f = stompClient.connect(url, new StompSessionHandlerAdapter() {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                super.afterConnected(session, connectedHeaders);

                session.subscribe("/game", new StompFrameHandler() {
                    public Type getPayloadType(StompHeaders stompHeaders) {
                        return byte[].class;
                    }

                    public void handleFrame(StompHeaders stompHeaders, Object o) {
                        String content = new String((byte[]) o);
                        logger.debug("Received message " + content);
                    }
                });
                logger.debug("Subscribed to '/game'");
            }
        }, "localhost", 8080);
        StompSession stompSession = f.get();
    }
}
