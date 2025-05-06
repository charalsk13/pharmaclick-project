package com.pharmaclick;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    // Τροποποίησε τη σύνδεση ανάλογα με τη βάση σου
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mariadb://localhost:3306/pharmaclick";
        String user = "root";
        String password = "1111"; // άλλαξέ το αν χρειάζεται
        return DriverManager.getConnection(url, user, password);
    }

    public static List<Medicine> getMedicinesByPharmacyId(int pharmacyId) {
        List<Medicine> medicines = new ArrayList<>();
        String sql = "SELECT id, name, description, price FROM medicines WHERE pharmacy_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pharmacyId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                medicines.add(new Medicine(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("price")
                ));
            }

        } catch (SQLException e) {
            System.out.println("⚠️ Σφάλμα στη λήψη φαρμάκων: " + e.getMessage());
        }

        return medicines;
    }


    public static void addFavorite(int userId, int medicineId) {
        System.out.println("🛠️ Trying to insert into favorites: userId=" + userId + ", medicineId=" + medicineId);
    
        String sql = "INSERT IGNORE INTO favorites (user_id, medicine_id) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, medicineId);
            int rows = stmt.executeUpdate();
            System.out.println("✅ Γράφτηκαν " + rows + " γραμμές.");
        } catch (SQLException e) {
            System.out.println("⚠️ Σφάλμα προσθήκης αγαπημένου: " + e.getMessage());
        }
    }
    
    
    public static void removeFavorite(int userId, int medicineId) {
        String sql = "DELETE FROM favorites WHERE user_id = ? AND medicine_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, medicineId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("⚠️ Σφάλμα διαγραφής αγαπημένου: " + e.getMessage());
        }
    }
    
    
}
