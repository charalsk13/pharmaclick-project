package com.pharmaclick;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SearchByCategoryController {

    @FXML
    private VBox categoryBox1, categoryBox2;
    @FXML
    private Label nameLabel1, nameLabel2;
    @FXML
    private ImageView imageView1, imageView2;

    private Connection connect() throws SQLException {
        String url = "jdbc:mariadb://localhost:3306/pharmaclick";
        String user = "root";
        String password = "1111";
        return DriverManager.getConnection(url, user, password);
    }

    public void loadCategories(int userId) {
        try (Connection conn = connect()) {
            String sql = "SELECT * FROM categories LIMIT 2";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                loadCategory1(rs);
            }

            if (rs.next()) {
                loadCategory2(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadCategory1(ResultSet rs) throws SQLException {
        nameLabel1.setText(rs.getString("name"));
        imageView1.setImage(new Image(getClass().getResourceAsStream("/images/" + rs.getString("image_path"))));
    }

    private void loadCategory2(ResultSet rs) throws SQLException {
        nameLabel2.setText(rs.getString("name"));
        imageView2.setImage(new Image(getClass().getResourceAsStream("/images/" + rs.getString("image_path"))));
    }
}
