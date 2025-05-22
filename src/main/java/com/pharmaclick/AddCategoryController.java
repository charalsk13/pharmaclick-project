package com.pharmaclick;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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

  
    private File selectedImageFile = null;

    @FXML
public void handleChooseImage() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Επιλογή εικόνας");
    fileChooser.getExtensionFilters().add(
        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
    );
    File file = fileChooser.showOpenDialog(imagePreview.getScene().getWindow());
    if (file != null) {
        selectedImageFile = file;
        imagePreview.setImage(new Image(file.toURI().toString()));
    }
}

private String copyImageToImagesFolder(File imageFile) throws IOException {
    String imagesDirPath = "images"; // φάκελος στην ρίζα του project ή εφαρμογής
    File imagesDir = new File(imagesDirPath);
    if (!imagesDir.exists()) {
        imagesDir.mkdirs();
    }

    String newFileName = System.currentTimeMillis() + "_" + imageFile.getName();
    File destFile = new File(imagesDir, newFileName);

    Files.copy(imageFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    System.out.println("Image copied to: " + destFile.getAbsolutePath());

    return newFileName;  // επιστρέφει το νέο όνομα για αποθήκευση στη βάση
}


@FXML
public void handleAddCategory() {
    System.out.println("▶ Πατήθηκε το κουμπί Προσθήκη");
    String name = categoryNameField.getText();
    String description = descriptionField.getText();
    String imageName = "foto.png";

    if (name.isEmpty()) {
        System.out.println("Το όνομα κατηγορίας είναι υποχρεωτικό.");
        return;
    }

    if (selectedImageFile != null) {
        try {
            imageName = copyImageToImagesFolder(selectedImageFile);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Σφάλμα κατά την αποθήκευση της εικόνας.");
            return;
        }
    }

    boolean isGlobal = pharmacyName == null || pharmacyName.isEmpty(); // ✔ Αν δεν έχει οριστεί φαρμακείο, είναι global

System.out.println("=== Debug AddCategory ===");
System.out.println("Name: " + name);
System.out.println("Description: " + description);
System.out.println("Image: " + imageName);
System.out.println("PharmacyName: " + pharmacyName);
System.out.println("Is Global: " + isGlobal);

    try (Connection conn = DatabaseConnection.getConnection()) {
        String sql = "INSERT INTO categories (name, description, image_url, pharmacy_name, is_global) VALUES (?, ?, ?, ?, ?)";
        var stmt = conn.prepareStatement(sql);
        stmt.setString(1, name);
        stmt.setString(2, description);
        stmt.setString(3, imageName);
        stmt.setString(4, isGlobal ? "" : pharmacyName);
        stmt.setBoolean(5, isGlobal);

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
