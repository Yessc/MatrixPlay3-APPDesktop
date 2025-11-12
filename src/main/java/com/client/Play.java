package com.client;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Play {

    @FXML
    private Canvas gameCanvas;

    private String player1Name;
    private String player2Name;

    @FXML
    public void initialize() {
        drawGame();
    }
    public void setPlayerNames(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    // Este método dibuja todo en el Canvas
    public void drawGame() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();

        // Fondo
        gc.setFill(Color.web("#EFE8FA"));
        gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        // Barras de jugadores
        gc.setFill(Color.web("#FF5733"));
        gc.fillRect(10, 250, 20, 100);
        gc.setFill(Color.web("#33FF57"));
        gc.fillRect(970, 250, 20, 100);

        // Pelota
        gc.setFill(Color.web("#FF8C00"));
        gc.fillOval(490, 290, 20, 20);

        // Nombres
        gc.setFill(Color.web("#FF0000"));
        gc.setFont(javafx.scene.text.Font.font(20));
        gc.fillText(player1Name, 200, 50);

        gc.setFill(Color.web("#00FF00"));
        gc.fillText(player2Name, 800, 50);

        // Puntuaciones
        gc.setFill(Color.web("#FF0000"));
        gc.fillText("0", 200, 100);
        gc.setFill(Color.web("#00FF00"));
        gc.fillText("0", 800, 100);

        //gc.drawLine(50,0, 50, 0);
    }
}
