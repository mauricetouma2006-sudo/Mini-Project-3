package com.example.miniproject2;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model: one employee. Uses JavaFX properties so the TableView
 * (PropertyValueFactory) can read the values and refresh automatically.
 */
public class Employee {
    private final StringProperty firstName = new SimpleStringProperty();
    private final StringProperty lastName = new SimpleStringProperty();
    private final IntegerProperty age = new SimpleIntegerProperty();
    private final DoubleProperty salary = new SimpleDoubleProperty();
    private final IntegerProperty annualLeave = new SimpleIntegerProperty();

    public Employee(String firstName, String lastName, int age, double salary, int annualLeave) {
        setFirstName(firstName);
        setLastName(lastName);
        setAge(age);
        setSalary(salary);
        setAnnualLeave(annualLeave);
    }

    public String getFirstName() { return firstName.get(); }
    public void setFirstName(String value) { firstName.set(value); }
    public StringProperty firstNameProperty() { return firstName; }

    public String getLastName() { return lastName.get(); }
    public void setLastName(String value) { lastName.set(value); }
    public StringProperty lastNameProperty() { return lastName; }

    public int getAge() { return age.get(); }
    public void setAge(int value) { age.set(value); }
    public IntegerProperty ageProperty() { return age; }

    public double getSalary() { return salary.get(); }
    public void setSalary(double value) { salary.set(value); }
    public DoubleProperty salaryProperty() { return salary; }

    public int getAnnualLeave() { return annualLeave.get(); }
    public void setAnnualLeave(int value) { annualLeave.set(value); }
    public IntegerProperty annualLeaveProperty() { return annualLeave; }
}
