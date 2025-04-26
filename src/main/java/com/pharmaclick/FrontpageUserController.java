package com.pharmaclick;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FrontpageUserController {

    @FXML
    private WebView mapView;

    @FXML
    private Button notificationsButton; // Το κουμπί με το φακελάκι!

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/NotificationsPage.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Ειδοποιήσεις");
        stage.setScene(new Scene(root));
        stage.show();
    } catch (Exception e) {
        System.out.println("⚠️ Σφάλμα ανοίγματος σελίδας ειδοποιήσεων: " + e.getMessage());
    }
}

}
