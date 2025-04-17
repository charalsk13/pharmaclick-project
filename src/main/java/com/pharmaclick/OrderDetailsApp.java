package com.pharmaclick;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class OrderDetailsApp extends Application {

    private static int orderId;

    public static void setOrderId(int id) {
        orderId = id;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/order_details.fxml"));
        Scene scene = new Scene(loader.load());
        
        OrderDetailsController controller = loader.getController();
        controller.loadOrderDetails(orderId);

        primaryStage.setTitle("Λεπτομέρειες Παραγγελίας");
        primaryStage.setScene(scene);

        primaryStage.setMinWidth(380);
        primaryStage.setMaxWidth(380);
        primaryStage.setMinHeight(600);
        primaryStage.setMaxHeight(600);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}