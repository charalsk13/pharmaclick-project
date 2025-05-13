package com.pharmaclick;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.scene.image.ImageView;




public class CartController implements Initializable {

    @FXML private VBox cartVBox;


    @FXML private Label totalLabel;

    @FXML private TextField commentField;

    @FXML private DatePicker pickupDate;

    @FXML private Button confirmBookingButton;

    @FXML private Label pharmacyNameLabel;
    @FXML private Label pharmacyAddressLabel;
    @FXML private Label pharmacyPhoneLabel;
    @FXML private Label pharmacyEmailLabel;
    @FXML private Label pharmacyHoursLabel;

    @FXML private TextField customerNameField;
    @FXML private TextField customerAddressField;
    @FXML private TextField customerPhoneField;
    @FXML private TextField customerEmailField;
    @FXML private TextField customerAmkaField;
    @FXML private ImageView homeButton;

    private int pharmacyId;
    private double total = 0.0;



    
@Override
public void initialize(URL location, ResourceBundle resources) {
    List<CartItem> items = CartManager.getInstance().getItems();
    cartVBox.getChildren().clear();
    total = 0.0;

    for (CartItem item : items) {
        HBox itemBox = createCartItemBox(item);
        cartVBox.getChildren().add(itemBox);
        total += item.getMedicine().getPrice() * item.getQuantity();
    }

    totalLabel.setText(String.format("%.2f €", total));

    // ❗ Πάρε το pharmacyId από το πρώτο φάρμακο στο καλάθι
  if (!items.isEmpty()) {
    this.pharmacyId = items.get(0).getMedicine().getPharmacyId();
    loadPharmacyInfoById(this.pharmacyId);
}


    confirmBookingButton.setOnAction(e -> confirmBooking());
}



public void loadPharmacyInfoById(int id) {
    try (Connection conn = DatabaseConnection.getConnection()) {
        String sql = "SELECT name, address, phone, email, hours FROM pharmacies WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            pharmacyNameLabel.setText(rs.getString("name"));
            pharmacyAddressLabel.setText(rs.getString("address"));
            pharmacyPhoneLabel.setText(rs.getString("phone"));
            pharmacyEmailLabel.setText(rs.getString("email"));
            pharmacyHoursLabel.setText(rs.getString("hours"));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}



private void confirmBooking() {
    String name = customerNameField.getText();
    String address = customerAddressField.getText();
    String phone = customerPhoneField.getText();
    String email = customerEmailField.getText();
    String amka = customerAmkaField.getText();
    String comments = commentField.getText();
    String pickupDateStr = (pickupDate.getValue() != null) ? pickupDate.getValue().toString() : null;

    List<CartItem> items = CartManager.getInstance().getItems();

    if (items.isEmpty()) {
        showAlert("⚠️ Το καλάθι είναι άδειο.");
        return;
    }

    String sql = "INSERT INTO bookings (pharmacy_id, customer_name, customer_address, customer_phone, customer_email, customer_amka, comment, pickup_date, total_price, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

        stmt.setInt(1, pharmacyId);
        stmt.setString(2, name);
        stmt.setString(3, address);
        stmt.setString(4, phone);
        stmt.setString(5, email);
        stmt.setString(6, amka);
        stmt.setString(7, comments);
        if (pickupDateStr != null)
            stmt.setDate(8, java.sql.Date.valueOf(pickupDateStr));
        else
            stmt.setNull(8, java.sql.Types.DATE);
        stmt.setDouble(9, total);
        stmt.setString(10, "pending");

        int rows = stmt.executeUpdate();
        if (rows == 0) {
            showAlert("⚠️ Κάτι πήγε στραβά. Δοκιμάστε ξανά.");
            return;
        }

        ResultSet generatedKeys = stmt.getGeneratedKeys();
        int bookingId = -1;
        if (generatedKeys.next()) {
            bookingId = generatedKeys.getInt(1);
        }

        // ➕ Αποθήκευση των φαρμάκων στην booking_items
        String insertItemSql = "INSERT INTO booking_items (booking_id, medicine_id, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement itemStmt = conn.prepareStatement(insertItemSql)) {
            for (CartItem item : items) {
                itemStmt.setInt(1, bookingId);
                itemStmt.setInt(2, item.getMedicine().getId());
                itemStmt.setInt(3, item.getQuantity());
                itemStmt.addBatch();
            }
            itemStmt.executeBatch();
        }

        showAlert("✅ Η κράτησή σας καταχωρήθηκε με επιτυχία!");
        CartManager.getInstance().clearCart(); // καθάρισε το καλάθι

        // (προαιρετικά) redirect στην αρχική ή άλλη σελίδα

    } catch (Exception e) {
        e.printStackTrace();
        showAlert("⚠️ Σφάλμα κατά την αποθήκευση: " + e.getMessage());
    }
}


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Επιτυχία");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

   

public void setPharmacyDetails(int id, String name, String address, String phone, String email, String hours) {
    this.pharmacyId = id;
    pharmacyNameLabel.setText(name);
    pharmacyAddressLabel.setText(address);
    pharmacyPhoneLabel.setText(phone);
    pharmacyEmailLabel.setText(email);
    pharmacyHoursLabel.setText(hours);
}







private HBox createCartItemBox(CartItem item) {
    Label nameLabel = new Label(item.getMedicine().getName());
    nameLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: normal;");

    Label quantityLabel = new Label("x" + item.getQuantity());
    quantityLabel.setStyle("-fx-font-size: 13px;");

    Label priceLabel = new Label(String.format("%.2f €", item.getMedicine().getPrice() * item.getQuantity()));
    priceLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #003344;");

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    HBox box = new HBox(nameLabel, quantityLabel, spacer, priceLabel);
    box.setSpacing(8);
    box.setPadding(new Insets(6, 10, 6, 10));
    box.setAlignment(Pos.CENTER_LEFT);
    box.setStyle("-fx-background-color: #D7E6EC;");

    return box;
}

@FXML private VBox pharmacyInfoBox;

@FXML
private void togglePharmacyInfo() {
    boolean currentlyVisible = pharmacyInfoBox.isVisible();
    pharmacyInfoBox.setVisible(!currentlyVisible);
    pharmacyInfoBox.setManaged(!currentlyVisible); // για να μην κρατάει κενό όταν είναι κρυφό
}

@FXML
private void goToHome() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/frontpage_user.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) homeButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    } catch (Exception e) {
        e.printStackTrace();
    }
}


}