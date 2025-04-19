

package com.pharmaclick;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AddMedicineController {

    @FXML
    private Label categoryLabel;

    @FXML
    private ImageView categoryIcon;

    @FXML
    private TextField drugNameField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField formField;

    @FXML
    private Spinner<Integer> quantitySpinner;

    @FXML
    private TextField priceField;

    @FXML
    private ComboBox<String> availabilityCombo;

    @FXML
    private TextField drugCodeField;

    public void setCategory(String name, String iconPath) {
        categoryLabel.setText(name);
        categoryIcon.setImage(new Image(getClass().getResourceAsStream(iconPath)));
    }

    @FXML
    private void handleAddMedicine() {
        // Εδώ μπαίνει ο κώδικας για την προσθήκη του φαρμάκου
        System.out.println("Φάρμακο προστέθηκε: " + drugNameField.getText());
    }
} 