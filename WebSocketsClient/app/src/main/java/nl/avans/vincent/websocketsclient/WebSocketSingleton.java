package nl.avans.vincent.websocketsclient;

/**
 * Created by Vincent on 27-10-2015.
 */
public class WebSocketSingleton {

    private static WebSocket webSocket;

    public static WebSocket getWebSocket() {
        return WebSocketSingleton.webSocket;
    }

    public static void setWebSocket(WebSocket webSocketPass) {
        WebSocketSingleton.webSocket = webSocketPass;
    }

}
