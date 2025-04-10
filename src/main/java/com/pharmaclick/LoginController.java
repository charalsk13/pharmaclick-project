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


                // Redirect ανάλογα με τον ρόλο
                String fxmlToLoad;
                if (role.equalsIgnoreCase("customer")) {
                    fxmlToLoad = "/views/frontpage_user.fxml";
                } else if (role.equalsIgnoreCase("pharmacist")) {
                    fxmlToLoad = "/views/frontpage_pharmacist.fxml";
                } else {
                    errorText.setText("Άγνωστος ρόλος χρήστη.");
                    return;
                }

                Parent root = FXMLLoader.load(getClass().getResource(fxmlToLoad));
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Αρχική Σελίδα");
                stage.show();

            } else {
                errorText.setText("Λάθος email ή κωδικός.");
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            errorText.setText("Σφάλμα σύνδεσης.");
        }
        System.out.println("Το κουμπί πατήθηκε!");

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
