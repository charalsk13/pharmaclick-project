package com.pharmaclick;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Στοιχεία σύνδεσης στη βάση δεδομένων
    private static final String URL = "jdbc:mariadb://localhost:3306/pharmaclick"; // Η IP και η θύρα του MariaDB server
    private static final String USER = "pharmaclick"; // Όνομα χρήστη που δημιούργησες
    private static final String PASSWORD = "1111"; // Ο κωδικός του χρήστη

    public static Connection getConnection() throws SQLException {
        // Σύνδεση με τη βάση δεδομένων
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        // Δοκιμή σύνδεσης
        try (Connection connection = getConnection()) {
            System.out.println("Επιτυχής σύνδεση με τη βάση δεδομένων!");
            
            // Εδώ μπορείς να εκτελέσεις SQL queries αν θέλεις
            
        } catch (SQLException e) {
            System.out.println("Σφάλμα σύνδεσης στη βάση δεδομένων: " + e.getMessage());
        }
    }
}
