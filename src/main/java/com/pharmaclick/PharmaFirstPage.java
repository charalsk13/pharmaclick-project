package com.pharmaclick;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Node;

public class PharmaFirstPage {

    @FXML
    public void goToProfile() {
        System.out.println("Προφίλ χρήστη");
    }

    @FXML
    public void handleReservationTick() {
        System.out.println("Επιβεβαίωση κράτησης");
    }

    @FXML
    public void handleApprove() {
        System.out.println("Έγκριση αιτήματος");
    }

    @FXML
    public void handleReject() {
        System.out.println("Απόρριψη αιτήματος");
    }

    @FXML
    public void goToCategoryA(MouseEvent event) {
        goToAddFormWithCategory("Αναλγητικά και Αντιφλεγμονώδη", "/images/category1.png", event);
    }

    @FXML
    public void goToCategoryB(MouseEvent event) {
        goToAddFormWithCategory("Βιταμίνες και Συμπληρώματα", "/images/category2.png", event);
    }

    public void goToAddFormWithCategory(String categoryName, String iconPath, MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddMedicine.fxml"));
            Parent root = loader.load();

            AddMedicineController controller = loader.getController();
            controller.setCategory(categoryName, iconPath);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 350, 600);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goToCategoryC() {
        System.out.println("Σελίδα Γ");
    }
}
