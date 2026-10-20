package com.example.miniproject2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
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
    @FXML
    private Button searchButton;
    @FXML
    private Button backButton;

    private ObservableList<Order> orderList = FXCollections.observableArrayList();
    private FilteredList<Order> filteredList;

    @FXML
    public void initialize() {
        // Map table columns to Order properties
        orderIdColumn.setCellValueFactory(cellData -> cellData.getValue().orderIdProperty().asObject());
        customerNameColumn.setCellValueFactory(cellData -> cellData.getValue().customerNameProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        totalColumn.setCellValueFactory(cellData -> cellData.getValue().totalProperty().asObject());

        // The table shows a filtered view of the order list (used by search)
        filteredList = new FilteredList<>(orderList, o -> true);
        ordersTable.setItems(filteredList);

        // Search updates live while typing
        searchField.textProperty().addListener((obs, oldText, newText) -> applySearch());

        // Handle row selection
        ordersTable.setOnMouseClicked(this::onRowSelect);
    }

    // Handles the Search button
    @FXML
    private void handleSearch() {
        applySearch();
    }

    // Filters the table by ID, customer name, date or total
    private void applySearch() {
        String text = searchField.getText() == null ? "" : searchField.getText().trim().toLowerCase();
        filteredList.setPredicate(order -> {
            if (text.isEmpty()) {
                return true;
            }
            return String.valueOf(order.getOrderId()).contains(text)
                    || (order.getCustomerName() != null && order.getCustomerName().toLowerCase().contains(text))
                    || (order.getDate() != null && order.getDate().toString().contains(text))
                    || String.valueOf(order.getTotal()).contains(text);
        });
    }

    // Handles the Back button: returns to the Home screen
    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeView.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Home View");
        stage.show();
    }

    // Checks all fields. 'ignore' is the order being edited (null when adding),
    // so it is not counted as a duplicate of itself.
    private boolean validateInput(Order ignore) {
        String idText = orderIdField.getText().trim();
        String customer = customerNameField.getText().trim();
        String totalText = totalField.getText().trim();

        if (idText.isEmpty() || customer.isEmpty() || dateField.getValue() == null || totalText.isEmpty()) {
            showAlert("Missing Information", "Please fill in all fields.");
            return false;
        }

        int id;
        try {
            id = Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Order ID must be a whole number.");
            return false;
        }
        if (id <= 0) {
            showAlert("Invalid Input", "Order ID must be greater than 0.");
            return false;
        }

        double total;
        try {
            total = Double.parseDouble(totalText);
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Total must be a number.");
            return false;
        }
        if (Double.isNaN(total) || Double.isInfinite(total) || total < 0) {
            showAlert("Invalid Input", "Total must be a valid number that is not negative.");
            return false;
        }

        for (Order o : orderList) {
            if (o != ignore && o.getOrderId() == id) {
                showAlert("Duplicate ID", "An order with ID " + id + " already exists.");
                return false;
            }
        }
        return true;
    }

    // Handles adding an order
    @FXML
    private void addOrder() {
        if (!validateInput(null)) {
            return;
        }
        int id = Integer.parseInt(orderIdField.getText().trim());
        String customer = customerNameField.getText().trim();
        LocalDate date = dateField.getValue();
        double total = Double.parseDouble(totalField.getText().trim());

        orderList.add(new Order(id, customer, date, total));
        clearFields();
    }

    // Handles updating an order
    @FXML
    private void updateOrder() {
        Order selectedOrder = ordersTable.getSelectionModel().getSelectedItem();
        if (selectedOrder == null) {
            showAlert("No Selection", "Please select an order to update.");
            return;
        }
        if (!validateInput(selectedOrder)) {
            return;
        }
        selectedOrder.setOrderId(Integer.parseInt(orderIdField.getText().trim()));
        selectedOrder.setCustomerName(customerNameField.getText().trim());
        selectedOrder.setDate(dateField.getValue());
        selectedOrder.setTotal(Double.parseDouble(totalField.getText().trim()));

        ordersTable.refresh();
        applySearch(); // re-apply the filter in case the edited order no longer matches
        clearFields();
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

    // Clears input fields and the table selection
    @FXML
    private void clearFields() {
        orderIdField.clear();
        customerNameField.clear();
        dateField.setValue(null);
        totalField.clear();
        ordersTable.getSelectionModel().clearSelection();
    }

    // Handles row clicks: fills the fields, or clears everything when an empty area is clicked
    private void onRowSelect(MouseEvent event) {
        Node node = event.getPickResult().getIntersectedNode();
        while (node != null && !(node instanceof TableRow)) {
            node = node.getParent();
        }
        if (node != null && ((TableRow<?>) node).isEmpty()) {
            clearFields();
            return;
        }

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
