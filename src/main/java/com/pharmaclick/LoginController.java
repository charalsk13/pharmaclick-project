package com.pharmaclick;

import java.io.IOException;
import java.sql.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class LoginController {

    public static String loggedInEmail;

    @FXML private TextField emailField1;
    @FXML private PasswordField passwordField1;
    @FXML private Hyperlink registerLink;
    @FXML private Button loginButton;

    // Μπορείς να προσθέσεις Text ή Label στο FXML για σφάλματα
    @FXML private Label errorText;

    private Connection connect() throws SQLException {
        String url = "jdbc:mariadb://localhost:3306/pharmaclick";
        String user = "pharmaclick";
        String password = "1111";
        return DriverManager.getConnection(url, user, password);
    }
    

    @FXML
    private void initialize() {
        errorText.setText(""); // καθαρίζει αρχικά
    }


    @FXML
    private void handleLogin() {
    
        String email = emailField1.getText().trim();
        String password = passwordField1.getText().trim();
    
        LoginController.loggedInEmail = email;
    
        if (email.isEmpty() || password.isEmpty()) {
            errorText.setText("Συμπλήρωσε όλα τα πεδία.");
            return;
        }
    
        try (Connection conn = connect()) {
            String sql = "SELECT user_type FROM users WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
    
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                String role = rs.getString("user_type");
    
                Stage stage = (Stage) loginButton.getScene().getWindow();
                Scene scene;
                Parent root;
    
                if (role.equalsIgnoreCase("pharmacist")) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/pharma_firstpage.fxml"));
                    root = loader.load();
                    
                    PharmaFirstPage controller = loader.getController();
                    controller.setPharmacyEmail(email); // ✅ περνάμε το email
    
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Αρχική Φαρμακοποιού");
                    stage.show();
    
                } else if (role.equalsIgnoreCase("customer")) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/frontpage_user.fxml"));
                    root = loader.load();
    
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Αρχική Χρήστη");
                    stage.show();
                } else {
                    errorText.setText("Άγνωστος ρόλος χρήστη.");
                }
    
            } else {
                errorText.setText("Λάθος email ή κωδικός.");
            }
    
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            errorText.setText("Σφάλμα σύνδεσης.");
        }
    }
    

    @FXML
    private void goToRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RegisterView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) registerLink.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Εγγραφή Χρήστη");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
