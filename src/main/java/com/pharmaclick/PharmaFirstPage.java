package com.pharmaclick;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



public class PharmaFirstPage {

    private String pharmacyEmail;

    public void setPharmacyEmail(String email) {
        this.pharmacyEmail = email;
    }
    @FXML
    private ImageView profileIcon;

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
    @FXML public void goToCategoryB1(MouseEvent event) { goToAddFormWithCategory("Αναλγητικά και Αντιφλεγμονώδη", "/images/category1.png", event); }
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

@FXML
private void initialize() {
    profileIcon.setOnMouseClicked(event -> {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/pharmacy_profile.fxml"));

            Parent profileRoot = loader.load();
            PharmacyProfileController controller = loader.getController();
            controller.setPharmacyId(pharmacyId); // ✨ Περνάς το ID εδώ

            Scene profileScene = new Scene(profileRoot);
            Stage stage = (Stage) profileIcon.getScene().getWindow();
            stage.setScene(profileScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    });


// Φόρτωση κατηγοριών
    if (pharmacyEmail == null || pharmacyEmail.isEmpty()) {
        System.out.println("Pharmacy email is null or empty!");
        return;
    }
    List<Category> categories = loadCategoriesForPharmacy(pharmacyEmail);
    displayCategories(categories);
}

private List<Category> loadCategoriesForPharmacy(String pharmacyEmail) {
    List<Category> categories = new ArrayList<>();
    try (Connection conn = DatabaseConnection.getConnection()) {
        String sql = "SELECT name, description, image_url FROM categories WHERE pharmacy_name = ? OR is_global = 1";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, pharmacyEmail);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Category cat = new Category();
            cat.setName(rs.getString("name"));
            cat.setDescription(rs.getString("description"));
            cat.setImageUrl(rs.getString("image_url"));
            categories.add(cat);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return categories;
}

public static class Category {
    private String name;
    private String description;
    private String imageUrl;

    public Category() {}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}


@FXML
private TilePane categoriesTilePane;  // πρέπει να έχεις @FXML το TilePane από το FXML

private void displayCategories(List<Category> categories) {
    categoriesTilePane.getChildren().clear();

    for (Category category : categories) {
        VBox box = new VBox();
        box.setSpacing(5);
        box.setStyle("-fx-background-color: #f9f9f9; -fx-padding: 10; -fx-background-radius: 10;");

        Label nameLabel = new Label(category.getName());
        nameLabel.setWrapText(true);
        nameLabel.setStyle("-fx-font-weight: bold; -fx-text-alignment: center;");

        ImageView imageView = new ImageView();
        imageView.setFitHeight(48);
        imageView.setFitWidth(48);
        Image image = new Image("/images/" + category.getImageUrl()); // προσαρμογή διαδρομής εικόνας
        imageView.setImage(image);

        box.getChildren().addAll(nameLabel, imageView);
        categoriesTilePane.getChildren().add(box);

        // Προαιρετικά: event handler για κλικ στην κατηγορία
        box.setOnMouseClicked(e -> {
            goToAddFormWithCategory(category.getName(), "/images/" + category.getImageUrl(), e);
        });
    }
}




private int pharmacyId;

public void setPharmacyId(int id) {
    this.pharmacyId = id;
}


}
