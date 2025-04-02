package com.example.miniproject2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class itemStore {
    private final ObservableList<item> items = FXCollections.observableArrayList();

    public itemStore() {
        items.addAll(
                new item(81524962, "Pencil", 0.2, 10),
                new item(45941957, "Pen", 1.0, 20),
                new item(89179752, "Eraser", 0.5, 15));
    }

    public ObservableList<item> getItems() {
        return items;
    }

    public void addItem(item item) {
        if (item != null) {
            items.add(item);
        }
    }

    public void removeItem(item item) {
        if (item != null) {
            items.remove(item);
        }
    }

    public void updateItem(item oldItem, int EAN, String name, double price, int stock) {
        oldItem.setEAN(EAN);
        oldItem.setName(name);
        oldItem.setPrice(price);
        oldItem.setStock(stock);
    }

}
