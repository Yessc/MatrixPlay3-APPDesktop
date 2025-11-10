package com.client;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConfigController {

    private Stage stage;
    private ClientConnection connection;

    @FXML
    private TextField name;

    @FXML
    private TextField url;

    @FXML
    private Button connect;

    @FXML
    private Label errorLabel;

    @FXML
    public void initialize() {
        ConfigData config = ConfigData.load();
        name.setText(config.getPlayerName());
        url.setText(config.getServerURL());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void connectToServer() {

        String nameUser = name.getText();
        String urlServer = url.getText();


        if (nameUser.isEmpty() || urlServer.isEmpty()) {
            errorLabel.setText("All fields are required");
            return;
        }


        try {
            ConfigData config = new ConfigData();
            config.setPlayerName(nameUser);
            config.setServerURL(urlServer);
            config.save();
        } catch (IOException e) {
            errorLabel.setText("Error saving configuration");
            return;
        }
        ConfigWaiting waitingController = (ConfigWaiting) UtilsViews.getController("Waiting");
        //waitingController.setPlayerNames(nameUser, "Player 2"); 
        UtilsViews.showView("Waiting", stage);

       /* try {
            connection = new ClientConnection(urlServer, new ClientConnection.Listener() {

                @Override
                public void onConnected() {
                    // Enviar datos al servidor
                    String json = "{\"type\":\"join\",\"player\":\"" + nameUser + "\"}";
                    connection.send(json);

                   
                    ConfigWaiting w = (ConfigWaiting) UtilsViews.getController("Waiting");
                    w.setPlayerNames(nameUser, "...");
                    UtilsViews.showView("Waiting", stage);
                }

                @Override
                public void onError(String msg) {
                    errorLabel.setText("Cannot connect to server");
                }

                @Override
                public void onCountdown(int seconds) {
                    UtilsViews.showView("Countdown", stage);
                    CountdownController.start(seconds);
                }
            });

            connection.connect();

        } catch (Exception e) {
            errorLabel.setText("Invalid server URL");
        }*/
    }
}
