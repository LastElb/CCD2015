package jchess.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * WebsocketClient for JChess-Client
 * Simplifies websocket connections to a server
 */
public class WebSocketClient {

    private static final Log logger = LogFactory.getLog(WebSocketClient.class);
    private ExecutorService executor;
    private StompSession stompSession;

    /**
     * @return Returns a boolean whether the websocket client is connected or not
     */
    public boolean isConnected() {
        return stompSession != null && stompSession.isConnected();
    }

    /**
     * Connect to a server. Establishes the default websocket connection.
     * @param host    Host as String
     * @param port    Port as integer
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void connect(String host, int port) throws ExecutionException, InterruptedException {
        executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            while (true)
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ignore) {
                    return;
                }
        });

        Transport webSocketTransport = new WebSocketTransport(new StandardWebSocketClient());
        List<Transport> transports = Collections.singletonList(webSocketTransport);

        SockJsClient sockJsClient = new SockJsClient(transports);
        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);

        String url = "ws://{host}:{port}/stomp";
        ListenableFuture<StompSession> f = stompClient.connect(url, new StompSessionHandlerAdapter() {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                super.afterConnected(session, connectedHeaders);
            }
        }, host, port);
        stompSession = f.get();
    }

    /**
     * Subscribe to destination URL
     * @param destination URL you want to subscribe to
     * @param handler Your handler to handle incoming messages
     */
    public void subscribe(String destination, StompFrameHandler handler) {
        stompSession.subscribe(destination, handler);
        logger.trace("Subscribed to '" + destination + "'");
    }

    /**
     * Closes the websocket connection
     * @throws InterruptedException
     */
    public void close() throws InterruptedException {
        stompSession.disconnect();
        executor.shutdownNow();
        executor.awaitTermination(1L, TimeUnit.SECONDS);
    }
}
