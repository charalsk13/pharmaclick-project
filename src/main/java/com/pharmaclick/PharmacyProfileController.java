package com.pharmaclick;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.control.TableView;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;




public class PharmacyProfileController {

    private int pharmacyId;

    @FXML
    private void initialize() {
        // Αν θες να κάνεις κάτι όταν ανοίγει η σελίδα
    }



    @FXML
    private void handleHelpClick(MouseEvent event) {
        System.out.println("Χρειάζεσαι βοήθεια!");
    }

   @FXML
private void handleInfoClick(MouseEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/pharmacy_myInfo.fxml"));
        Parent root = loader.load();

        // Πέρνα το ID στον controller
        PharmacyMyInfoController controller = loader.getController();
        controller.setPharmacyId(this.pharmacyId);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 350, 600);
        stage.setScene(scene);
    } catch (IOException e) {
        e.printStackTrace();
    }
}



    @FXML
    private void handleNotificationsClick(MouseEvent event) {
        System.out.println("Ειδοποιήσεις!");
    }

    @FXML
    private void handlePrivacyOptClick(MouseEvent event) {
        System.out.println("Privacy Options!");
    }

    @FXML
    private void handleLogoutClick(MouseEvent event) {
        System.out.println("Αποσύνδεση!");
    }

    @FXML
    private void goBackHome(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/pharma_firstpage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 350, 600);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


@FXML
private void handleBookingsArrowClick(MouseEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/bookings.fxml"));
        Parent root = loader.load();

        // Από εδώ περνάς το pharmacyId αν το έχεις
        OrdersController controller = loader.getController(); 

        controller.setPharmacyId(this.pharmacyId); 

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

public void setPharmacyId(int pharmacyId) {
    this.pharmacyId = pharmacyId;
}


}
