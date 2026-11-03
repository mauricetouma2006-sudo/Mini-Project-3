package com.example.miniproject2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Model/Store for the Accounts table: checks login credentials against the database.
 */
public class AccountsStore {

    /** Returns true if the username exists and the password's hash matches the stored hash. */
    public boolean checkLogin(String username, String password) throws SQLException {
        String sql = "SELECT password_hash FROM Accounts WHERE username = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password_hash");
                    return storedHash.equals(PasswordUtil.hash(password));
                }
                return false; // no such username
            }
        }
    }
}
