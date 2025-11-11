package com.client;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONObject;

import javafx.application.Platform;

public class ClientConnection extends WebSocketClient {

    public interface Listener {
        void onConnected();

        void onError(String msg);

        void onCountdown(int seconds);
        
        void onPlayer2Received(String name);
    }

    private Listener listener;
    private String player1Name;

    public ClientConnection(String url, String playerName, Listener listener) throws Exception {
        super(new URI(url));
        this.listener = listener;
        this.player1Name = playerName;
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        // Enviar logClient al servidor
        JSONObject log = new JSONObject();
        JSONObject inner = new JSONObject();
        inner.put("type","register");
        inner.put("clientName", player1Name);
        inner.put("clientType", "Desktop");
        //inner.put("avatar", "avatar1.png");
        log.put("logClient", inner);

        send(inner.toString());
    }

    @Override
    public void onMessage(String message) {
        Platform.runLater(() -> {
            try {
                JSONObject obj = new JSONObject(message);

                
                if (obj.has("countdown")) {
                    int seconds = obj.getInt("countdown");
                    listener.onCountdown(seconds);
                }

                if (obj.has("clientsList")) {
                    JSONArray clients = obj.getJSONArray("clientsList");
                    for (int i = 0; i < clients.length(); i++) {
                        JSONObject client = clients.getJSONObject(i);
                        String name = client.getString("clientName");
                        if (!name.equals(player1Name)) {
                            listener.onPlayer2Received(name);
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onError(Exception ex) {
        Platform.runLater(() -> listener.onError(ex.getMessage()));
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
    }
}
