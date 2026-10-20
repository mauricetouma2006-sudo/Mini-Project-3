package com.example.miniproject2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class itemStore {
    private final ObservableList<Item> items = FXCollections.observableArrayList();

    public itemStore() {
        items.addAll(
                new Item(81524962, "Pencil", 0.2, 10),
                new Item(45941957, "Pen", 1.0, 20),
                new Item(89179752, "Eraser", 0.5, 15));
    }

    public ObservableList<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        if (item != null) {
            items.add(item);
        }
    }

    public void removeItem(Item item) {
        if (item != null) {
            items.remove(item);
        }
    }

    public void updateItem(Item oldItem, long EAN, String name, double price, int stock) {
        oldItem.setEAN(EAN);
        oldItem.setName(name);
        oldItem.setPrice(price);
        oldItem.setStock(stock);
    }

}
