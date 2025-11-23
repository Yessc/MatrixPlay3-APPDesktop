package com.client;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class FinalController {
    

    @FXML
    private ImageView winnerImage;

    @FXML
    private Label winner;

    @FXML
    private Label winnerpoints;

    @FXML
    private ImageView looserImage;

    @FXML
    private Label looser;

    @FXML
    private Label looserpoints;

    private Stage stage;

    public void setWinnerData(String name, int points, String imagePath) {
        Platform.runLater(() -> {
            winner.setText(name);
            winnerpoints.setText("Points: " + points);
            winnerImage.setImage(new javafx.scene.image.Image(imagePath));
        });
    }

    public void setLooserData(String name, int points, String imagePath) {
        Platform.runLater(() -> {
            looser.setText(name);
            looserpoints.setText("Points: " + points);
            looserImage.setImage(new javafx.scene.image.Image(imagePath));
        });
    }
    
}
