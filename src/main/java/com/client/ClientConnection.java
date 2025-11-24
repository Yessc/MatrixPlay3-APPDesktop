package com.client;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONObject;

import javafx.application.Platform;
import javafx.stage.Stage;

public class ClientConnection extends WebSocketClient {

    public interface Listener {
        void onConnected();

        void onError(String msg);

        void onCountdown(int seconds);
        
        void onPlayer2Received(String name);
    }

    private Listener listener;
    private String player1Name;
    private int goalScored;
    private Stage stage;

    public ClientConnection(String url, String playerName, Listener listener) throws Exception {
        super(new URI(url));
        this.listener = listener;
        this.player1Name = playerName;
        this.goalScored = goalScored;
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        // Enviar logClient al servidor
        JSONObject log = new JSONObject();
        JSONObject inner = new JSONObject();
        inner.put("type","register");
        inner.put("clientName", player1Name);
        inner.put("clientType", "Desktop");

        log.put("logClient", inner);

        send(inner.toString());

        Main.clientConnection = this;
    }

    public void sendMessage(String type, String message) {
        JSONObject inner = new JSONObject();
        inner.put("type", type);
        inner.put("message", message);
        inner.put("clientName", player1Name);
        inner.put("goalScored", goalScored);

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
                    String player1 = obj.getString("player1Name");
                    String player2 = obj.getString("player2Name");
                    System.out.println("Countdown received: " + seconds);
                    Play playController = (Play) UtilsViews.getController("Play");
                    playController.setPlayerNames(player1, player2);
                    // listener.onCountdown(seconds);
                    CountdownController countdownController = (CountdownController) UtilsViews.getController("Countdown");
                    countdownController.changeCountdownLabel(seconds);
                }

                if (obj.has("clientsList")) {
                    String[] arrayNames = new String[2];

                    JSONArray clients = obj.getJSONArray("clientsList");
                    ConfigWaiting configWaiting = (ConfigWaiting) UtilsViews.getController("Waiting");
                    for (int i = 0; i < clients.length(); i++) {
                        JSONObject client = clients.getJSONObject(i);
                        String name = client.getString("clientName");
                        // if (!name.equals(player1Name)) {
                        //     listener.onPlayer2Received(name);
                        // }
                        
                        if (i < 2) {
                            arrayNames[i] = name;
                        }

                    }
                    if (arrayNames[0] != null && arrayNames[1] != null){
                        configWaiting.setPlayerNames(arrayNames[0], arrayNames[1]);
                    }
                    

                    
                    
                }

                if (obj.getString("type").equals("initialPosition")) {
                    System.out.println("Initial position received");
                    // JSONObject position = obj.getJSONObject("initialPosition");

                    double playerWidth = Double.parseDouble(obj.getString("playersSize").split(" ")[0]);
                    double playerHeight = Double.parseDouble(obj.getString("playersSize").split(" ")[1]);

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
                    playController.player_width = playerWidth * Play.res;
                    playController.player_height = playerHeight * Play.res;
                    System.out.println("Player 1 position: " + playController.player1X + ", " + playController.player1Y);
                    System.out.println("Player 2 position: " + playController.player2X + ", " + playController.player2Y);

                    double ballxRaw = Double.parseDouble(obj.getString("ball").split(" ")[0]);
                    double ballyRaw = Double.parseDouble(obj.getString("ball").split(" ")[1]);
                    double[] ballPos = Play.denormalizePosition(ballxRaw, ballyRaw);

                    double ballRadiusRaw = obj.getDouble("ballRadius");
                    playController.ballRadius = ballRadiusRaw * Play.res;

                    playController.ballX = ballPos[0] - playController.ballRadius / 2;
                    playController.ballY = ballPos[1] - playController.ballRadius / 2;
                    playController.drawGame();
                    
                    // Update player positions
                }


                if (obj.getString("type").equals("playerPosition")) {
                    System.out.println("Player position received");
                    // JSONObject position = obj.getJSONObject("initialPosition");

                    double posxRaw = Double.parseDouble(obj.getString("position").split(" ")[0]);
                    double posyRaw = Double.parseDouble(obj.getString("position").split(" ")[1]);
                    String player = obj.getString("playerName");

                    
                    Play playController = (Play) UtilsViews.getController("Play");
                    
                    if (playController.player1Name.equals(player)) {
                        // playController.player1X = Play.denormalizePosition(posxRaw, posyRaw)[0];
                        playController.player1Y = Play.denormalizePosition(posxRaw, posyRaw)[1];
                        System.out.println("Updating player 1 position");
                    }
                    else {
                        // playController.player2X = Play.denormalizePosition(posxRaw, posyRaw)[0];
                        playController.player2Y = Play.denormalizePosition(posxRaw, posyRaw)[1];
                        System.out.println("Updating player 2 position");
                    }
                    
                    playController.drawGame();
                    
                    // Update player positions
                }

                if (obj.getString("type").equals("ballPosition")) {
                    System.out.println("Ball position received");
                    // JSONObject position = obj.getJSONObject("initialPosition");

                    double posxRaw = Double.parseDouble(obj.getString("position").split(" ")[0]);
                    double posyRaw = Double.parseDouble(obj.getString("position").split(" ")[1]);

                    
                    Play playController = (Play) UtilsViews.getController("Play");
                    
                    playController.ballX = Play.denormalizePosition(posxRaw, posyRaw)[0] - playController.ballRadius / 2;
                    playController.ballY = Play.denormalizePosition(posxRaw, posyRaw)[1] - playController.ballRadius / 2;
                    
                    playController.drawGame();
                }

                if (obj.getString("type").equals("goalScored")) {
                    String name = obj.getString("playerName");
                    Play playController = (Play) UtilsViews.getController("Play");
                    playController.updateScore(name);
                }

                if (obj.getString("type").equals("gameOver")) {
                    String winner = obj.getString("winner");
                    String loser = obj.getString("loser");
                    int scoreWinner = obj.getInt("scoreP1");
                    int scoreLoser = obj.getInt("scoreP2");

                    FinalController finalController = (FinalController) UtilsViews.getController("Final");
                    finalController.setStage(stage);
                    finalController.updateFinalGame(winner, loser, scoreWinner, scoreLoser);
                    UtilsViews.showView("Final", stage);

                    
       
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
