package com.example.miniproject2;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty age;
    private final SimpleStringProperty address;
    private final SimpleStringProperty status;

    public Person(String name, Integer age, String address, String status) {
        this.name = new SimpleStringProperty(name);
        this.age = new SimpleIntegerProperty(age);
        this.address = new SimpleStringProperty(address);
        this.status = new SimpleStringProperty(status);
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
