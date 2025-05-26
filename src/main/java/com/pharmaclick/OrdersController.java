package com.pharmaclick;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;

import java.io.IOException;
import java.sql.*;

import com.pharmaclick.Booking;

public class OrdersController {

    @FXML
    private Label ordersCountLabel;

    @FXML
    private VBox OrdersVBox;

    private int pharmacyId;

    public void setPharmacyId(int pharmacyId) {
        this.pharmacyId = pharmacyId;
        System.out.println("Pharmacy ID received: " + pharmacyId);
        loadBookingsForPharmacy();
    }

    @FXML
    private void initialize() {
        System.out.println("OrdersController initialized successfully!");
    }

    @FXML
    private void backprofileclicked(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/pharmacy_profile.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 350, 600);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadBookingsForPharmacy() {
        ObservableList<Booking> bookings = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/pharmaclick", "root", "1111");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM bookings WHERE pharmacy_id = ?")) {

            stmt.setInt(1, pharmacyId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Booking booking = new Booking(
    rs.getInt("id"),
    rs.getInt("pharmacy_id"),
    rs.getString("customer_name"),
    rs.getString("customer_address"),
    rs.getString("customer_phone"),
    rs.getString("customer_email"),
    rs.getString("customer_amka"),
    rs.getString("comment"),
    rs.getDate("pickup_date"),
    rs.getDouble("total_price"),
    rs.getString("status") // ✅ νέο πεδίο
);

                bookings.add(booking);
            }

            ordersCountLabel.setText(bookings.size() + " κρατήσεις");

            for (Booking booking : bookings) {
                OrdersVBox.getChildren().add(createBookingBox(booking));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private HBox createBookingBox(Booking booking) {
    HBox hbox = new HBox(10);
    hbox.setStyle("-fx-background-color: #E8F0F2; -fx-border-color: #B0C4DE; -fx-border-radius: 10; -fx-background-radius: 10;");
    hbox.setPrefHeight(120);
    hbox.setPrefWidth(300);
    hbox.setPadding(new Insets(10));

    // Avatar
    ImageView avatar = new ImageView(new Image(getClass().getResource("/images/circle (1).png").toExternalForm()));
    avatar.setFitHeight(50);
    avatar.setFitWidth(50);

    // Πληροφορίες
    VBox vbox = new VBox(5);
    Label nameLabel = new Label("👤 Όνομα: " + booking.getCustomerName());
    Label amkaLabel = new Label("🆔 ΑΜΚΑ: " + booking.getCustomerAmka());
    Label bookingIdLabel = new Label("🔢 No: " + booking.getId());
    Label dateLabel = new Label("📅 Ημ/νία: " + booking.getPickupDate());
    Label statusLabel = new Label("📦 Κατάσταση: " + booking.getStatus());

    nameLabel.setStyle("-fx-font-weight: bold;");
    statusLabel.setStyle("-fx-text-fill: #2A7F62; -fx-font-style: italic;");

    vbox.getChildren().addAll(nameLabel, amkaLabel, bookingIdLabel, dateLabel, statusLabel);
    vbox.setPrefWidth(200);

    // Clickable arrow
    ImageView arrow = new ImageView(new Image(getClass().getResource("/images/right-arrow (1).png").toExternalForm()));
    arrow.setFitHeight(25);
    arrow.setFitWidth(25);
    arrow.setOnMouseClicked(e -> {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/pharmacy_order_details.fxml"));
        Parent root = loader.load();

        PharmacyOrderDetailsController controller = loader.getController();
        controller.setBooking(booking);  // Περνάμε το αντικείμενο κράτησης

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 350, 600));
        stage.show();
    } catch (IOException ex) {
        ex.printStackTrace();
    }
});


    hbox.getChildren().addAll(avatar, vbox, arrow);
    return hbox;
}


@FXML
private void goBackToProfile(MouseEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/pharmacy_profile.fxml"));
        Parent root = loader.load();

        // Αν θέλεις να περάσεις pharmacyId:
        // PharmacyProfileController controller = loader.getController();
        // controller.setPharmacyId(this.pharmacyId);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 335, 600));
    } catch (IOException e) {
        e.printStackTrace();
    }
}

}
