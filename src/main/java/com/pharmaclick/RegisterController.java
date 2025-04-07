package com.pharmaclick;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RegisterController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private TextField addressField;
    @FXML private TextField amkaField;
    @FXML private CheckBox customerCheckBox;
    @FXML private CheckBox pharmacistCheckBox;
    @FXML private Button registerButton;
    @FXML private Hyperlink loginLink;

    @FXML
    private void handleRegister() {
        String email = emailField.getText();
        String password = passwordField.getText();
        String address = addressField.getText();
        String amka = amkaField.getText();
        boolean isCustomer = customerCheckBox.isSelected();
        boolean isPharmacist = pharmacistCheckBox.isSelected();

        System.out.println("Εγγραφή:");
        System.out.println("Email: " + email);
        System.out.println("Κωδικός: " + password);
        System.out.println("Διεύθυνση: " + address);
        System.out.println("AMKA: " + amka);
        System.out.println("Ρόλος - Πελάτης: " + isCustomer + ", Φαρμακοποιός: " + isPharmacist);
    }
}
