
package com.pharmaclick;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.scene.layout.Region;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddMedicineController {

    @FXML private Label categoryLabel;
    @FXML private ImageView categoryIcon;
    @FXML private TextField nameField;
    @FXML private TextField descriptionField;
    @FXML private TextField formField;
    @FXML private Spinner<Integer> quantitySpinner;
    @FXML private TextField priceField;
    @FXML private ComboBox<String> availabilityCombo;
    @FXML private TextField CodeField;

   

private int selectedCategoryId;
private int pharmacyId; // όχι το name


    
public void setCategory(String name, String iconPath) {
    this.selectedCategoryId = findCategoryIdByName(name);
    categoryLabel.setText(name);
    categoryIcon.setImage(new Image(getClass().getResourceAsStream(iconPath)));

    System.out.println("✅ Category set: " + name + " → id=" + selectedCategoryId);

    if (selectedCategoryId > 0 && pharmacyId > 0) {
        System.out.println("➡️ Load medicines για pharmacyId: " + pharmacyId + ", categoryId: " + selectedCategoryId);
        loadMedicines();
    }
}


private int findCategoryIdByName(String name) {
    try (Connection conn = DatabaseConnection.getConnection()) {
        String sql = "SELECT id FROM categories WHERE name = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, name);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("id");
        }
    } catch (SQLException e) {
        System.err.println("❌ Σφάλμα κατά την αναζήτηση category id: " + e.getMessage());
    }
    return -1; // επιστρέφει -1 αν δεν βρεθεί
}




public void setPharmacy(int id) {
    this.pharmacyId = id;
    System.out.println("✅ Pharmacy ID set to: " + pharmacyId);

    if (selectedCategoryId > 0 && pharmacyId > 0) {
        System.out.println("➡️ Load medicines για pharmacyId: " + pharmacyId + ", categoryId: " + selectedCategoryId);
        loadMedicines();
    }
}




  @FXML
private void initialize() {
    quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 1));
    availabilityCombo.getItems().addAll("Διαθέσιμο", "Μη διαθέσιμο");
    availabilityCombo.getSelectionModel().selectFirst();

    
}



    @FXML
private VBox medicineListVBox;

public void loadMedicines() {
    medicineListVBox.getChildren().clear(); // καθάρισε τα προηγούμενα

    try (Connection conn = DatabaseConnection.getConnection()) {
        String sql = "SELECT name, description, price FROM medicines WHERE pharmacy_id = ? AND category_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, pharmacyId);
        stmt.setInt(2, selectedCategoryId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String name = rs.getString("name");
            String desc = rs.getString("description");
            double price = rs.getDouble("price");

            HBox medicineBox = new HBox(10);
            ImageView icon = new ImageView(new Image(getClass().getResourceAsStream("/images/category1.png")));
            icon.setFitWidth(20);
            icon.setFitHeight(20);

            VBox infoBox = new VBox(2);
            Label nameLabel = new Label(name);
            nameLabel.setStyle("-fx-font-weight: bold;");
            Label descLabel = new Label(desc);
            descLabel.setStyle("-fx-font-size: 11px;");
            descLabel.setPrefWidth(198);
            infoBox.getChildren().addAll(nameLabel, descLabel);

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            VBox priceBox = new VBox(2);
            Label priceLabel = new Label(String.format("%.2f €", price));
            Spinner<Integer> spinner = new Spinner<>(0, 99, 1);
            spinner.setPrefWidth(60);
            priceBox.setAlignment(Pos.CENTER_RIGHT);
            priceBox.getChildren().addAll(priceLabel, spinner);

            medicineBox.getChildren().addAll(icon, infoBox, spacer, priceBox);
            medicineListVBox.getChildren().add(medicineBox);
        }
    } catch (SQLException e) {
        System.out.println("❌ Σφάλμα κατά τη φόρτωση φαρμάκων: " + e.getMessage());
    }
    System.out.println("➡️ Load medicines για pharmacyId: " + pharmacyId + ", categoryId: " + selectedCategoryId);

}

    @FXML
private void handleAddMedicine() {
    String name = nameField.getText();
    String description = descriptionField.getText();
    String form = formField.getText();
    int quantity = quantitySpinner.getValue() != null ? quantitySpinner.getValue() : 0;
    double price;
    try {
        price = Double.parseDouble(priceField.getText());
    } catch (NumberFormatException e) {
        System.out.println("❌ Μη έγκυρη τιμή.");
        return;
    }
    String availability = availabilityCombo.getValue();
    String drug_code = CodeField.getText();

    if (name.isEmpty() || selectedCategoryId <= 0 || pharmacyId <= 0) {
        System.out.println("❌ Όλα τα πεδία είναι υποχρεωτικά");
        return;
    }

    try (Connection conn = DatabaseConnection.getConnection()) {
        String sql = "INSERT INTO medicines (pharmacy_id, name, description, form, quantity, price, availability, drug_code, category_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, pharmacyId);
        stmt.setString(2, name);
        stmt.setString(3, description);
        stmt.setString(4, form);
        stmt.setInt(5, quantity);
        stmt.setDouble(6, price);
        stmt.setString(7, availability);
        stmt.setString(8, drug_code);
        stmt.setInt(9, selectedCategoryId);
        stmt.executeUpdate();

        System.out.println("✅ Φάρμακο προστέθηκε: " + name);
        loadMedicines(); // ανανέωση λίστας
    } catch (SQLException e) {
        System.out.println("❌ Σφάλμα κατά την προσθήκη: " + e.getMessage());
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
