package com.pharmaclick;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SearchByCategoryApp extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/SearchByCategory.fxml"));
        Parent root = loader.load();

        SearchByCategoryController controller = loader.getController();
        controller.loadCategories(3); 

        Scene scene = new Scene(root, 369, 563);
        stage.setScene(scene);
        stage.setTitle("Αναζήτηση κατά Κατηγορία");

        stage.setMinWidth(352);
        stage.setMaxWidth(352);
        stage.setMinHeight(600);
        stage.setMaxHeight(600);
        stage.setResizable(false);
        stage.show();
    }
}
