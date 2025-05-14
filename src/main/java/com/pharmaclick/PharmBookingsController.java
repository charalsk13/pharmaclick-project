package com.pharmaclick;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.pharmaclick.models.Booking;




public class PharmBookingsController {

    @FXML
    private TableView<Booking> bookingsTable;

    private int currentPharmacyId;

    public void setPharmacyId(int pharmacyId) {
        this.currentPharmacyId = pharmacyId;
        loadBookingsForPharmacy();
    }

    private void loadBookingsForPharmacy() {
        ObservableList<Booking> bookings = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmaclick", "root", "");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM bookings WHERE pharmacy_id = ?")) {

            stmt.setInt(1, currentPharmacyId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Booking booking = new Booking(
                    rs.getInt("id"),
                    rs.getInt("pharmacy_id"),
                    rs.getString("customer_name"),
                    rs.getString("customer_address"),
                    rs.getString("customer_phone"),
                    rs.getString("customer_email"),
                    rs.getString("customer_amka"),
                    rs.getString("comment"),
                    rs.getDate("pickup_date"),
                    rs.getDouble("total_price")
                );
                bookings.add(booking);
            }

            bookingsTable.setItems(bookings);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
