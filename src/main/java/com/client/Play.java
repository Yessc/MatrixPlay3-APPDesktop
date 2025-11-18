package com.client;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Play {

    public static final int res = 576;

    @FXML
    private Canvas gameCanvas;

    public String player1Name;
    public String player2Name;
    
    public double player1X = 10;;
    public double player1Y = 250;
    public double player2X = 970;;
    public double player2Y = 250;
    public double ballX = 490;;
    public double ballY = 290;
    public double ballRadius = 5;

    public double player_width = 20;
    public double player_height = 100;

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
        gc.fillRect(player1X, player1Y - player_height/2, player_width, player_height);
        gc.setFill(Color.web("#33FF57"));
        gc.fillRect(player2X - player_width, player2Y - player_height/2, player_width, player_height);

        // Pelota
        gc.setFill(Color.web("#FF8C00"));
        gc.fillOval(ballX, ballY, ballRadius, ballRadius);

        // Nombres
        gc.setFill(Color.web("#FF0000"));
        gc.setFont(javafx.scene.text.Font.font(20));
        gc.fillText(player1Name, 160, 50);

        gc.setFill(Color.web("#00FF00"));
        gc.fillText(player2Name, res - 180, 50);

        // Puntuaciones
        gc.setFill(Color.web("#FF0000"));
        gc.fillText("0", 160, 100);
        gc.setFill(Color.web("#00FF00"));
        gc.fillText("0", res - 180, 100);

        //gc.drawLine(50,0, 50, 0);
    }

    public static double[] denormalizePosition(double normX, double normY) {
        double x = normX * res;
        double y = normY * res;
        return new double[]{x, y};
    }
}
