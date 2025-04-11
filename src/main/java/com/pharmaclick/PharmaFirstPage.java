
package com.pharmaclick;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

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
        System.out.println("Σελίδα Α");
    }

    @FXML
    public void goToCategoryB() {
        System.out.println("Σελίδα Β");
    }

    @FXML
    public void goToCategoryC() {
        System.out.println("Σελίδα Γ");
    }
}
