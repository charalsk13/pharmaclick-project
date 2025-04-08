package com.pharmaclick;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static void main(String[] args) {
        String url = "jdbc:mariadb://192.168.1.4:3306/pharmaclick";
        String user = "pharmaclick";
        String pass = "1111";

        try {
            Class.forName("org.mariadb.jdbc.Driver"); // Ενεργοποιεί τον driver

            Connection conn = DriverManager.getConnection(url, user, pass);
            System.out.println("✅ Σύνδεση επιτυχής!");
            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Δεν βρέθηκε ο driver: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("❌ Σφάλμα σύνδεσης στη βάση δεδομένων: " + e.getMessage());
        }
    }
}
