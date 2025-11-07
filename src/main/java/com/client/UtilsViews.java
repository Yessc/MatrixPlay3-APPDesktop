package com.client;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class UtilsViews {

    public static StackPane parentContainer = new StackPane();
    public static ArrayList<Object> controllers = new ArrayList<>();

    // Add one view to the list
    public static void addView(Class<?> cls, String name, String path) throws Exception {
        
        boolean defaultView = false;
        FXMLLoader loader = new FXMLLoader(cls.getResource(path));
        Pane view = loader.load();
        ObservableList<Node> children = parentContainer.getChildren();

        // First view is the default view
        if (children.isEmpty()) {
            defaultView = true;
        }

        view.setId(name);
        view.setVisible(defaultView);
        view.setManaged(defaultView);

        children.add(view);
        controllers.add(loader.getController());
    }

   
   // Show a view by its name
    public static void showView(String name) {
        ObservableList<Node> children = parentContainer.getChildren();

        for (Node child : children) {
            if (child.getId().equals(name)) {
                child.setVisible(true);
                child.setManaged(true);
            } else {
                child.setVisible(false);
                child.setManaged(false);
            }
        }
    }
}
