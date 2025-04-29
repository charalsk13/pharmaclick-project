package com.pharmaclick;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class PharmacyDetailsMapController {

    @FXML
    private Label pharmacyNameLabel;

    @FXML
    private Label pharmacyAddressLabel;

    @FXML
    private Label pharmacyPhoneLabel;

    @FXML
    private ImageView notificationsButton;
    
    @FXML
    private ImageView homeButton;
    
    public void setPharmacyDetails(String name, String address, String phone) {
        pharmacyNameLabel.setText(name);
        pharmacyAddressLabel.setText(address);
        pharmacyPhoneLabel.setText(phone);
    }

       @FXML
    private void openNotificationsPage(javafx.scene.input.MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/notifications_user.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println("⚠️ Σφάλμα ανοίγματος σελίδας ειδοποιήσεων: " + e.getMessage());
        }
    }

    @FXML
private void goToFrontpage(javafx.scene.input.MouseEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/frontpage_user.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    } catch (Exception e) {
        System.out.println("⚠️ Σφάλμα επιστροφής στην αρχική σελίδα: " + e.getMessage());
    }
}

}
