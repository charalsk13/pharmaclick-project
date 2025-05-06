package com.pharmaclick;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.scene.control.Alert;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Locale;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import netscape.javascript.JSObject;
import javafx.concurrent.Worker;
import com.pharmaclick.DatabaseConnection;
import java.net.URL;
import javafx.scene.input.MouseEvent;


public class FrontpageUserController {

    @FXML
    private WebView mapView;

    @FXML
    private ImageView notificationsButton;

    private int currentUserId;

    public void setUserId(int userId) {
    this.currentUserId = userId;
    }


    @FXML
    public void initialize() {
        try {
            URL url = getClass().getResource("/views/map.html");
            if (url != null) {
                mapView.getEngine().load(url.toExternalForm());
                System.out.println("✅ Χάρτης φορτώθηκε");

                mapView.getEngine().getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
                    if (newState == Worker.State.SUCCEEDED) {
                        JSObject window = (JSObject) mapView.getEngine().executeScript("window");
                        window.setMember("javaApp", new JSBridge(this));
                        System.out.println("✅ Η javaApp είναι τώρα διαθέσιμη στο JavaScript");
                
                        String pharmaciesJson = loadPharmaciesFromDatabase();
                        mapView.getEngine().executeScript("window.setPharmacies(" + pharmaciesJson + ");");
                    }
                });
                
            } else {
                System.out.println("❌ Δεν βρέθηκε το map.html");
                showAlert("Δεν βρέθηκε το αρχείο map.html");
            }
        } catch (Exception e) {
            System.out.println("⚠️ Σφάλμα φόρτωσης χάρτη: " + e.getMessage());
            showAlert("Σφάλμα φόρτωσης χάρτη: " + e.getMessage());
        }
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
            showAlert("Σφάλμα ανοίγματος σελίδας ειδοποιήσεων: " + e.getMessage());
        }
    }

    public void openPharmacyDetails(String name) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM pharmacies WHERE name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                int id = rs.getInt("id");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
    
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/PharmacyDetailsMap.fxml"));
                Parent root = loader.load();
    
                PharmacyDetailsMapController controller = loader.getController();
                if (controller != null) {
                    Pharmacy pharmacy = new Pharmacy(id, name, address, phone);
                    controller.setUserId(currentUserId);           // ✅ Πέρασμα χρήστη
                    controller.setPharmacyDetails(pharmacy);       // ✅ Πέρασμα φαρμακείου
                }
                
    
                Stage stage = (Stage) mapView.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                showAlert("Το φαρμακείο δεν βρέθηκε στη βάση.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Σφάλμα φόρτωσης στοιχείων φαρμακείου: " + e.getMessage());
        }
    }
    
    

    public String loadPharmaciesFromDatabase() {
        StringBuilder pharmaciesJson = new StringBuilder("[");
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT name, address, phone, latitude, longitude FROM pharmacies";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                double latitude = rs.getDouble("latitude");
                double longitude = rs.getDouble("longitude");
            
                // Αν κάποια συντεταγμένη είναι NULL ή μηδέν, αγνόησέ το
                if (rs.wasNull() || latitude == 0.0 || longitude == 0.0) {
                    continue;
                }
            
                String name = rs.getString("name");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
            
               pharmaciesJson.append(String.format(
        Locale.US, // 👈 ΕΔΩ!
        "{\"name\":\"%s\", \"coords\":[%f, %f], \"info\":\"%s, Τηλ: %s\"},",
        escapeJson(name), latitude, longitude, escapeJson(address), phone
    ));
            }
            
            
            if (pharmaciesJson.length() > 1 && pharmaciesJson.charAt(pharmaciesJson.length() - 1) == ',') {
                pharmaciesJson.deleteCharAt(pharmaciesJson.length() - 1); // Αφαίρεση τελευταίου κόμματος
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        pharmaciesJson.append("]");
        System.out.println("DEBUG JSON:\n" + pharmaciesJson.toString());
        return pharmaciesJson.toString();
    }

    private String escapeJson(String s) {
        return s.replace("\"", "\\\"");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Πληροφορία");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
private void handleCartClick(MouseEvent event) {
    try {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/cart.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Καλάθι Κράτησης");
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

}
