package com.pharmaclick;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;

public class UserProfileController {

    @FXML private Button ordersButton;
    @FXML private Button helpButton;
    @FXML private Button favoritesButton;
    @FXML private Button userInfoButton;
    @FXML private Button addressesButton;
    @FXML private Button paymentMethodsButton;
    @FXML private Button notificationsButton;
    @FXML private Button privacySettingsButton;
    @FXML private Button logoutButton;

    @FXML
    private void openOrdersPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Customer_orders.fxml"));
            Parent root = loader.load();

            CustomerOrdersController controller = loader.getController();
            controller.loadCustomerOrders(3); // Φόρτωση δεδομένων με orderId = 3

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Οι κρατήσεις μου");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openFavorites(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/favorites.fxml"));
            Parent root = loader.load();
            
            FavoritesController controller = loader.getController();
            controller.setUserId(1);
            
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Τα Αγαπημένα μου");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    @FXML
    private void openHelpPage(ActionEvent event) {
        System.out.println("Help page not implemented yet.");
    }

    @FXML
    private void openUserInfo(ActionEvent event) {
        openView(event, "/views/Customer_info.fxml", "Οι πληροφορίες μου");
    }

    @FXML
    private void openAddresses(ActionEvent event) {
        openView(event, "/views/Addresses.fxml", "Διευθύνσεις");
    }

    @FXML
    private void openPaymentMethods(ActionEvent event) {
        System.out.println("Payment methods page not implemented yet.");
    }

    @FXML
    private void openNotifications(ActionEvent event) {
        System.out.println("Notifications page not implemented yet.");
    }

    @FXML
    private void openPrivacySettings(ActionEvent event) {
        System.out.println("Privacy settings page not implemented yet.");
    }

    @FXML
private void openLogout() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Login");
        stage.show();

        Stage currentStage = (Stage) logoutButton.getScene().getWindow();
        currentStage.close();

    } catch (IOException e) {
        e.printStackTrace();
    }
}

    private void openView(ActionEvent event, String fxmlFile, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}