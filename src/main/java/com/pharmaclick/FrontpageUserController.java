package com.pharmaclick;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

public class FrontpageUserController {

    @FXML
    private WebView mapView;

    @FXML
    private ImageView notificationsButton;


    @FXML
    public void initialize() {
        try {
            var url = getClass().getResource("/views/map.html");
            if (url != null) {
                mapView.getEngine().load(url.toExternalForm());
                System.out.println("✅ Χάρτης φορτώθηκε");
            } else {
                System.out.println("❌ Δεν βρέθηκε το map.html");
            }
        } catch (Exception e) {
            System.out.println("⚠️ Σφάλμα φόρτωσης χάρτη: " + e.getMessage());
        }
    }

    @FXML
private void openNotificationsPage(javafx.scene.input.MouseEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/notifications_user.fxml"));
        Parent root = loader.load();

        // Παίρνουμε το ΙΔΙΟ παράθυρο και αλλάζουμε Scene
        Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    } catch (Exception e) {
        System.out.println("⚠️ Σφάλμα ανοίγματος σελίδας ειδοποιήσεων: " + e.getMessage());
    }
}


}
