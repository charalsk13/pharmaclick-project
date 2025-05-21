package com.pharmaclick;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.*;

public class PharmacyMyInfoController {

    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextField pharmacyNameField;
    @FXML private TextField fullNameField;
    @FXML private TextField passwordField;
    @FXML private TextField addressField;
    @FXML private TextField pharmacyCodeField;
    @FXML private Label successLabel;
    @FXML private Label deleteAccountLabel;

    private int pharmacyId;
    public static String loggedInEmail;

    public void setPharmacyId(int pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    // === 1. Φόρτωση δεδομένων όταν ανοίγει η σκηνή ===
    @FXML
    private void initialize() {
        loadPharmacyInfo();
    }

    private void loadPharmacyInfo() {
    String email = LoginController.loggedInEmail;
    System.out.println("🔍 Loading info for email: " + email);

    try (Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/pharmaclick", "root", "1111")) {

        String pharmacistQuery = "SELECT * FROM pharmacists WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(pharmacistQuery)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("✅ Pharmacist found.");
                fullNameField.setText(rs.getString("full_name"));
                emailField.setText(rs.getString("email"));
                passwordField.setText(rs.getString("password"));
                pharmacyCodeField.setText(rs.getString("amka"));

                pharmacyId = rs.getInt("pharmacy_id");
                System.out.println("➡ pharmacy_id: " + pharmacyId);
            } else {
                System.out.println("❌ Pharmacist not found!");
                return;
            }
        }

        String pharmacyQuery = "SELECT * FROM pharmacies WHERE id = ?";
        try (PreparedStatement stmt2 = conn.prepareStatement(pharmacyQuery)) {
            stmt2.setInt(1, pharmacyId);
            ResultSet rs2 = stmt2.executeQuery();

            if (rs2.next()) {
                System.out.println("✅ Pharmacy found.");
                pharmacyNameField.setText(rs2.getString("name"));
                phoneField.setText(rs2.getString("phone"));
                addressField.setText(rs2.getString("address"));
            } else {
                System.out.println("❌ Pharmacy not found!");
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}



    // === 2. Ενημέρωση των αλλαγών ===
   @FXML
private void handleSubmitChanges(ActionEvent event) {
    String email = emailField.getText();
    String phone = phoneField.getText();
    String pharmacyName = pharmacyNameField.getText();
    String fullName = fullNameField.getText();
    String password = passwordField.getText();
    String address = addressField.getText();
    String amka = pharmacyCodeField.getText();

    Connection conn = null;

    try {
        conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/pharmaclick", "root", "1111");
        conn.setAutoCommit(false);

        // === 1. Update pharmacists ===
        String updatePharmacist = "UPDATE pharmacists SET full_name=?, password=? WHERE email=?";
        try (PreparedStatement stmt = conn.prepareStatement(updatePharmacist)) {
            stmt.setString(1, fullName);
            stmt.setString(2, password);
            stmt.setString(3, LoginController.loggedInEmail);
            stmt.executeUpdate();
        }

        // === 2. Update pharmacies ===
        String updatePharmacy = "UPDATE pharmacies SET name=?, address=?, phone=? WHERE id=?";
        try (PreparedStatement stmt2 = conn.prepareStatement(updatePharmacy)) {
            stmt2.setString(1, pharmacyName);
            stmt2.setString(2, address);
            stmt2.setString(3, phone);
            stmt2.setInt(4, pharmacyId);
            stmt2.executeUpdate();
        }

        // === 3. Update users ===
        String updateUser = "UPDATE users SET password=? WHERE email=?";
        try (PreparedStatement stmt3 = conn.prepareStatement(updateUser)) {
            stmt3.setString(1, password);
            stmt3.setString(2, LoginController.loggedInEmail);
            stmt3.executeUpdate();
        }

        conn.commit();
LoginController.loggedInEmail = email;

successLabel.setText("✅ Οι αλλαγές αποθηκεύτηκαν επιτυχώς!");

        

        System.out.println("✅ Ενημέρωση όλων των στοιχείων επιτυχής!");

    } catch (SQLException e) {
        e.printStackTrace();
        try {
            if (conn != null) conn.rollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    } finally {
        try {
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}


    // === 3. Επιστροφή στην αρχική σελίδα ===
    @FXML
    public void goBackToHome(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/pharma_firstpage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 350, 600);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // === 4. Dummy event handlers for FXML compatibility ===

    @FXML private void handleChangePhotoClick(MouseEvent event) {
        System.out.println("Φωτογραφία πατήθηκε!");
    }

    @FXML private void handlePharmacyNameClick(MouseEvent event) {
        System.out.println("Κλικ στο όνομα φαρμακείου");
    }

    @FXML private void handleIdClick(MouseEvent event) {
        System.out.println("Κλικ στο πεδίο AMKA/Κωδικός");
    }

    @FXML private void handleLocationClick(MouseEvent event) {
        System.out.println("Κλικ στη διεύθυνση");
    }

    @FXML private void handlePasswordClick(MouseEvent event) {
        System.out.println("Κλικ στον κωδικό");
    }

    @FXML private void handleNameClick(MouseEvent event) {
        System.out.println("Κλικ στο ονοματεπώνυμο");
    }

    @FXML private void handleEmailClick(MouseEvent event) {
        System.out.println("Κλικ στο email");
    }

    @FXML private void handleTelephoneClick(MouseEvent event) {
        System.out.println("Κλικ στο τηλέφωνο");
    }

    @FXML
private void handleDeleteAccountClick(MouseEvent event) {
    String email = LoginController.loggedInEmail;

    if (email == null) {
        System.out.println("❌ Δεν υπάρχει χρήστης συνδεδεμένος.");
        return;
    }

    try (Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/pharmaclick", "root", "1111")) {
        conn.setAutoCommit(false);

        // 1. Βρες το pharmacy_id
        int pharmacyId = -1;
        String getPharmacyIdQuery = "SELECT pharmacy_id FROM pharmacists WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(getPharmacyIdQuery)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                pharmacyId = rs.getInt("pharmacy_id");
            }
        }

        // 2. Διέγραψε από pharmacists
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM pharmacists WHERE email = ?")) {
            stmt.setString(1, email);
            stmt.executeUpdate();
        }

        // 3. Διέγραψε από users
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM users WHERE email = ?")) {
            stmt.setString(1, email);
            stmt.executeUpdate();
        }

        // 4. Διέγραψε από pharmacies
        if (pharmacyId != -1) {
            try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM pharmacies WHERE id = ?")) {
                stmt.setInt(1, pharmacyId);
                stmt.executeUpdate();
            }
        }

        conn.commit();
        System.out.println("✅ Ο λογαριασμός διαγράφηκε επιτυχώς!");

        // Επιστροφή στην login σελίδα
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) deleteAccountLabel.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Είσοδος");
        stage.show();

    } catch (Exception e) {
        e.printStackTrace();
    }
}

}
