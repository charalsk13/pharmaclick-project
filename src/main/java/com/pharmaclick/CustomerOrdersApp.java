package com.pharmaclick;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CustomerOrdersApp extends Application{

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Customer_orders.fxml"));
        Parent root = loader.load();

        CustomerOrdersController controller = loader.getController();

        controller.loadCustomerOrders(3);

        Scene scene = new Scene(root, 369, 563);
        stage.setScene(scene);
        stage.setTitle("Οι παραγγελίες μου");

        stage.setMinWidth(352);
        stage.setMaxWidth(352);
        stage.setMinHeight(600);
        stage.setMaxHeight(600);
        stage.setResizable(false);
        stage.show();
    }  
}