package com.example.miniproject2;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;

public class HomeController {

    @FXML
    private void handleManageEntities() {
        System.out.println("Navigating to Business Entities Management...");
        // Load Business Entities Management view here
    }

    @FXML
    private void handleEmployeesEntities(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("employee.fxml"));
        Parent root = loader.load();

        // Get the current stage and set the new scene
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Employee Management");
        stage.show();
    }

    @FXML
    private void handleInventoryEntities(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("inventory.fxml"));
        Parent root = loader.load();

        // Get the current stage and set the new scene
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Inventory Management");
        stage.show();
    }

    @FXML
    private void handleOrdersEntities(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("order-view.fxml"));
        Parent root = loader.load();

        // Get the current stage and set the new scene
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Order Management");
        stage.show();
    }

    @FXML
    private void handleSupplierEntities(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("supplier-view.fxml"));
        Parent root = loader.load();

        // Get the current stage and set the new scene
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Supplier Management");
        stage.show();
    }

    @FXML
    private void handleLogout(ActionEvent event) throws IOException {
        System.out.println("Logging out...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sign-in.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Sign In");
        stage.show();
    }

    @FXML
    private void handlePersonEntities(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("persons-view.fxml"));
        Parent root = loader.load();

        // Get the current stage and set the new scene
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Customer Management");
        stage.show();
    }

}
