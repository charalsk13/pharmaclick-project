package com.pharmaclick;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;
import java.sql.Connection;

public class AddCategoryController {

    private String pharmacyName;

    public void setPharmacyName(String name) {
        this.pharmacyName = name;
    }

    @FXML
    private TextField categoryNameField;

    @FXML
    private TextField descriptionField;

    @FXML
    private ImageView imagePreview;

    @FXML
    private Button addButton;

    @FXML
    public void handleChooseImage() {
        System.out.println("Επιλέχθηκε εικόνα για την κατηγορία.");
        // Προαιρετικά: FileChooser εδώ
    }

    @FXML
public void handleAddCategory() {
    System.out.println("▶ Πατήθηκε το κουμπί Προσθήκη");
    String name = categoryNameField.getText();
    String description = descriptionField.getText();
    String image = "foto.png";

    if (name.isEmpty() || pharmacyName == null || pharmacyName.isEmpty()) {
        System.out.println("Το όνομα κατηγορίας και το φαρμακείο είναι υποχρεωτικά.");
        return;
    }

    try (Connection conn = DatabaseConnection.getConnection()) {
        String sql = "INSERT INTO categories (name, description, image_url, pharmacy_name) VALUES (?, ?, ?, ?)";
        var stmt = conn.prepareStatement(sql);
        stmt.setString(1, name);
        stmt.setString(2, description);
        stmt.setString(3, image);
        stmt.setString(4, pharmacyName);

        int rows = stmt.executeUpdate();
        if (rows > 0) {
            System.out.println("✅ Η κατηγορία προστέθηκε επιτυχώς.");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}


    @FXML
    public void goBackToHome(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/pharma_firstpage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 350, 600));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
