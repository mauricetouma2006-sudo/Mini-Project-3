package com.example.miniproject2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Model: holds the employees and keeps them in sync with the MySQL table
 * Employees. Every change goes to the database first, then to the list.
 */
public class EmployeeStore {
    private static final EmployeeStore INSTANCE = new EmployeeStore();

    private final ObservableList<Employee> employees = FXCollections.observableArrayList();

    private EmployeeStore() {
        loadEmployees();
    }

    public static EmployeeStore getInstance() {
        return INSTANCE;
    }

    public ObservableList<Employee> getEmployees() {
        return employees;
    }

    /** Step 2: read all rows from the database into the list. */
    private void loadEmployees() {
        String sql = "SELECT employee_id, first_name, last_name, age, salary, annual_leave FROM Employees";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                employees.add(new Employee(
                        rs.getInt("employee_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("age"),
                        rs.getDouble("salary"),
                        rs.getInt("annual_leave")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Step 3: INSERT, then read back the id that MySQL generated. */
    public void addEmployee(Employee employee) {
        if (employee == null) {
            return;
        }
        String sql = "INSERT INTO Employees (first_name, last_name, age, salary, annual_leave) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setInt(3, employee.getAge());
            ps.setDouble(4, employee.getSalary());
            ps.setInt(5, employee.getAnnualLeave());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    employee.setEmployeeId(keys.getInt(1));
                }
            }
            employees.add(employee);   // only after the database accepted it
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Step 5: DELETE by id. */
    public void deleteEmployee(Employee employee) {
        if (employee == null) {
            return;
        }
        String sql = "DELETE FROM Employees WHERE employee_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, employee.getEmployeeId());
            ps.executeUpdate();
            employees.remove(employee);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Step 4: UPDATE by id. */
    public void updateEmployee(Employee employee, String firstName, String lastName,
                               int age, double salary, int annualLeave) {
        if (employee == null) {
            return;
        }
        String sql = "UPDATE Employees SET first_name = ?, last_name = ?, age = ?, "
                + "salary = ?, annual_leave = ? WHERE employee_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setInt(3, age);
            ps.setDouble(4, salary);
            ps.setInt(5, annualLeave);
            ps.setInt(6, employee.getEmployeeId());
            ps.executeUpdate();

            employee.setFirstName(firstName);
            employee.setLastName(lastName);
            employee.setAge(age);
            employee.setSalary(salary);
            employee.setAnnualLeave(annualLeave);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}