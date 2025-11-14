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

                System.out.println("Message received: " + message);
                if (obj.getString("type").equals("countdown")) {
                    
                    int seconds = obj.getInt("value");
                    System.out.println("Countdown received: " + seconds);
                    // listener.onCountdown(seconds);
                    CountdownController countdownController = (CountdownController) UtilsViews.getController("Countdown");
                    countdownController.changeCountdownLabel(seconds);
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

                if (obj.getString("type").equals("initialPosition")) {
                    System.out.println("Initial position received");
                    // JSONObject position = obj.getJSONObject("initialPosition");

                    double p1xRaw = Double.parseDouble(obj.getString("p1").split(" ")[0]);
                    double p1yRaw = Double.parseDouble(obj.getString("p1").split(" ")[1]);
                    double p2xRaw = Double.parseDouble(obj.getString("p2").split(" ")[0]);
                    double p2yRaw = Double.parseDouble(obj.getString("p2").split(" ")[1]);

                    double[] p1Pos = Play.denormalizePosition(p1xRaw, p1yRaw);
                    double[] p2Pos = Play.denormalizePosition(p2xRaw, p2yRaw);
                    Play playController = (Play) UtilsViews.getController("Play");
                    playController.player1X = p1Pos[0];
                    playController.player1Y = p1Pos[1];
                    playController.player2X = p2Pos[0];
                    playController.player2Y = p2Pos[1];
                    System.out.println("Player 1 position: " + playController.player1X + ", " + playController.player1Y);
                    System.out.println("Player 2 position: " + playController.player2X + ", " + playController.player2Y);
                    playController.drawGame();
                    
                    // Update player positions
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
