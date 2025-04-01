package com.example.miniproject2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class InventoryController {

    @FXML
    private TableView<Item> inventoryTable;
    @FXML
    private TableColumn<Item, Integer> idColumn;
    @FXML
    private TableColumn<Item, String> nameColumn;
    @FXML
    private TableColumn<Item, Integer> quantityColumn;
    @FXML
    private TableColumn<Item, Double> costColumn;
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField costField;
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;

    private final ObservableList<Item> inventory = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set up table columns
        idColumn.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        nameColumn.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));
        quantityColumn.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getQuantity())
                        .asObject());
        costColumn.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getCost()).asObject());

        inventoryTable.setItems(inventory);

        // Handle table row selection
        inventoryTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                idField.setText(String.valueOf(newSelection.getId()));
                nameField.setText(newSelection.getName());
                quantityField.setText(String.valueOf(newSelection.getQuantity()));
                costField.setText(String.valueOf(newSelection.getCost()));
            }
        });
    }

    @FXML
    private void addItem() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            double cost = Double.parseDouble(costField.getText());

            inventory.add(new Item(id, name, quantity, cost));
            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Invalid input", "Please enter valid data.");
        }
    }

    @FXML
    private void updateItem() {
        Item selectedItem = inventoryTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                selectedItem.setId(Integer.parseInt(idField.getText()));
                selectedItem.setName(nameField.getText());
                selectedItem.setQuantity(Integer.parseInt(quantityField.getText()));
                selectedItem.setCost(Double.parseDouble(costField.getText()));
                inventoryTable.refresh();
                clearFields();
            } catch (NumberFormatException e) {
                showAlert("Invalid input", "Please enter valid data.");
            }
        } else {
            showAlert("No Selection", "Please select an item to update.");
        }
    }

    @FXML
    private void removeItem() {
        Item selectedItem = inventoryTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            inventory.remove(selectedItem);
            clearFields();
        } else {
            showAlert("No Selection", "Please select an item to delete.");
        }
    }

    private void clearFields() {
        idField.clear();
        nameField.clear();
        quantityField.clear();
        costField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
