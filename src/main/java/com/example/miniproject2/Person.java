package com.example.miniproject2;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {
    // The database primary key (Customers.customer_id).
    // 0 means "not saved in the database yet".
    private int customerId;

    private final SimpleStringProperty name;
    private final SimpleIntegerProperty age;
    private final SimpleStringProperty address;
    private final SimpleStringProperty status;

    // Existing constructor: still works, so PersonsController doesn't change.
    // The id is 0 until PersonsStore.addPerson gets the real one from MySQL.
    public Person(String name, Integer age, String address, String status) {
        this(0, name, age, address, status);
    }

    // New constructor: used when we read a row from the database.
    public Person(int customerId, String name, Integer age, String address, String status) {
        this.customerId = customerId;
        this.name = new SimpleStringProperty(name);
        this.age = new SimpleIntegerProperty(age);
        this.address = new SimpleStringProperty(address);
        this.status = new SimpleStringProperty(status);
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return this.name;
    }

    public String getStatus() {
        return status.get();
    }

    public int getAge() {
        return age.get();
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public IntegerProperty ageProperty() {
        return this.age;
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public StringProperty addressProperty() {
        return this.address;
    }

    public void setStatus(String personStatus) {
        this.status.set(personStatus);
    }
}