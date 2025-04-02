package com.example.miniproject2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;

public class OrdersController {

    @FXML
    private TableView<Order> ordersTable;
    @FXML
    private TableColumn<Order, Integer> orderIdColumn;
    @FXML
    private TableColumn<Order, String> customerNameColumn;
    @FXML
    private TableColumn<Order, LocalDate> dateColumn;
    @FXML
    private TableColumn<Order, Double> totalColumn;

    @FXML
    private TextField orderIdField;
    @FXML
    private TextField customerNameField;
    @FXML
    private DatePicker dateField;
    @FXML
    private TextField totalField;
    @FXML
    private TextField searchField;

    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button clearButton;

    private ObservableList<Order> orderList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Map table columns to Order properties
        orderIdColumn.setCellValueFactory(cellData -> cellData.getValue().orderIdProperty().asObject());
        customerNameColumn.setCellValueFactory(cellData -> cellData.getValue().customerNameProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        totalColumn.setCellValueFactory(cellData -> cellData.getValue().totalProperty().asObject());

        // Set data in the table
        ordersTable.setItems(orderList);

        // Handle row selection
        ordersTable.setOnMouseClicked(this::onRowSelect);
    }

    // Handles adding an order
    @FXML
    private void addOrder() {
        try {
            int id = Integer.parseInt(orderIdField.getText());
            String customer = customerNameField.getText();
            LocalDate date = dateField.getValue();
            double total = Double.parseDouble(totalField.getText());

            Order order = new Order(id, customer, date, total);
            orderList.add(order);
            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter valid values.");
        }
    }

    // Handles updating an order
    @FXML
    private void updateOrder() {
        Order selectedOrder = ordersTable.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            try {
                selectedOrder.setOrderId(Integer.parseInt(orderIdField.getText()));
                selectedOrder.setCustomerName(customerNameField.getText());
                selectedOrder.setDate(dateField.getValue());
                selectedOrder.setTotal(Double.parseDouble(totalField.getText()));
                ordersTable.refresh();
                clearFields();
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Please enter valid values.");
            }
        } else {
            showAlert("No Selection", "Please select an order to update.");
        }
    }

    // Handles deleting an order
    @FXML
    private void deleteOrder() {
        Order selectedOrder = ordersTable.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            orderList.remove(selectedOrder);
            clearFields();
        } else {
            showAlert("No Selection", "Please select an order to delete.");
        }
    }

    // Clears input fields
    @FXML
    private void clearFields() {
        orderIdField.clear();
        customerNameField.clear();
        dateField.setValue(null);
        totalField.clear();
    }

    // Handles row selection and fills the fields
    private void onRowSelect(MouseEvent event) {
        Order selectedOrder = ordersTable.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            orderIdField.setText(String.valueOf(selectedOrder.getOrderId()));
            customerNameField.setText(selectedOrder.getCustomerName());
            dateField.setValue(selectedOrder.getDate());
            totalField.setText(String.valueOf(selectedOrder.getTotal()));
        }
    }

    // Displays an alert
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
