package com.example.miniproject2;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomeController {

    @FXML
    private void handleManageEntities() {
        System.out.println("Navigating to Business Entities Management...");
        // Load Business Entities Management view here
    }

    @FXML
    private void handleLogout(javafx.event.ActionEvent event) throws IOException {
        // Load the Sign-In view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sign-in.fxml"));
        Parent root = loader.load();

        // Get the current stage and set the new scene
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Sign In");
        stage.show();
        stage.setResizable(false);
    }
}
