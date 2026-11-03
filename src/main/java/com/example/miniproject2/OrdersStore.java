package com.example.miniproject2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Store for the Orders table (Mini Project 4: JDBC).
 * Every change is saved in MySQL first, then the list shown in the table is updated.
 */
public class OrdersStore {
    private final ObservableList<Order> orders = FXCollections.observableArrayList();

    public OrdersStore() {
        loadOrders();
    }

    /** SELECT: reads all orders from the database into the list. */
    public void loadOrders() {
        orders.clear();
        String sql = "SELECT order_id, customer_name, order_date, total FROM Orders ORDER BY order_id";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("order_id"),
                        rs.getString("customer_name"),
                        rs.getDate("order_date").toLocalDate(),
                        rs.getDouble("total")));
            }
        } catch (SQLException e) {
            System.out.println("Could not load orders: " + e.getMessage());
        }
    }

    public ObservableList<Order> getOrders() {
        return orders;
    }

    /** INSERT */
    public void addOrder(Order order) {
        String sql = "INSERT INTO Orders (order_id, customer_name, order_date, total) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, order.getOrderId());
            stmt.setString(2, order.getCustomerName());
            stmt.setDate(3, java.sql.Date.valueOf(order.getDate()));
            stmt.setDouble(4, order.getTotal());
            stmt.executeUpdate();
            orders.add(order);
        } catch (SQLException e) {
            System.out.println("Could not add order: " + e.getMessage());
        }
    }

    /** UPDATE (the WHERE uses the old ID, so the ID itself can also be changed) */
    public void updateOrder(Order order, int newId, String customer, LocalDate date, double total) {
        String sql = "UPDATE Orders SET order_id = ?, customer_name = ?, order_date = ?, total = ? WHERE order_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, newId);
            stmt.setString(2, customer);
            stmt.setDate(3, java.sql.Date.valueOf(date));
            stmt.setDouble(4, total);
            stmt.setInt(5, order.getOrderId());
            stmt.executeUpdate();

            order.setOrderId(newId);
            order.setCustomerName(customer);
            order.setDate(date);
            order.setTotal(total);
        } catch (SQLException e) {
            System.out.println("Could not update order: " + e.getMessage());
        }
    }

    /** DELETE */
    public void deleteOrder(Order order) {
        String sql = "DELETE FROM Orders WHERE order_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, order.getOrderId());
            stmt.executeUpdate();
            orders.remove(order);
        } catch (SQLException e) {
            System.out.println("Could not delete order: " + e.getMessage());
        }
    }
}