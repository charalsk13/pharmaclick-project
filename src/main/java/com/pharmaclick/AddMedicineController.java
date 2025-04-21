package com.pharmaclick;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

    private String pharmacyName;

    public void setCategory(String name, String iconPath) {
        categoryLabel.setText(name);
        categoryIcon.setImage(new Image(getClass().getResourceAsStream(iconPath)));
    }

    public void setPharmacyName(String name) {
        this.pharmacyName = name;
    }

    @FXML
    private void initialize() {
        quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 1));
        availabilityCombo.getItems().addAll("Διαθέσιμο", "Μη διαθέσιμο");
        availabilityCombo.getSelectionModel().selectFirst();
    }

    @FXML
    private void handleAddMedicine() {
        String name = nameField.getText();
        String description = descriptionField.getText();
        String form = formField.getText();
        int quantity = quantitySpinner.getValue() != null ? quantitySpinner.getValue() : 0;
        double price = Double.parseDouble(priceField.getText());
        String availability = availabilityCombo.getValue();
        String Code = CodeField.getText();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO medicines (pharmacy_name, name, description, form, quantity, price, availability, code) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, pharmacyName);
            stmt.setString(2, name);
            stmt.setString(3, description);
            stmt.setString(4, form);
            stmt.setInt(5, quantity);
            stmt.setDouble(6, price);
            stmt.setString(7, availability);
            stmt.setString(8, Code);
            stmt.executeUpdate();

            System.out.println("✅ Φάρμακο προστέθηκε: " + name);

        } catch (SQLException e) {
            System.out.println("❌ Σφάλμα κατά την προσθήκη: " + e.getMessage());
        }
    }
}

