package com.client;

import java.net.URI;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import javafx.application.Platform;

public class ClientConnection extends WebSocketClient {

    public interface Listener {
        void onConnected();

        void onError(String msg);

        void onCountdown(int seconds);
    }

    private Listener listener;

    public ClientConnection(String url, Listener listener) throws Exception {
        super(new URI(url));
        this.listener = listener;
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        Platform.runLater(() -> listener.onConnected());
    }

    @Override
    public void onMessage(String message) {


        if (message.contains("\"countdown\"")) {
            int seconds = Integer.parseInt(message.replaceAll("\\D+", ""));
            Platform.runLater(() -> listener.onCountdown(seconds));
        }
    }

    @Override
    public void onError(Exception ex) {
        Platform.runLater(() -> listener.onError(ex.getMessage()));
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
    }
}
