package com.client;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class UtilsViews {

    public static StackPane parentContainer = new StackPane();
    public static ArrayList<Object> controllers = new ArrayList<>();
    public static String currentView;

    
    public static void addView(Class<?> cls, String name, String path) throws Exception {
        boolean defaultView = false;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(cls.getResource(path));

        if (loader.getLocation() == null) {
            throw new RuntimeException("FXML file not found: " + path);
        }

        Pane view = loader.load();
        ObservableList<Node> children = parentContainer.getChildren();

      
        if (children.isEmpty()) {
            defaultView = true;
            currentView = name;
        }

        view.setId(name);
        view.setVisible(defaultView);
        view.setManaged(defaultView);

        children.add(view);
        controllers.add(loader.getController());
    }

  
    public static void showView(String name, Stage stage) {
        ObservableList<Node> children = parentContainer.getChildren();

        for (Node child : children) {
            if (child.getId().equals(name)) {
                child.setVisible(true);
                child.setManaged(true);
                currentView = name;

                switch (name) {
                    case "Countdown", "Config", "Waiting" -> {
                        stage.setWidth(600);
                        stage.setHeight(600);
                    }
                    case "Play" -> {
                        stage.setWidth(1000);
                        stage.setHeight(600);
                    }
                }

            } else {
                child.setVisible(false);
                child.setManaged(false);
            }
        }
    }
    
    public static Object getController(String viewName) {
        ObservableList<Node> children = parentContainer.getChildren();
        for (int i = 0; i < children.size(); i++) {
            Node child = children.get(i);
            if (child.getId().equals(viewName)) {
                return controllers.get(i);
            }
        }
        return null;
    }

}
