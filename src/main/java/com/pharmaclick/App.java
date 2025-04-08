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
        // Φορτώνουμε το FXML από τον φάκελο views
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RegisterView.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 340, 600); // μέγεθος παραθύρου
        stage.setScene(scene);
        stage.setTitle("Εγγραφή Χρήστη");
        stage.show();
    }
}
