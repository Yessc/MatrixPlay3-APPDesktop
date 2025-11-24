package com.client;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.Button;

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

    @FXML
    private Button returnButton; 

    @FXML
    private Button finishButton;

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
    
    public void initialize() {
        returnButton.setOnAction(event -> goBack()); //boton regreso a configuracion
        finishButton.setOnAction(event -> FinishGame()); //boton que cierra juego
    }


    private void FinishGame() {
        
        stage = (Stage) finishButton.getScene().getWindow();
        stage.close();
    }

    
    private void goBack() {
        //Stage stage = (Stage) returnButton.getScene().getWindow();
        UtilsViews.showView("Config", stage);
    }

   
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    /*public void updateFinalGame(String winnerName, String looserName, int winnerPoints, int looserPoints) {
    winner.setText(winnerName);
    looser.setText(looserName);
    winnerpoints.setText(String.valueOf(winnerPoints));
    looserpoints.setText(String.valueOf(looserPoints));

    String winnerImagePath = "/assets/images/win.png";
    String looserImagePath = "/assets/images/losser.png";

    winnerImage.setImage(new Image(winnerImagePath));
    looserImage.setImage(new Image(looserImagePath));
}*/
    
}
