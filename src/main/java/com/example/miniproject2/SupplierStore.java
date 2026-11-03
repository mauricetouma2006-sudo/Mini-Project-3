package com.example.miniproject2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Store for the Suppliers table (Mini Project 4: JDBC).
 * Every operation is done on the MySQL database first, then the in-memory list
 * shown by the TableView is updated to match.
 */
public class SupplierStore {
    private final ObservableList<Supplier> suppliers = FXCollections.observableArrayList();

    public SupplierStore() {
        loadSuppliers();
    }

    /** SELECT: reads all suppliers from the database into the list. */
    public void loadSuppliers() {
        suppliers.clear();
        String sql = "SELECT supplier_id, company_name, phone, email, product FROM Suppliers ORDER BY supplier_id";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                suppliers.add(new Supplier(
                        rs.getInt("supplier_id"),
                        rs.getString("company_name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("product")));
            }
        } catch (SQLException e) {
            System.out.println("Could not load suppliers: " + e.getMessage());
        }
    }

    public ObservableList<Supplier> getSuppliers() {
        return suppliers;
    }

    /** INSERT */
    public void addSupplier(Supplier supplier) {
        if (supplier == null) {
            return;
        }
        String sql = "INSERT INTO Suppliers (supplier_id, company_name, phone, email, product) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, supplier.getSupplierId());
            stmt.setString(2, supplier.getCompanyName());
            stmt.setString(3, supplier.getPhone());
            stmt.setString(4, supplier.getEmail());
            stmt.setString(5, supplier.getProduct());
            stmt.executeUpdate();
            suppliers.add(supplier);
        } catch (SQLException e) {
            System.out.println("Could not add supplier: " + e.getMessage());
        }
    }

    /** DELETE */
    public void deleteSupplier(Supplier supplier) {
        if (supplier == null) {
            return;
        }
        String sql = "DELETE FROM Suppliers WHERE supplier_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, supplier.getSupplierId());
            stmt.executeUpdate();
            suppliers.remove(supplier);
        } catch (SQLException e) {
            System.out.println("Could not delete supplier: " + e.getMessage());
        }
    }

    /** UPDATE (the WHERE uses the old ID, so the ID itself can also be changed) */
    public void updateSupplier(Supplier supplier, int id, String companyName, String phone, String email,
            String product) {
        if (supplier == null) {
            return;
        }
        String sql = "UPDATE Suppliers SET supplier_id = ?, company_name = ?, phone = ?, email = ?, product = ? "
                + "WHERE supplier_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setString(2, companyName);
            stmt.setString(3, phone);
            stmt.setString(4, email);
            stmt.setString(5, product);
            stmt.setInt(6, supplier.getSupplierId());
            stmt.executeUpdate();

            supplier.setSupplierId(id);
            supplier.setCompanyName(companyName);
            supplier.setPhone(phone);
            supplier.setEmail(email);
            supplier.setProduct(product);
        } catch (SQLException e) {
            System.out.println("Could not update supplier: " + e.getMessage());
        }
    }

    // Returns true if another supplier (not "ignore") already uses this ID
    public boolean idExists(int id, Supplier ignore) {
        for (Supplier s : suppliers) {
            if (s != ignore && s.getSupplierId() == id) {
                return true;
            }
        }
        return false;
    }
}
