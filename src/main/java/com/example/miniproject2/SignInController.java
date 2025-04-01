package com.example.miniproject2;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignInController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button signInButton;

    @FXML
    private void handleSignIn() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.equals("admin") && password.equals("password")
                || username.equals("admin1") && password.equals("password1")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) signInButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Home Page");
            stage.show();
        } else {
            System.out.println("Invalid Credentials");
        }
    }

}