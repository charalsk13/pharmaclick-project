package com.pharmaclick;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.stage.Stage;

import com.pharmaclick.models.Booking;

import java.io.IOException;
import java.sql.*;

public class PharmacyOrderDetailsController {

    @FXML private Label orderNumberLabel;
    @FXML private Label orderDateLabel;
    @FXML private Label nameLabel;
    @FXML private Label addressLabel;
    @FXML private Label cityLabel;
    @FXML private Label countryLabel;
    @FXML private Label tkLabel;
    @FXML private Label phoneLabel;
    @FXML private Label amkaLabel;
    @FXML private VBox medicineItemsBox;

    public void setBooking(Booking booking) {
        orderNumberLabel.setText(String.valueOf(booking.getId()));
        orderDateLabel.setText(booking.getPickupDate().toString());
        nameLabel.setText("Όνομα: " + booking.getCustomerName());
        addressLabel.setText("Διεύθυνση: " + booking.getCustomerAddress());
        phoneLabel.setText("Τηλέφωνο: " + booking.getCustomerPhone());
        amkaLabel.setText(booking.getCustomerAmka());

        // Dummy placeholders αν δεν έχεις τα άλλα
        cityLabel.setText("city");
        countryLabel.setText("country");
        tkLabel.setText("TK");

        loadBookingItems(booking.getId());
    }

    private void loadBookingItems(int bookingId) {
        medicineItemsBox.getChildren().clear();
        try (Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/pharmaclick", "pharmaclick", "1111")) {
            String sql = """
                SELECT m.name, m.description, m.price, bi.quantity
                FROM booking_items bi
                JOIN medicines m ON bi.medicine_id = m.id
                WHERE bi.booking_id = ?
            """;

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");

                VBox itemBox = createMedicineBox(name, description, quantity, price);
                medicineItemsBox.getChildren().add(itemBox);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private VBox createMedicineBox(String name, String description, int quantity, double price) {
        VBox medicineBox = new VBox(6);
        medicineBox.setStyle("-fx-background-color: white; -fx-border-radius: 10px; -fx-background-radius: 10px; "
                + "-fx-padding: 12px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 6, 0.0, 0, 2);");
        medicineBox.setPrefWidth(290);
        medicineBox.setPadding(new Insets(12));

        Label nameLabel = new Label("💊 " + name);
        nameLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

        Label descLabel = new Label(description);
        descLabel.setStyle("-fx-font-size: 13px;");

        Label quantityLabel = new Label("Ποσότητα: " + quantity);
        quantityLabel.setStyle("-fx-font-size: 13px;");

        Label priceLabel = new Label(String.format("Τιμή: %.2f €", price));
        priceLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #2e8b57;");

        medicineBox.getChildren().addAll(nameLabel, descLabel, quantityLabel, priceLabel);
        return medicineBox;
    }

    @FXML
    private void handleBackToOrdersClick(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/bookings.fxml"));
            Parent root = loader.load();

            OrdersController controller = loader.getController();
            controller.setPharmacyId(Session.getLoggedInPharmacyId());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 350, 600));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
