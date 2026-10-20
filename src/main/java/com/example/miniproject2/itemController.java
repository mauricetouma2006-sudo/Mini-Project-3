package com.example.miniproject2;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class itemController {

    @FXML
    private Button addbtn, updateBtn, deleteBtn;

    @FXML
    private TableColumn<Item, Long> EANcol;

    @FXML
    private TextField eanFld, nameFld, priceFld, stockFld;

    @FXML
    private TableColumn<Item, String> nameCol;

    @FXML
    private TableColumn<Item, Double> priceCol;

    @FXML
    private TableColumn<Item, Integer> stockCol;

    @FXML
    private TableView<Item> itemsTable;

    @FXML
    private Label errorMsg;
    private final itemStore ic = new itemStore();
    private final ObservableList<Item> itemsList = ic.getItems();

    @FXML
    public void initialize() {
        EANcol.setCellValueFactory(new PropertyValueFactory<>("EAN"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
        stockCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));

        itemsTable.setItems(ic.getItems());

        itemsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                eanFld.setText(Long.toString(newSelection.getEAN()));
                nameFld.setText(newSelection.getName());
                priceFld.setText(Double.toString(newSelection.getPrice()));
                stockFld.setText(Integer.toString(newSelection.getStock()));
            }
            if (errorMsg == null) {
                System.out.println("Error: errorMsg Label is not initialized!");
            }
        });
    }

    @FXML
    void addItem(ActionEvent event) {

        errorMsg.setText("");
        boolean isValid = true;
        String error = "";

        long EAN = 0;
        try {
            EAN = Long.parseLong(eanFld.getText().trim());
        } catch (NumberFormatException e) {
            error += "EAN must be a valid number.\n";
            isValid = false;
        }

        String name = nameFld.getText().trim();
        if (name.isEmpty()) {
            error += "You must enter an item Name.\n";
            isValid = false;
        }

        double price = 0;
        try {
            price = Double.parseDouble(priceFld.getText().trim());
        } catch (NumberFormatException e) {
            error += "Price must be a valid number.\n";
            isValid = false;
        }

        int stock = 0;
        try {
            stock = Integer.parseInt(stockFld.getText().trim());
        } catch (NumberFormatException e) {
            error += "Stock must be a valid number.\n";
            isValid = false;
        }

        if (isValid) {
            Item newItem = new Item(EAN, name, price, stock);
            ic.addItem(newItem); // Ensure item is added to itemStore
            itemsTable.setItems(FXCollections.observableArrayList(ic.getItems())); // Refresh table
            itemsTable.refresh();
            clearFields();
        } else {
            errorMsg.setText(error);
        }

    }

    @FXML
    void updateItem(ActionEvent event) {
        Item selectedItem = itemsTable.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            errorMsg.setText("No item selected for update.");
            return;
        }

        try {
            long EAN = Long.parseLong(eanFld.getText().trim());
            String name = nameFld.getText().trim();
            double price = Double.parseDouble(priceFld.getText().trim());
            int stock = Integer.parseInt(stockFld.getText().trim());

            selectedItem.setEAN(EAN);
            selectedItem.setName(name);
            selectedItem.setPrice(price);
            selectedItem.setStock(stock);

            itemsTable.refresh();
            clearFields();
        } catch (NumberFormatException e) {
            errorMsg.setText("Please enter valid numerical values for EAN, Price, and Stock.");
        }
    }

    @FXML
    void deleteItem(ActionEvent event) {
        Item selectedItem = itemsTable.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            errorMsg.setText("No item selected for deletion.");
            return;
        }

        ic.removeItem(selectedItem);

        itemsTable.setItems(FXCollections.observableArrayList(ic.getItems()));
        itemsTable.refresh();
        clearFields();
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeView.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Home View");
        stage.show();
    }

    private void clearFields() {
        eanFld.clear();
        nameFld.clear();
        priceFld.clear();
        stockFld.clear();
    }
}