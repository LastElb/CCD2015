package jchess;

import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor on 12.11.2015.
 */
public class Websocket {
    void test() {

        StandardWebSocketClient webSocketClient = new StandardWebSocketClient();
        List<Transport> transports = new ArrayList<>();
        transports.add(new WebSocketTransport(webSocketClient));
        SockJsClient sockJsClient = new SockJsClient(transports);
        try {
            ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
            taskScheduler.afterPropertiesSet();

            String stompUrl = "ws://{host}:{port}/stomp";
            WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
            stompClient.setMessageConverter(new StringMessageConverter());
            stompClient.setTaskScheduler(taskScheduler);
            stompClient.setDefaultHeartbeat(new long[] {0, 0});
            stompClient.connect(stompUrl, new MySessionHandler());
        }catch (Exception e) {}
    }

    public class MySessionHandler extends StompSessionHandlerAdapter {

        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            // ...
            // http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#websocket-fallback-sockjs-client
            // Subscribe to events
        }
    }

}
