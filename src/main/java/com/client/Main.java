package com.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    public static ClientConnection clientConnection;
   @Override
   public void start(Stage primaryStage) throws Exception {
       UtilsViews.parentContainer = new StackPane();
       UtilsViews.addView(Main.class, "Config", "/assets/viewConfig.fxml");
       UtilsViews.addView(Main.class, "Waiting", "/assets/viewWaiting.fxml");
       UtilsViews.addView(Main.class, "Countdown", "/assets/viewCountdown.fxml");
       UtilsViews.addView(Main.class, "Play", "/assets/viewPlay.fxml");
       UtilsViews.addView(Main.class, "Final", "/assets/viewFinal.fxml");
    
       ConfigController configController = (ConfigController) UtilsViews.getController("Config");
    //    Play playController = (Play) UtilsViews.getController("Play");
       configController.setStage(primaryStage);
   
       Scene scene = new Scene(UtilsViews.parentContainer);
       primaryStage.setTitle("Matrix Play");
       primaryStage.setScene(scene);
       

   
       UtilsViews.showView("Config", primaryStage);
       
       primaryStage.show();

       scene.setOnKeyPressed((keyEvent) -> {
        switch (keyEvent.getCode()) {
            case UP:
                clientConnection.sendMessage("movement", "up");;
                
                break;
                
            case DOWN:
                clientConnection.sendMessage("movement", "down");

                break;

            
            default:
                throw new AssertionError();
        }
       });

   }
       
         
   
   public static void main(String[] args) {
       launch(args);
   }
    
}
