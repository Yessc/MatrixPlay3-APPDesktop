package com.client;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;





public class CountdownController {

    private CountdownController instance;

    private Stage stage;

    @FXML
    private Label countdownLabel;

    private String nameUser; 
    @FXML
    public void initialize() {

    }

    public CountdownController() {
        this.instance = this;
    }

    public void start( int seconds, String playerName, String player2Name) {
        this.nameUser = playerName; 
        startCountdown(seconds);
    }

    private void startCountdown(int seconds) {
        countdownLabel.setText(String.valueOf(seconds));

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    int current = Integer.parseInt(countdownLabel.getText());
                    if (current > 0) {
                        countdownLabel.setText(String.valueOf(current - 1));
                    }
                    if (current == 0) {
                        UtilsViews.showView("Play", stage);
                        Play playController = (Play) UtilsViews.getController("Play");
                    }
                }));
        timeline.setCycleCount(seconds + 1);
        timeline.play();
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
