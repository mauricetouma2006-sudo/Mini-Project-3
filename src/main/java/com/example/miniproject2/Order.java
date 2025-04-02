package com.example.miniproject2;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Order {
    private final IntegerProperty orderId;
    private final StringProperty customerName;
    private final ObjectProperty<LocalDate> date;
    private final DoubleProperty total;

    public Order(int orderId, String customerName, LocalDate date, double total) {
        this.orderId = new SimpleIntegerProperty(orderId);
        this.customerName = new SimpleStringProperty(customerName);
        this.date = new SimpleObjectProperty<>(date);
        this.total = new SimpleDoubleProperty(total);
    }

    public int getOrderId() {
        return orderId.get();
    }

    public void setOrderId(int id) {
        this.orderId.set(id);
    }

    public IntegerProperty orderIdProperty() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public void setCustomerName(String name) {
        this.customerName.set(name);
    }

    public StringProperty customerNameProperty() {
        return customerName;
    }

    public LocalDate getDate() {
        return date.get();
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public double getTotal() {
        return total.get();
    }

    public void setTotal(double total) {
        this.total.set(total);
    }

    public DoubleProperty totalProperty() {
        return total;
    }
}
