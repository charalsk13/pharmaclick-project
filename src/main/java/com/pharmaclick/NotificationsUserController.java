package com.pharmaclick;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;




public class NotificationsUserController {
    @FXML
    private VBox notificationsVBox;
    
    @FXML
    public void initialize() {
        loadNotifications();
    }
    
    private void loadNotifications() {
        notificationsVBox.getChildren().clear();
        String email = Session.getLoggedInEmail();
    
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT message, created_at FROM notifications WHERE user_email = ? ORDER BY created_at DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
    
            while (rs.next()) {
                String message = rs.getString("message");
                String timestamp = rs.getString("created_at");
    
                // Δημιουργία VBox για κάθε ειδοποίηση
                VBox notifBox = new VBox(4);
                notifBox.setStyle("-fx-background-color: #EAF6FF; -fx-padding: 10; -fx-background-radius: 8;");
    
                Label messageLabel = new Label(message);
                messageLabel.setWrapText(true);
                messageLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #333333;");
    
                Label dateLabel = new Label(timestamp);
                dateLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #666666;");
    
                notifBox.getChildren().addAll(messageLabel, dateLabel);
                notificationsVBox.getChildren().add(notifBox);
            }
    
            if (notificationsVBox.getChildren().isEmpty()) {
                Label noNotif = new Label("Δεν υπάρχουν ειδοποιήσεις.");
                noNotif.setStyle("-fx-text-fill: gray; -fx-padding: 15;");
                notificationsVBox.getChildren().add(noNotif);
            }
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    @FXML
    private void goBack(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/frontpage_user.fxml"));
            Parent root = loader.load();

            // Παίρνουμε το ΙΔΙΟ παράθυρο και αλλάζουμε Scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println("⚠️ Σφάλμα επιστροφής στη σελίδα χρήστη: " + e.getMessage());
        }
    }
}
