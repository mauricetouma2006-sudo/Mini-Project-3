package com.example.miniproject2;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private Label errorLabel;

    private final AccountsStore accountsStore = new AccountsStore();

    @FXML
    private void handleSignIn() throws IOException {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        // Empty fields: tell the user instead of failing silently
        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please enter your username and password.");
            return;
        }

        boolean valid;
        try {
            // Check the credentials against the Accounts table (passwords are stored as SHA-256 hashes)
            valid = accountsStore.checkLogin(username, password);
        } catch (java.sql.SQLException e) {
            errorLabel.setText("Cannot connect to the database. Is MySQL running?");
            System.out.println(e);
            return;
        }

        if (valid) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) signInButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Home Page");
            stage.show();
        } else {
            // Show the error on screen (before it was only printed in the console)
            errorLabel.setText("Invalid username or password.");
            passwordField.clear();
        }
    }

}
