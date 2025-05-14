package com.pharmaclick;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
public void start(Stage stage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
    Scene scene = new Scene(root, 350, 650);
    
    stage.setScene(scene);
    stage.setTitle("Σύνδεση Χρήστη");

    stage.setMinWidth(350);
    stage.setMaxWidth(350);
    stage.setMinHeight(600);
    stage.setMaxHeight(600);

    stage.setResizable(false); // Απενεργοποιεί το resize τελείως
    stage.show();
}

}
