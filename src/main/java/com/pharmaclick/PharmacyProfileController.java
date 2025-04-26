package com.pharmaclick;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Node;


public class PharmacyProfileController {

    @FXML
    private void initialize() {
        // Αν θες να κάνεις κάτι όταν ανοίγει η σελίδα
    }

    @FXML
    private void handleBookingsClick(MouseEvent event) {
        System.out.println("Πατήθηκαν οι Κρατήσεις!");
    }

    @FXML
    private void handleHelpClick(MouseEvent event) {
        System.out.println("Χρειάζεσαι βοήθεια!");
    }

    @FXML
    private void handleInfoClick(MouseEvent event) {
        System.out.println("Πληροφορίες!");
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

}
