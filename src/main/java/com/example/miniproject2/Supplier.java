package com.example.miniproject2;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * MODEL: one supplier of the company (who sells us our products).
 * Each field is a JavaFX property so the TableView can display it.
 */
public class Supplier {
    private final IntegerProperty supplierId;
    private final StringProperty companyName;
    private final StringProperty phone;
    private final StringProperty email;
    private final StringProperty product;

    public Supplier(int supplierId, String companyName, String phone, String email, String product) {
        this.supplierId = new SimpleIntegerProperty(supplierId);
        this.companyName = new SimpleStringProperty(companyName);
        this.phone = new SimpleStringProperty(phone);
        this.email = new SimpleStringProperty(email);
        this.product = new SimpleStringProperty(product);
    }

    public int getSupplierId() {
        return supplierId.get();
    }

    public void setSupplierId(int supplierId) {
        this.supplierId.set(supplierId);
    }

    public IntegerProperty supplierIdProperty() {
        return supplierId;
    }

    public String getCompanyName() {
        return companyName.get();
    }

    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }

    public StringProperty companyNameProperty() {
        return companyName;
    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getProduct() {
        return product.get();
    }

    public void setProduct(String product) {
        this.product.set(product);
    }

    public StringProperty productProperty() {
        return product;
    }
}
