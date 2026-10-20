package com.example.miniproject2;

import javafx.beans.property.*;

public class Item {
    private final SimpleLongProperty ean;
    private final SimpleStringProperty name;
    private final SimpleDoubleProperty price;
    private final SimpleIntegerProperty stock;

    public Item(long ean, String name, double price, int stock) {
        this.ean = new SimpleLongProperty(ean);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.stock = new SimpleIntegerProperty(stock);
    }

    public long getEAN() {
        return ean.get();
    }

    public void setEAN(long ean) {
        this.ean.set(ean);
    }

    public LongProperty eanProperty() {
        return ean;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public int getStock() {
        return stock.get();
    }

    public void setStock(int stock) {
        this.stock.set(stock);
    }

    public IntegerProperty stockProperty() {
        return stock;
    }
}
