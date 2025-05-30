package com.pharmaclick;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class FavoritesController {
    private int userId;
    
    @FXML
    private AnchorPane rootPane;
    
    @FXML
    private Button backButton;
    
    @FXML
    private Label deliveryDateLabel1;
    
    @FXML
    private Label medicineDescriptionLabel;

    @FXML
    private Label imageView1;
    
    private Connection connect() throws SQLException {
        String url = "jdbc:mariadb://localhost:3306/pharmaclick_new";
        String user = "root";
        String password = "123";
        return DriverManager.getConnection(url, user, password);
    }

    public void setUserId(int userId) {
        this.userId = userId;
        loadFavorites();
    }
    
    private void loadFavorites() {
        try (Connection conn = connect()) {
            String sql = "SELECT m.name, m.description " +
                         "FROM favorites f " +
                         "JOIN medicines m ON f.medicine_id = m.id " +
                         "WHERE f.user_id = ? " +
                         "LIMIT 2";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                deliveryDateLabel1.setText(rs.getString("name"));
                medicineDescriptionLabel.setText(rs.getString("description"));
                
            }

        } catch (SQLException e) {
            e.printStackTrace();
            deliveryDateLabel1.setText("Σφάλμα φόρτωσης");
            medicineDescriptionLabel.setText("");
        }
    }
    
    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/User_profile.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("User Profile");
            stage.setScene(new Scene(root));
            stage.show();

            Stage currentStage = (Stage) backButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}