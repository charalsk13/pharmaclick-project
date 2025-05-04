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

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class PharmacyMyInfoController {

    @FXML
    private void handleChangePhotoClick(MouseEvent event) {
        System.out.println("Αλλαγή φωτογραφίας");
    }

    @FXML
    private void handleEmailClick(MouseEvent event) {
        System.out.println("Πεδίο Email");
    }

    @FXML
    private void handleTelephoneClick(MouseEvent event) {
        System.out.println("Πεδίο Τηλέφωνο");
    }

    @FXML
    private void handlePharmacyNameClick(MouseEvent event) {
        System.out.println("Πεδίο Όνομα Φαρμακείου");
    }

    @FXML
    private void handleNameClick(MouseEvent event) {
        System.out.println("Πεδίο Ονοματεπώνυμο");
    }

    @FXML
    private void handlePasswordClick(MouseEvent event) {
        System.out.println("Πεδίο Κωδικός");
    }

    @FXML
    private void handleLocationClick(MouseEvent event) {
        System.out.println("Πεδίο Διεύθυνση");
    }

    @FXML
    private void handleIdClick(MouseEvent event) {
        System.out.println("Πεδίο Κωδικός Φαρμακείου");
    }

    @FXML
    private void handleDeleteAccountClick(MouseEvent event) {
        System.out.println("Διαγραφή Λογαριασμού");
    }

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

    @FXML private TextField emailField;
@FXML private TextField phoneField;
@FXML private TextField pharmacyNameField;
@FXML private TextField fullNameField;
@FXML private TextField passwordField;
@FXML private TextField addressField;
@FXML private TextField pharmacyCodeField;

@FXML
private void handleSubmitChanges(ActionEvent event) {
    String email = emailField.getText();
    String phone = phoneField.getText();
    String pharmacyName = pharmacyNameField.getText();
    String fullName = fullNameField.getText();
    String password = passwordField.getText();
    String address = addressField.getText();
    String amka = pharmacyCodeField.getText();

    // Παράδειγμα σύνδεσης και ενημέρωσης DB
    try (Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/pharmaclick", "root", "1111");
         PreparedStatement stmt = conn.prepareStatement(
             "UPDATE pharmacists SET full_name=?, email=?, phone=?, password=?, address=?, amka=? WHERE email=?")) {

        stmt.setString(1, fullName);
        stmt.setString(2, email);
        stmt.setString(3, phone);
        stmt.setString(4, password);
        stmt.setString(5, address);
        stmt.setString(6, amka);
        stmt.setString(7, LoginController.loggedInEmail);
 // WHERE email = current user email

        int updated = stmt.executeUpdate();
        
        if (updated > 0) {
            System.out.println("Ενημέρωση επιτυχής!");
            LoginController.loggedInEmail = email;

        } else {
            System.out.println("Καμία αλλαγή. Ίσως το email δεν αντιστοιχεί σε χρήστη.");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

}
