package com.pharmaclick;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Node;

public class PharmaFirstPage {

    private String pharmacyEmail;

    public void setPharmacyEmail(String email) {
        this.pharmacyEmail = email;
    }

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
    public void goToAddForm(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddMedicine.fxml"));
            Parent root = loader.load();

            AddMedicineController controller = loader.getController();
            controller.setPharmacyName(pharmacyEmail);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 350, 600);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleConfirmReservation1() {
        System.out.println("Επιβεβαιώθηκε η κράτηση 1");
    }

    @FXML
    public void handleConfirmReservation2() {
        System.out.println("Επιβεβαιώθηκε η κράτηση 2");
    }

    @FXML
    public void handleApproveRequest1() {
        System.out.println("Εγκρίθηκε το αίτημα 1");
    }

    @FXML
    public void handleRejectRequest1() {
        System.out.println("Απορρίφθηκε το αίτημα 1");
    }

    @FXML
    public void handleApproveRequest2() {
        System.out.println("Εγκρίθηκε το αίτημα 2");
    }

    @FXML
    public void handleRejectRequest2() {
        System.out.println("Απορρίφθηκε το αίτημα 2");
    }

    // Για κάθε κατηγορία
    public void goToAddFormWithCategory(String categoryName, String iconPath, MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddMedicine.fxml"));
            Parent root = loader.load();

            AddMedicineController controller = loader.getController();
            controller.setCategory(categoryName, iconPath);
            controller.setPharmacyName(pharmacyEmail);  // περνάει το email

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 350, 600);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML public void goToCategoryA1(MouseEvent event) { goToAddFormWithCategory("Αναλγητικά και Αντιφλεγμονώδη", "/images/category1.png", event); }
    @FXML public void goToCategoryB2(MouseEvent event) { goToAddFormWithCategory("Βιταμίνες και Συμπληρώματα", "/images/category2.png", event); }
    @FXML public void goToCategoryA2(MouseEvent event) { goToAddFormWithCategory("Βιταμίνες και Συμπληρώματα", "/images/category2.png", event); }
    @FXML public void goToCategoryA3(MouseEvent event) { goToAddFormWithCategory("Κρυολογήματα και Γρίπη", "/images/category3.png", event); }
    @FXML public void goToCategoryB3(MouseEvent event) { goToAddFormWithCategory("Κρυολογήματα και Γρίπη", "/images/category3.png", event); }
    @FXML public void goToCategoryA4(MouseEvent event) { goToAddFormWithCategory("Βρεφικά Προϊόντα", "/images/category4.png", event); }
    @FXML public void goToCategoryB4(MouseEvent event) { goToAddFormWithCategory("Βρεφικά Προϊόντα", "/images/category4.png", event); }
    @FXML public void goToCategoryA5(MouseEvent event) { goToAddFormWithCategory("Δερματολογικά", "/images/category5.png", event); }
    @FXML public void goToCategoryB5(MouseEvent event) { goToAddFormWithCategory("Δερματολογικά", "/images/category5.png", event); }
    @FXML public void goToCategoryA6(MouseEvent event) { goToAddFormWithCategory("Ερωτική Υγεία και Προφυλάξεις", "/images/category6.png", event); }
    @FXML public void goToCategoryB6(MouseEvent event) { goToAddFormWithCategory("Ερωτική Υγεία και Προφυλάξεις", "/images/category6.png", event); }

    @FXML
    public void goToCategoryC(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddCategory.fxml"));
            Parent root = loader.load();
    
            // Περνάμε το email στο controller της φόρμας
            AddCategoryController controller = loader.getController();
            controller.setPharmacyName(pharmacyEmail);
    
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 350, 600));
            stage.setTitle("Προσθήκη Κατηγορίας");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

@FXML
public void goBackToHome(ActionEvent event) {
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
