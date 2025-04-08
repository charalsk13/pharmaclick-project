package com.pharmaclick;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterController {

    // Σύνδεση με τα πεδία της φόρμας (με βάση τα fx:id του FXML)
    @FXML
    private TextField emailField1;

    @FXML
    private PasswordField passwordField1;

    @FXML
    private TextField addressField1;

    @FXML
    private TextField amkaField1;

    @FXML
    private CheckBox customerCheckBox1;

    @FXML
    private CheckBox pharmacistCheckBox1;

    @FXML
    private javafx.scene.control.Hyperlink loginLink1;


    // Η μέθοδος που καλείται όταν ο χρήστης πατήσει "Δημιουργία Λογαριασμού"
    public void handleRegister() {
        // Πάρε τα δεδομένα από τα πεδία της φόρμας
        String email = emailField1.getText(); 
        String password = passwordField1.getText();
        String address = addressField1.getText();
        String amka = amkaField1.getText();

        String userType;
        if (customerCheckBox1.isSelected()) {
            userType = "customer";
        } else if (pharmacistCheckBox1.isSelected()) {
            userType = "pharmacist";
        } else {
            userType = "unknown";
        }

        // Κάλεσε τη μέθοδο για να προσθέσεις το χρήστη στη βάση δεδομένων
        registerUser(email, password, address, amka, userType);
    }

    // Μέθοδος για την εισαγωγή του χρήστη στη βάση δεδομένων
    private void registerUser(String email, String password, String address, String amka, String userType) {
        String query = "INSERT INTO users (email, password, address, amka, user_type) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, email);
            statement.setString(2, password);
            statement.setString(3, address);
            statement.setString(4, amka);
            statement.setString(5, userType);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Ο χρήστης εγγράφηκε με επιτυχία!");
            } else {
                System.out.println("Η εγγραφή απέτυχε.");
            }
        } catch (SQLException e) {
            System.out.println("Σφάλμα κατά την εγγραφή: " + e.getMessage());
        }
    }
    @FXML
    private void goToLogin() {
        try {
            // Φόρτωση του login.fxml
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/com/pharmaclick/login.fxml"));
            javafx.scene.Parent root = loader.load();
    
            javafx.stage.Stage stage = (javafx.stage.Stage) loginLink1.getScene().getWindow(); // Παίρνει το υπάρχον stage
            stage.setScene(new javafx.scene.Scene(root));
            stage.setTitle("Σύνδεση Χρήστη");
            stage.show();
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Μέθοδος για τη σύνδεση με τη βάση δεδομένων
    private Connection getConnection() throws SQLException {
        String url = "jdbc:mariadb://localhost:3306/pharmaclick";
        String user = "pharmaclick";
        String password = "1111";
        return DriverManager.getConnection(url, user, password);
    }
}
