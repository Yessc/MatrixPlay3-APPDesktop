package com.client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ConfigWaiting {

    @FXML
    private ImageView player1Image;

    @FXML
    private ImageView player2Image;

    @FXML
    private Label player1Name;

    @FXML
    private Label player2Name;

    private String p1 = "";
    private String p2 = "";

    @FXML
    public void initialize() {
    
    }

    public void setPlayerNames(String p1, String p2) {
        if (player1Name != null)
            player1Name.setText(p1);

        if (player2Name != null)
            player2Name.setText(p2);
    }
    
    public void setSecondPlayer(String name) {
        if (player2Name != null)
            player2Name.setText(name);
    }
}
