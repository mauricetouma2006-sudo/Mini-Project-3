package com.example.miniproject2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Store for the Items table (Mini Project 4: JDBC).
 * Every operation is done on the MySQL database first, then the in-memory list
 * shown by the TableView is updated to match.
 */
public class itemStore {
    private final ObservableList<Item> items = FXCollections.observableArrayList();

    public itemStore() {
        loadItems();
    }

    /** SELECT: reads all items from the database into the list. */
    public void loadItems() {
        items.clear();
        String sql = "SELECT ean, name, price, stock FROM Items ORDER BY name";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                items.add(new Item(
                        rs.getLong("ean"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock")));
            }
        } catch (SQLException e) {
            System.out.println("Could not load items: " + e.getMessage());
        }
    }

    public ObservableList<Item> getItems() {
        return items;
    }

    /** INSERT */
    public void addItem(Item item) {
        if (item == null) {
            return;
        }
        String sql = "INSERT INTO Items (ean, name, price, stock) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, item.getEAN());
            stmt.setString(2, item.getName());
            stmt.setDouble(3, item.getPrice());
            stmt.setInt(4, item.getStock());
            stmt.executeUpdate();
            items.add(item);
        } catch (SQLException e) {
            System.out.println("Could not add item: " + e.getMessage());
        }
    }

    /** DELETE */
    public void removeItem(Item item) {
        if (item == null) {
            return;
        }
        String sql = "DELETE FROM Items WHERE ean = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, item.getEAN());
            stmt.executeUpdate();
            items.remove(item);
        } catch (SQLException e) {
            System.out.println("Could not delete item: " + e.getMessage());
        }
    }

    /** UPDATE (the WHERE uses the old EAN, so the EAN itself can also be changed) */
    public void updateItem(Item oldItem, long EAN, String name, double price, int stock) {
        if (oldItem == null) {
            return;
        }
        String sql = "UPDATE Items SET ean = ?, name = ?, price = ?, stock = ? WHERE ean = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, EAN);
            stmt.setString(2, name);
            stmt.setDouble(3, price);
            stmt.setInt(4, stock);
            stmt.setLong(5, oldItem.getEAN());
            stmt.executeUpdate();

            oldItem.setEAN(EAN);
            oldItem.setName(name);
            oldItem.setPrice(price);
            oldItem.setStock(stock);
        } catch (SQLException e) {
            System.out.println("Could not update item: " + e.getMessage());
        }
    }

    // Returns true if another item (not "ignore") already uses this EAN
    public boolean eanExists(long ean, Item ignore) {
        for (Item i : items) {
            if (i != ignore && i.getEAN() == ean) {
                return true;
            }
        }
        return false;
    }
}
