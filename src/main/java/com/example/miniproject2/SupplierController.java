package com.example.miniproject2;

import java.io.IOException;

import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * CONTROLLER for supplier-view.fxml.
 * Connects the View (the screen) to the Model (Supplier + SupplierStore)
 * and handles every button click: Add, Update, Delete, Clear, Back, plus the search box.
 */
public class SupplierController {

    // Text fields (names must match fx:id in supplier-view.fxml)
    @FXML
    private TextField idFld;
    @FXML
    private TextField companyFld;
    @FXML
    private TextField phoneFld;
    @FXML
    private TextField emailFld;
    @FXML
    private TextField productFld;
    @FXML
    private TextField searchFld;

    // Table and its columns
    @FXML
    private TableView<Supplier> suppliersTable;
    @FXML
    private TableColumn<Supplier, Integer> idCol;
    @FXML
    private TableColumn<Supplier, String> companyCol;
    @FXML
    private TableColumn<Supplier, String> phoneCol;
    @FXML
    private TableColumn<Supplier, String> emailCol;
    @FXML
    private TableColumn<Supplier, String> productCol;

    // Shows errors or success messages
    @FXML
    private Label messageLbl;

    private final SupplierStore store = new SupplierStore();
    private FilteredList<Supplier> filteredSuppliers;

    // Runs automatically after the FXML is loaded
    @FXML
    public void initialize() {
        // Tell each column which property of Supplier to show
        idCol.setCellValueFactory(cell -> cell.getValue().supplierIdProperty().asObject());
        companyCol.setCellValueFactory(cell -> cell.getValue().companyNameProperty());
        phoneCol.setCellValueFactory(cell -> cell.getValue().phoneProperty());
        emailCol.setCellValueFactory(cell -> cell.getValue().emailProperty());
        productCol.setCellValueFactory(cell -> cell.getValue().productProperty());

        // The table shows a filtered view of the store, so the search box can hide rows
        filteredSuppliers = new FilteredList<>(store.getSuppliers(), s -> true);
        suppliersTable.setItems(filteredSuppliers);

        // When a row is clicked, copy its values into the text fields
        suppliersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, selected) -> {
            if (selected != null) {
                idFld.setText(String.valueOf(selected.getSupplierId()));
                companyFld.setText(selected.getCompanyName());
                phoneFld.setText(selected.getPhone());
                emailFld.setText(selected.getEmail());
                productFld.setText(selected.getProduct());
                messageLbl.setText("");
            }
        });

        // Every time the search text changes, re-filter the table
        searchFld.textProperty().addListener((obs, oldText, newText) -> {
            String text = newText.trim().toLowerCase();
            filteredSuppliers.setPredicate(s -> text.isEmpty()
                    || s.getCompanyName().toLowerCase().contains(text)
                    || s.getProduct().toLowerCase().contains(text));
        });
    }

    // CREATE
    @FXML
    private void addSupplier(ActionEvent event) {
        String error = validate(null);
        if (!error.isEmpty()) {
            showError(error);
            return;
        }
        store.addSupplier(new Supplier(Integer.parseInt(idFld.getText().trim()), companyFld.getText().trim(),
                phoneFld.getText().trim(), emailFld.getText().trim(), productFld.getText().trim()));
        clearFields();
        showSuccess("Supplier added.");
    }

    // UPDATE
    @FXML
    private void updateSupplier(ActionEvent event) {
        Supplier selected = suppliersTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Please select a supplier to update.");
            return;
        }
        String error = validate(selected);
        if (!error.isEmpty()) {
            showError(error);
            return;
        }
        store.updateSupplier(selected, Integer.parseInt(idFld.getText().trim()), companyFld.getText().trim(),
                phoneFld.getText().trim(), emailFld.getText().trim(), productFld.getText().trim());
        suppliersTable.refresh();
        clearFields();
        showSuccess("Supplier updated.");
    }

    // DELETE
    @FXML
    private void deleteSupplier(ActionEvent event) {
        Supplier selected = suppliersTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Please select a supplier to delete.");
            return;
        }
        store.deleteSupplier(selected);
        clearFields();
        showSuccess("Supplier deleted.");
    }

    @FXML
    private void clearForm(ActionEvent event) {
        clearFields();
        messageLbl.setText("");
    }

    // Go back to the Home page
    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeView.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Home Page");
        stage.show();
    }

    /**
     * Checks every field and returns all problems found ("" means everything is valid).
     * "editing" is the supplier being updated (null when adding a new one),
     * so it is not counted as a duplicate of itself.
     */
    private String validate(Supplier editing) {
        String error = "";

        String idText = idFld.getText().trim();
        if (idText.isEmpty()) {
            error += "ID is required.\n";
        } else {
            try {
                int id = Integer.parseInt(idText);
                if (id <= 0) {
                    error += "ID must be a positive number.\n";
                } else if (store.idExists(id, editing)) {
                    error += "A supplier with this ID already exists.\n";
                }
            } catch (NumberFormatException e) {
                error += "ID must be a whole number.\n";
            }
        }

        if (companyFld.getText().trim().isEmpty()) {
            error += "Company name is required.\n";
        }

        String phone = phoneFld.getText().trim();
        if (!phone.matches("\\d{8,15}")) {
            error += "Phone must contain 8 to 15 digits.\n";
        }

        String email = emailFld.getText().trim();
        if (!email.matches("[^@\\s]+@[^@\\s]+\\.[^@\\s]+")) {
            error += "Enter a valid email (example: name@company.com).\n";
        }

        if (productFld.getText().trim().isEmpty()) {
            error += "Product is required.\n";
        }

        return error;
    }

    private void clearFields() {
        idFld.clear();
        companyFld.clear();
        phoneFld.clear();
        emailFld.clear();
        productFld.clear();
        suppliersTable.getSelectionModel().clearSelection();
    }

    private void showError(String message) {
        messageLbl.setStyle("-fx-text-fill: red;");
        messageLbl.setText(message);
    }

    private void showSuccess(String message) {
        messageLbl.setStyle("-fx-text-fill: green;");
        messageLbl.setText(message);
    }
}
