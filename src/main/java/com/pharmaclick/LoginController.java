package com.pharmaclick;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

public class LoginController {
@FXML
private Hyperlink registerLink;

@FXML
private void goToRegister() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RegisterView.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) registerLink.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Εγγραφή Χρήστη");
        stage.show();

    } catch (IOException e) {
        e.printStackTrace();
    }
}

}
