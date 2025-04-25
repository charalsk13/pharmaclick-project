package com.pharmaclick;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class CustomerOrdersController {

    @FXML private Label statusLabel1, deliveryDateLabel1, idLabel1;
    @FXML private Label statusLabel2, deliveryDateLabel2, idLabel2;
    @FXML private ProgressBar progressBar1, progressBar2;
    @FXML private ImageView imageView1, imageView2;
    @FXML private Label totalOrdersLabel;

    private Connection connect() throws SQLException {
        String url = "jdbc:mariadb://localhost:3306/pharmaclick";
        String user = "root";
        String password = "123";
        return DriverManager.getConnection(url, user, password);
    }

    public void loadCustomerOrders(int id) {
        try (Connection conn = connect()) {
            String sql = "SELECT * FROM orders WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            int orderCount = 0;

            if (rs.next()) {
                fillOrder1(rs);
                orderCount++;
            }

            if (rs.next()) {
                fillOrder2(rs);
                orderCount++;
            }

            totalOrdersLabel.setText(orderCount + " παραγγελίες");

        } catch (SQLException e) {
            e.printStackTrace();
            totalOrdersLabel.setText("Σφάλμα βάσης.");
        }
    }

    private void fillOrder1(ResultSet rs) throws SQLException {
        statusLabel1.setText(rs.getString("status"));
        deliveryDateLabel1.setText("Ημ/νία Παράδοσης: " + rs.getDate("delivery_date"));
        idLabel1.setText("Κωδικός: " + rs.getInt("id"));
        progressBar1.setProgress(rs.getDouble("progress"));
        imageView1.setImage(new Image(getClass().getResourceAsStream("/images/category3.png")));
    }

    private void fillOrder2(ResultSet rs) throws SQLException {
        statusLabel2.setText(rs.getString("status"));
        deliveryDateLabel2.setText("Ημ/νία Παράδοσης: " + rs.getDate("delivery_date"));
        idLabel2.setText("Κωδικός: " + rs.getInt("id"));
        progressBar2.setProgress(rs.getDouble("progress"));
        imageView2.setImage(new Image(getClass().getResourceAsStream("/images/category3.png")));

        
    }
    @FXML
private void launchOrderDetailsApp() {
    try {
        int orderId = Integer.parseInt(idLabel1.getText().replace("Κωδικός: ", ""));

        OrderDetailsApp.setOrderId(orderId);

        new OrderDetailsApp().start(new Stage());

    } catch (Exception e) {
        e.printStackTrace();
    }
}


}