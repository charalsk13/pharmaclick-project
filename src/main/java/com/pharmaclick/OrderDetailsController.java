package com.pharmaclick;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.sql.*;

public class OrderDetailsController {

    @FXML private Label orderNumberLabel, orderDateLabel;
    @FXML private Label customerNameLabel, addressLabel, cityLabel, countryLabel, postalCodeLabel, phoneLabel;
    @FXML private Label pharmacyNameLabel, pharmacyAddressLabel, pharmacyCityLabel, pharmacyCountryLabel, pharmacyPostalCodeLabel, pharmacyPhoneLabel;
    @FXML private Label statusLabel, deliveryDateLabel, productNameLabel, productDescriptionLabel, productPriceLabel;
    @FXML private ProgressBar progressBar;
    @FXML private Button backButton;
    @FXML private ImageView productImage;

    private Connection connect() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    public void loadOrderDetails(int orderId) {
        try (Connection conn = connect()) {
            String sql = "SELECT * FROM orders WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                // Παραγγελία
                orderNumberLabel.setText("Κωδικός: " + rs.getInt("id"));
                orderDateLabel.setText("Ημ/νία: " + rs.getDate("order_date"));
    
                // Πελάτης
                customerNameLabel.setText("Όνομα: " + rs.getString("customer_name"));
                addressLabel.setText("Διεύθυνση: " + rs.getString("customer_address"));
                cityLabel.setText("Πόλη: " + rs.getString("customer_city"));
                countryLabel.setText("Χώρα: " + rs.getString("customer_country"));
                postalCodeLabel.setText("ΤΚ: " + rs.getString("customer_postal"));
                phoneLabel.setText("Τηλέφωνο: " + rs.getString("customer_phone"));
    
                // Φαρμακείο
                pharmacyNameLabel.setText("Φαρμακείο: " + rs.getString("pharmacy_name"));
                pharmacyAddressLabel.setText("Διεύθυνση: " + rs.getString("pharmacy_address"));
                pharmacyCityLabel.setText("Πόλη: " + rs.getString("pharmacy_city"));
                pharmacyCountryLabel.setText("Χώρα: " + rs.getString("pharmacy_country"));
                pharmacyPostalCodeLabel.setText("ΤΚ: " + rs.getString("pharmacy_postal"));
                pharmacyPhoneLabel.setText("Τηλέφωνο: " + rs.getString("pharmacy_phone"));
    
                // Προϊόν
                productNameLabel.setText(rs.getString("product_name"));
                productDescriptionLabel.setText(rs.getString("product_description"));
                productPriceLabel.setText(rs.getBigDecimal("product_price") + " €");
    
                // Κατάσταση και πρόοδος
                statusLabel.setText(rs.getString("status"));
                deliveryDateLabel.setText("Ημ/νία Παράδοσης: " + rs.getDate("delivery_date"));
                progressBar.setProgress(rs.getDouble("progress"));
            } else {
                customerNameLabel.setText("Δεν βρέθηκε παραγγελία.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            customerNameLabel.setText("Σφάλμα βάσης.");
        }
    }

    @FXML
private void goBack() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Customer_orders.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Οι Κρατήσεις μου");
        stage.setScene(new Scene(root));
        stage.show();

        Stage currentStage = (Stage) backButton.getScene().getWindow();
        currentStage.close();

    } catch (IOException e) {
        e.printStackTrace();
    }
}

}