package com.example.miniproject2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Holds the list of suppliers in memory (like PersonsStore and itemStore).
 * The controller asks this class to add, update and delete suppliers.
 */
public class SupplierStore {
    private final ObservableList<Supplier> suppliers = FXCollections.observableArrayList();

    public SupplierStore() {
        // Sample data so the table is not empty when the screen opens
        suppliers.addAll(
                new Supplier(1, "Office Plus", "01234567", "sales@officeplus.com", "Stationery"),
                new Supplier(2, "Tech Source", "03987654", "contact@techsource.com", "Computers"),
                new Supplier(3, "Paper House", "09112233", "info@paperhouse.com", "Paper"));
    }

    public ObservableList<Supplier> getSuppliers() {
        return suppliers;
    }

    public void addSupplier(Supplier supplier) {
        if (supplier != null) {
            suppliers.add(supplier);
        }
    }

    public void deleteSupplier(Supplier supplier) {
        if (supplier != null) {
            suppliers.remove(supplier);
        }
    }

    public void updateSupplier(Supplier supplier, int id, String companyName, String phone, String email,
            String product) {
        if (supplier != null) {
            supplier.setSupplierId(id);
            supplier.setCompanyName(companyName);
            supplier.setPhone(phone);
            supplier.setEmail(email);
            supplier.setProduct(product);
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
