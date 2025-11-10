package com.client;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class CountdownController {

    private static CountdownController instance;

    @FXML
    private Label countdownLabel;

    public CountdownController() {
        instance = this;
    }

    public static void start(int seconds) {
        if (instance != null) {
            instance.startCountdown(seconds);
        }
    }

    private void startCountdown(int seconds) {

        countdownLabel.setText(String.valueOf(seconds));

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    int current = Integer.parseInt(countdownLabel.getText());
                    if (current > 1) {
                        countdownLabel.setText(String.valueOf(current - 1));
                    }
                }));
        timeline.setCycleCount(seconds);
        timeline.play();
    }
}
