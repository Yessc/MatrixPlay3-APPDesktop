package com.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ConfigController {

    @FXML
    private TextField name;

    @FXML
    private TextField url;

    @FXML
    private Button connect;

    @FXML
    private Label errorLabel;

    public void initialize() {
    }

    @FXML
    private void connectToServer() {
       /*  String nameUser = name.getText();
        String urlServer = url.getText();

        if (nameUser.isEmpty() || urlServer.isEmpty()) {
            errorLabel.setText("All fields are required");
        } else {
            errorLabel.setText("");

            try {
                System.out.println( urlServer);//Conectado al servidor: " 
            } catch (Exception e) {
                errorLabel.setText();//"Error: Could not connect to the server."
            }
        }
    }*/
}
