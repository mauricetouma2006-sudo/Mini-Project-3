package com.example.miniproject2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Model: holds the list of employees in memory (like ItemStore, SupplierStore
 * and PersonsStore). The controller asks this class to add, update and delete.
 */
public class EmployeeStore {
    private static final EmployeeStore INSTANCE = new EmployeeStore();

    private final ObservableList<Employee> employees = FXCollections.observableArrayList();

    private EmployeeStore() {
        // Sample data so the table is not empty the first time it opens
        employees.addAll(
                new Employee("John", "Smith", 30, 1500.0, 20),
                new Employee("Sara", "Khoury", 27, 1300.0, 18));
    }

    /**
     * Every screen load creates a new controller, so the store is shared:
     * otherwise the employees would disappear when you press Back and return.
     */
    public static EmployeeStore getInstance() {
        return INSTANCE;
    }

    public ObservableList<Employee> getEmployees() {
        return employees;
    }

    public void addEmployee(Employee employee) {
        if (employee != null) {
            employees.add(employee);
        }
    }

    public void deleteEmployee(Employee employee) {
        if (employee != null) {
            employees.remove(employee);
        }
    }

    public void updateEmployee(Employee employee, String firstName, String lastName,
            int age, double salary, int annualLeave) {
        if (employee != null) {
            employee.setFirstName(firstName);
            employee.setLastName(lastName);
            employee.setAge(age);
            employee.setSalary(salary);
            employee.setAnnualLeave(annualLeave);
        }
    }
}
