package com.client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class ConfigWaiting {

    @FXML
    private ImageView player1Image;

    @FXML
    private ImageView player2Image;

    @FXML
    private Label player1Name;

    @FXML
    private Label player2Name;

    private String player1; 
    private String player2;
    private Stage stage;

    private ClientConnection client;

    @FXML
    public void initialize() {

    }
    
    public void startClient(String url, String playerName) {
        this.player1 = playerName;
        if (player1Name != null)
            player1Name.setText(playerName);

        try {
            client = new ClientConnection(url, playerName, new ClientConnection.Listener() {
                @Override
                public void onConnected() {
                    System.out.println("Connected to server");
                }

                @Override
                public void onError(String msg) {
                    System.err.println("Error: " + msg);
                }

                @Override
                public void onCountdown(int seconds) {
                }

                @Override
                public void onPlayer2Received(String name) {
                    setSecondPlayer(name);
                }
            });
            client.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
   
    public void setPlayerNames(String p1, String p2) {
        if (player1Name != null)
            player1Name.setText(p1);

        if (player2Name != null)
            player2Name.setText(p2);
            checkPlayersReady();
    }
    
    
    
    public void setSecondPlayer(String name) {
        if (player2Name != null) {
            player2Name.setText(name);
            this.player2 = name;
            checkPlayersReady();
        }
    }
    
    public boolean areBothPlayersReady() {
        return player1Name != null && !player1Name.getText().isEmpty()
                && player2Name != null && !player2Name.getText().isEmpty();
    }
    
    private void checkPlayersReady() {
        if (areBothPlayersReady()) {

            System.out.println("¡Two players Ready!");

            UtilsViews.showView("Countdown", stage);
            CountdownController countdownController = (CountdownController) UtilsViews.getController("Countdown");
            countdownController.setStage(stage);
            //countdownController.start(5, player1Name.getText(), player2Name.getText());

            countdownController.start(3, "Jugador 1", "Jugador 2");
        }
    }
    
    
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
