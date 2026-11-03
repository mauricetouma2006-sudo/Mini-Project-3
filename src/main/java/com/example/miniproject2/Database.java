package com.example.miniproject2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * One place for the MySQL connection settings.
 * Every Store calls Database.getConnection() instead of repeating the URL and password.
 * Everyone on the team uses the same settings: database miniproject3db, user root, password 123456.
 */
public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/miniproject3db";
    private static final String USER = "root";
    private static final String PASSWORD = "Shromps77moon@@";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC driver not found. Reload Maven (pom.xml).", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
