package com.pharmaclick;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.sql.*;

public class SearchPharmacyController {

    @FXML
    private VBox pharmacyBox1, pharmacyBox2;
    @FXML
    private Label nameLabel1, addressLabel1;
    @FXML
    private Label nameLabel2, addressLabel2;
    @FXML
    private ImageView imageView1, imageView2;

    private Connection connect() throws SQLException {
        String url = "jdbc:mariadb://localhost:3306/pharmaclick";
        String user = "root";
        String password = "123";
        return DriverManager.getConnection(url, user, password);
    }

    public void loadPharmacies() {
        try (Connection conn = connect()) {
            String sql = "SELECT * FROM pharmacies LIMIT 2";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                loadPharmacy1(rs);
            }

            if (rs.next()) {
                loadPharmacy2(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadPharmacy1(ResultSet rs) throws SQLException {
        nameLabel1.setText(rs.getString("name"));
        addressLabel1.setText(rs.getString("address"));
        imageView1.setImage(new Image(getClass().getResourceAsStream("/images/pharmacy.png")));
    }

    private void loadPharmacy2(ResultSet rs) throws SQLException {
        nameLabel2.setText(rs.getString("name"));
        addressLabel2.setText(rs.getString("address"));
        imageView2.setImage(new Image(getClass().getResourceAsStream("/images/pharmacy.png")));
    }
}
