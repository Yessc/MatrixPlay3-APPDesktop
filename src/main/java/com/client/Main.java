package com.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        UtilsViews.parentContainer = new StackPane();
        UtilsViews.addView(Main.class, "Config", "/assets/viewConfig.fxml");
        UtilsViews.addView(Main.class, "Waiting", "/assets/viewWaiting.fxml");
        UtilsViews.addView(Main.class, "Countdown", "/assets/viewCountdown.fxml");
        UtilsViews.addView(Main.class, "Play", "/assets/viewPlay.fxml");
     
        ConfigController configController = (ConfigController) UtilsViews.getController("Config");
        configController.setStage(primaryStage);

        Scene scene = new Scene(UtilsViews.parentContainer);
        primaryStage.setTitle("Matrix Play");
        primaryStage.setScene(scene);
        
        

        UtilsViews.showView("Config", primaryStage);
        
        primaryStage.show();

        
       

    }


    public static void main(String[] args) {
        launch(args);
    }
}
