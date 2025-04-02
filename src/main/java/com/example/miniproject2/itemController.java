package com.example.miniproject2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class itemController {

    @FXML
    private Button addbtn, updateBtn, deleteBtn;

    @FXML
    private TableColumn<item, Integer> EANcol;

    @FXML
    private TextField eanFld, nameFld, priceFld, stockFld;

    @FXML
    private TableColumn<item, String> nameCol;

    @FXML
    private TableColumn<item, Double> priceCol;

    @FXML
    private TableColumn<item, Integer> stockCol;

    @FXML
    private TableView<item> itemsTable;

    @FXML
    private Label errorMsg;
    private final itemStore ic = new itemStore();
    private final ObservableList<item> itemsList = ic.getItems();

    @FXML
    public void initialize() {
        EANcol.setCellValueFactory(new PropertyValueFactory<>("EAN"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
        stockCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));

        itemsTable.setItems(ic.getItems());

        itemsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                eanFld.setText(Integer.toString(newSelection.getEAN()));
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

        int EAN = 0;
        try {
            EAN = Integer.parseInt(eanFld.getText().trim());
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
            item newItem = new item(EAN, name, price, stock);
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
        item selectedItem = itemsTable.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            errorMsg.setText("No item selected for update.");
            return;
        }

        try {
            int EAN = Integer.parseInt(eanFld.getText().trim());
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
        item selectedItem = itemsTable.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            errorMsg.setText("No item selected for deletion.");
            return;
        }

        ic.removeItem(selectedItem);

        itemsTable.setItems(FXCollections.observableArrayList(ic.getItems()));
        itemsTable.refresh();
        clearFields();
    }

    private void clearFields() {
        eanFld.clear();
        nameFld.clear();
        priceFld.clear();
        stockFld.clear();
    }
}