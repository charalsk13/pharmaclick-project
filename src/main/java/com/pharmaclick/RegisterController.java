package com.pharmaclick;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterController {

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

    public void handleRegister() {
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

        registerUser(email, password, address, amka, userType);

        if (userType.equals("pharmacist")) {
            registerPharmacist(email, address, amka, password);  // 👈 Εδώ περνάμε και το password
        } else if (userType.equals("customer")) {
            registerCustomer(email, address, amka, password);
        }
        // Μόλις τελειώσει η εγγραφή, δείξε Alert επιτυχίας
showSuccessAndRedirect();
    }

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
            System.out.println("Σφάλμα κατά την εγγραφή χρήστη: " + e.getMessage());
        }
    }

    private void registerPharmacist(String email, String address, String amka, String password) {
        String query = "INSERT INTO pharmacists (email, address, amka, password) VALUES (?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, email);
            statement.setString(2, address);
            statement.setString(3, amka);
            statement.setString(4, password);

            statement.executeUpdate();
            System.out.println("Ο φαρμακοποιός εγγράφηκε με επιτυχία!");
        } catch (SQLException e) {
            System.out.println("Σφάλμα κατά την εγγραφή φαρμακοποιού: " + e.getMessage());
        }
    }

    private void registerCustomer(String email, String address, String amka, String password) {
        String query = "INSERT INTO customers (email, address, amka, password) VALUES (?, ?, ?, ?)";
    
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
    
            statement.setString(1, email);
            statement.setString(2, address);
            statement.setString(3, amka);
            statement.setString(4, password);
    
            statement.executeUpdate();
            System.out.println("Ο πελάτης εγγράφηκε με επιτυχία!");
        } catch (SQLException e) {
            System.out.println("Σφάλμα κατά την εγγραφή πελάτη: " + e.getMessage());
        }
    }
    

    @FXML
    private void goToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) loginLink1.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Σύνδεση Χρήστη");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mariadb://localhost:3306/pharmaclick";
        String user = "pharmaclick";
        String password = "1111";
        return DriverManager.getConnection(url, user, password);
    }
    private void showSuccessAndRedirect() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Επιτυχία");
        alert.setHeaderText(null);
        alert.setContentText("Η εγγραφή ολοκληρώθηκε επιτυχώς! Παρακαλώ συνδεθείτε.");
        alert.showAndWait(); // Περιμένει να πατήσεις ΟΚ
    
        // Μετά σε πάει στη σελίδα Σύνδεσης
        goToLogin();
    }
    
}
