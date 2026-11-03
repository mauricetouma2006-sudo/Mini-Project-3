package com.example.miniproject2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Store for the Customers table (Mini Project 4: JDBC).
 * Every operation is done on the MySQL database first, then the in-memory list
 * shown by the TableView is updated to match.
 */
public class PersonsStore {
    private final ObservableList<Person> persons = FXCollections.observableArrayList();

    public PersonsStore() {
        loadPersons();
    }

    /** SELECT: reads all customers from the database into the list. */
    public void loadPersons() {
        persons.clear();
        String sql = "SELECT customer_id, name, age, address, status FROM Customers ORDER BY customer_id";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                persons.add(new Person(
                        rs.getInt("customer_id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("address"),
                        rs.getString("status")));
            }
        } catch (SQLException e) {
            System.out.println("Could not load customers: " + e.getMessage());
        }
    }

    public ObservableList<Person> getPersonsList() {
        return persons;
    }

    /** INSERT: MySQL creates the customer_id, and we read it back into the Person. */
    public void addPerson(Person person) {
        if (person == null) {
            return;
        }
        String sql = "INSERT INTO Customers (name, age, address, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, person.getName());
            stmt.setInt(2, person.getAge());
            stmt.setString(3, person.getAddress());
            stmt.setString(4, person.getStatus());
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    person.setCustomerId(keys.getInt(1));
                }
            }
            persons.add(person);
        } catch (SQLException e) {
            System.out.println("Could not add customer: " + e.getMessage());
        }
    }

    /** DELETE */
    public void deletePerson(Person person) {
        if (person == null) {
            return;
        }
        String sql = "DELETE FROM Customers WHERE customer_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, person.getCustomerId());
            stmt.executeUpdate();
            persons.remove(person);
        } catch (SQLException e) {
            System.out.println("Could not delete customer: " + e.getMessage());
        }
    }

    /** UPDATE */
    public void updatePerson(Person person, String name, Integer age, String address, String status) {
        if (person == null) {
            return;
        }
        String sql = "UPDATE Customers SET name = ?, age = ?, address = ?, status = ? WHERE customer_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setString(3, address);
            stmt.setString(4, status);
            stmt.setInt(5, person.getCustomerId());
            stmt.executeUpdate();

            person.setName(name);
            person.setAge(age);
            person.setAddress(address);
            person.setStatus(status);
        } catch (SQLException e) {
            System.out.println("Could not update customer: " + e.getMessage());
        }
    }
}