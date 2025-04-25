package com.pharmaclick;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mariadb://localhost:3306/pharmaclick";
    private static final String USER = "pharmaclick";
    private static final String PASSWORD = "1111";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // main method μόνο για έλεγχο
    public static void main(String[] args) {
        try {
            Connection conn = getConnection();
            System.out.println("✅ Σύνδεση επιτυχής!");
            conn.close();
        } catch (SQLException e) {
            System.out.println("❌ Σφάλμα σύνδεσης: " + e.getMessage());
        }
    }
}
