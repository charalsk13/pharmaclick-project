package com.pharmaclick;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class CartController implements Initializable {

    @FXML private VBox cartVBox;

    @FXML private Label totalLabel;

    @FXML private TextField commentField;

    @FXML private DatePicker pickupDate;

    @FXML private Button confirmBookingButton;

    // Πελάτης (ενδεικτικά πεδία)
    @FXML private TextField customerNameField;
    @FXML private TextField customerAddressField;
    @FXML private TextField customerPhoneField;
    @FXML private TextField customerEmailField;
    @FXML private TextField customerAmkaField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Παράδειγμα δυναμικής προσθήκης φαρμάκων στο καλάθι
        cartVBox.getChildren().add(new Label("Παρακεταμόλη - 5.00 €"));
        cartVBox.getChildren().add(new Label("Depon - 3.50 €"));

        // Ενημέρωση συνολικού ποσού
        totalLabel.setText("8.50 €");

        // Προσθήκη ενεργειών (π.χ. επιβεβαίωση κράτησης)
        confirmBookingButton.setOnAction(e -> confirmBooking());
    }

    private void confirmBooking() {
        String name = customerNameField.getText();
        String address = customerAddressField.getText();
        String phone = customerPhoneField.getText();
        String email = customerEmailField.getText();
        String amka = customerAmkaField.getText();
        String comments = commentField.getText();
        String pickup = (pickupDate.getValue() != null) ? pickupDate.getValue().toString() : "Καμία ημερομηνία";

        System.out.println("✅ Νέα κράτηση από: " + name);
        System.out.println("📦 Σχόλια: " + comments + ", Ημερομηνία: " + pickup);
        // Εδώ μπορείς να προσθέσεις εισαγωγή σε βάση ή αποστολή email κλπ

        showAlert("Η κράτησή σας καταχωρήθηκε!");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Επιτυχία");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
