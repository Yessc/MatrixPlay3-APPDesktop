package com.client;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConfigController {

    private Stage stage;

    private ConfigData config;
  

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
        ConfigData.clear();
        config = ConfigData.load();
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

        config.setPlayerName(nameUser);
        config.setServerURL(urlServer);
        config.save();

        ConfigWaiting waitingController = (ConfigWaiting) UtilsViews.getController("Waiting");//recordar eliminar cuando pruebe la conexion
        waitingController.setPlayerNames(nameUser, "Player 2");
        UtilsViews.showView("Waiting", stage);
        
        /*ConfigWaiting waitingController = (ConfigWaiting) UtilsViews.getController("Waiting");

       waitingController.setStage(stage);
        waitingController.startClient(urlServer, nameUser);
        UtilsViews.showView("Waiting", stage);*/
        
    }
}
