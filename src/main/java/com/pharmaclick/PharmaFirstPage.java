package com.pharmaclick;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.sql.Connection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.pharmaclick.Booking;
import com.pharmaclick.BookingItem;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;



public class PharmaFirstPage {

    private String pharmacyEmail;

    private int pharmacyId;

    public void setPharmacyEmail(String email) {
        this.pharmacyEmail = email;

        if (email == null || email.isEmpty()) {
        System.out.println("EMAIL ΕΙΝΑΙ ΑΚΥΡΟ");
        return;
    }

    System.out.println("✅ Email που πέρασε: " + email);

    List<Category> categories = loadCategoriesForPharmacy(pharmacyEmail);
    displayCategories(categories);
    }

    private List<Category> loadCategoriesForPharmacy(String pharmacyEmail) {
    List<Category> categories = new ArrayList<>();

    try (Connection conn = DatabaseConnection.getConnection()) {
        String sql = "SELECT name, description, image_url FROM categories WHERE pharmacy_name = ? OR is_global = 1";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, pharmacyEmail);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Category category = new Category();
            category.setName(rs.getString("name"));
            category.setDescription(rs.getString("description"));
            category.setImageUrl(rs.getString("image_url"));
            categories.add(category);
        }
    } catch (SQLException e) {
        System.err.println("Σφάλμα κατά τη φόρτωση κατηγοριών:");
        e.printStackTrace();
    }

    return categories;
}


    @FXML
    private ImageView profileIcon;

    @FXML
    public void goToProfile() {
        System.out.println("Προφίλ χρήστη");
    }

    @FXML
    public void handleReservationTick() {
        System.out.println("Επιβεβαίωση κράτησης");
    }

    @FXML
    public void handleApprove() {
        System.out.println("Έγκριση αιτήματος");
    }

    @FXML
    public void handleReject() {
        System.out.println("Απόρριψη αιτήματος");
    }

    @FXML
    public void goToAddForm(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddMedicine.fxml"));
            Parent root = loader.load();

            AddMedicineController controller = loader.getController();
            controller.setPharmacyName(pharmacyEmail);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 350, 600);
            stage.setScene(scene);
        }  catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    public void handleConfirmReservation1() {
        System.out.println("Επιβεβαιώθηκε η κράτηση 1");
    }

    @FXML
    public void handleConfirmReservation2() {
        System.out.println("Επιβεβαιώθηκε η κράτηση 2");
    }

    @FXML
    public void handleApproveRequest1() {
        System.out.println("Εγκρίθηκε το αίτημα 1");
    }

    @FXML
    public void handleRejectRequest1() {
        System.out.println("Απορρίφθηκε το αίτημα 1");
    }

    @FXML
    public void handleApproveRequest2() {
        System.out.println("Εγκρίθηκε το αίτημα 2");
    }

    @FXML
    public void handleRejectRequest2() {
        System.out.println("Απορρίφθηκε το αίτημα 2");
    }

    // Για κάθε κατηγορία
    public void goToAddFormWithCategory(String categoryName, String iconPath, MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddMedicine.fxml"));
            Parent root = loader.load();

            AddMedicineController controller = loader.getController();
            controller.setCategory(categoryName, iconPath);
            controller.setPharmacyName(pharmacyEmail);  // περνάει το email

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 350, 600);
            stage.setScene(scene);
     

        } catch (IOException e) {
        e.printStackTrace();
    }
    }


    @FXML public void goToCategoryA1(MouseEvent event) { goToAddFormWithCategory("Αναλγητικά και Αντιφλεγμονώδη", "/images/category1.png", event); }
    @FXML public void goToCategoryB1(MouseEvent event) { goToAddFormWithCategory("Αναλγητικά και Αντιφλεγμονώδη", "/images/category1.png", event); }
    @FXML public void goToCategoryB2(MouseEvent event) { goToAddFormWithCategory("Βιταμίνες και Συμπληρώματα", "/images/category2.png", event); }
    @FXML public void goToCategoryA2(MouseEvent event) { goToAddFormWithCategory("Βιταμίνες και Συμπληρώματα", "/images/category2.png", event); }
    @FXML public void goToCategoryA3(MouseEvent event) { goToAddFormWithCategory("Κρυολογήματα και Γρίπη", "/images/category3.png", event); }
    @FXML public void goToCategoryB3(MouseEvent event) { goToAddFormWithCategory("Κρυολογήματα και Γρίπη", "/images/category3.png", event); }
    @FXML public void goToCategoryA4(MouseEvent event) { goToAddFormWithCategory("Βρεφικά Προϊόντα", "/images/category4.png", event); }
    @FXML public void goToCategoryB4(MouseEvent event) { goToAddFormWithCategory("Βρεφικά Προϊόντα", "/images/category4.png", event); }
    @FXML public void goToCategoryA5(MouseEvent event) { goToAddFormWithCategory("Δερματολογικά", "/images/category5.png", event); }
    @FXML public void goToCategoryB5(MouseEvent event) { goToAddFormWithCategory("Δερματολογικά", "/images/category5.png", event); }
    @FXML public void goToCategoryA6(MouseEvent event) { goToAddFormWithCategory("Ερωτική Υγεία και Προφυλάξεις", "/images/category6.png", event); }
    @FXML public void goToCategoryB6(MouseEvent event) { goToAddFormWithCategory("Ερωτική Υγεία και Προφυλάξεις", "/images/category6.png", event); }


    
    @FXML
    public void goToCategoryC(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddCategory.fxml"));
            Parent root = loader.load();
    
            // Περνάμε το email στο controller της φόρμας
            AddCategoryController controller = loader.getController();
            controller.setPharmacyName(pharmacyEmail);
    
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 350, 600));
            stage.setTitle("Προσθήκη Κατηγορίας");
            stage.show();
        } 
     catch (IOException e) {
        e.printStackTrace();
    }


}

@FXML
private void initialize() {
    profileIcon.setOnMouseClicked(event -> {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/pharmacy_profile.fxml"));

            Parent profileRoot = loader.load();
            PharmacyProfileController controller = loader.getController();
            controller.setPharmacyId(pharmacyId); // ✨ Περνάς το ID εδώ

            Scene profileScene = new Scene(profileRoot);
            Stage stage = (Stage) profileIcon.getScene().getWindow();
            stage.setScene(profileScene);
        } 
         catch (IOException e) {
        e.printStackTrace();
    }
    });
}

public static class Category {
    private String name;
    private String description;
    private String imageUrl;

    public Category() {}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}


@FXML private TilePane categoriesTilePane;  // πρέπει να έχεις @FXML το TilePane από το FXML

private void displayCategories(List<Category> categories) {
    categoriesTilePane.getChildren().clear();

    for (Category category : categories) {
        VBox box = new VBox(5);
        box.setStyle("-fx-background-color: #f9f9f9; -fx-padding: 10; -fx-background-radius: 10;");

        Label nameLabel = new Label(category.getName());
        nameLabel.setWrapText(true);
        nameLabel.setStyle("-fx-font-weight: bold; -fx-text-alignment: center;");

        ImageView imageView = new ImageView();
        imageView.setFitHeight(48);
        imageView.setFitWidth(48);

        try {
            Image image = new Image(getClass().getResource("/images/" + category.getImageUrl()).toExternalForm());
            imageView.setImage(image);
        } catch (Exception e) {
            System.out.println("Image not found: " + category.getImageUrl());
        }

        box.getChildren().addAll(nameLabel, imageView);

        // Προαιρετικό: onClick μετάβαση με category info
        box.setOnMouseClicked(e -> {
            goToAddFormWithCategory(category.getName(), "/images/" + category.getImageUrl(), e);
        });

        categoriesTilePane.getChildren().add(box);
    }
}







public void setPharmacyId(int id) {
    this.pharmacyId = id;
    List<Booking> bookings = loadBookingsForPharmacy(pharmacyId);
    displayBookings(bookings); // αιτήματα
    displayApprovedBookings(bookings); // κρατήσεις
}


 @FXML private VBox bookingsVBox;      // ο container από το FXML

    private List<Booking> loadBookingsForPharmacy(int pharmacyId) {
    List<Booking> list = new ArrayList<>();
    String sql = "SELECT id, pharmacy_id, customer_name, customer_address, customer_phone, customer_email, customer_amka, comment, pickup_date, total_price, status FROM bookings WHERE pharmacy_id = ? ORDER BY pickup_date DESC";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, pharmacyId);
        System.out.println("Loading bookings for pharmacyId = " + pharmacyId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Booking b = new Booking(
                rs.getInt("id"),
                rs.getInt("pharmacy_id"),
                rs.getString("customer_name"),
                rs.getString("customer_address"),
                rs.getString("customer_phone"),
                rs.getString("customer_email"),
                rs.getString("customer_amka"),
                rs.getString("comment"),
                rs.getDate("pickup_date"),
                rs.getDouble("total_price"),
                rs.getString("status")
            );
            list.add(b);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
}

private List<BookingItem> loadItemsForBooking(int bookingId) {
    List<BookingItem> items = new ArrayList<>();
    String sql = "SELECT m.name, m.description, m.price, bi.quantity FROM booking_items bi JOIN medicines m ON bi.medicine_id = m.id WHERE bi.booking_id = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, bookingId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            items.add(new BookingItem(
                rs.getString("name"),
                rs.getString("description"),
                rs.getDouble("price"),
                rs.getInt("quantity")
            ));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return items;
}

private void displayBookings(List<Booking> bookings) {
    bookingsVBox.getChildren().clear();
    DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    for (Booking b : bookings) {
        if ("approve".equalsIgnoreCase(b.getStatus())) continue;

        List<BookingItem> items = loadItemsForBooking(b.getId());

        VBox box = new VBox(8);
        box.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-background-radius: 10;");

        // 1η γραμμή: Πελάτης + AMKA
        Label lblClient = new Label(b.getCustomerName() + " (AMKA: " + b.getCustomerAmka() + ")");
        lblClient.setStyle("-fx-font-weight: bold;");

        // 2η γραμμή: Ημ/νία, Σύνολο, Κατάσταση
        HBox row2 = new HBox(15);
        row2.setAlignment(Pos.CENTER_LEFT);
        row2.getChildren().addAll(
            new Label("Ημ/νία: " + df.format(b.getPickupDate().toLocalDate())),
            new Label("Σύνολο: " + b.getTotalPrice() + " €"),
            new Label("Κατάσταση: " + b.getStatus())
        );

        // 3η γραμμή: Λίστα ειδών
        VBox itemsBox = new VBox(4);
        for (BookingItem it : items) {
            Label li = new Label("• " + it.getMedicineName() + " ×" + it.getQuantity()
                                + " (" + it.getPrice() + " €)");
            itemsBox.getChildren().add(li);
        }

        // 4η γραμμή: Κουμπιά αποδοχής/απόρριψης
        Button acceptBtn = new Button("Αποδοχή");
        Button denyBtn = new Button("Απόρριψη");

        acceptBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        denyBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");

        HBox actionRow = new HBox(10, acceptBtn, denyBtn);
        actionRow.setAlignment(Pos.CENTER_RIGHT);

        // Προσθήκη listener
        acceptBtn.setOnAction(e -> {
            updateBookingStatus(b.getId(), "approve");
            refreshBookings();
        });

        denyBtn.setOnAction(e -> {
            updateBookingStatus(b.getId(), "denied");
            refreshBookings();
        });

        // Προσθήκη όλων στο κουτί
        box.getChildren().addAll(lblClient, row2, new Separator(), itemsBox, actionRow);
        bookingsVBox.getChildren().add(box);
    }
}

private void refreshBookings() {
    List<Booking> updatedBookings = loadBookingsForPharmacy(this.pharmacyId);
    displayBookings(updatedBookings);
    displayApprovedBookings(updatedBookings);
}

private Booking getBookingById(int bookingId) {
    String sql = "SELECT * FROM bookings WHERE id = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, bookingId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new Booking(
                rs.getInt("id"),
                rs.getInt("pharmacy_id"),
                rs.getString("customer_name"),
                rs.getString("customer_address"),
                rs.getString("customer_phone"),
                rs.getString("customer_email"),
                rs.getString("customer_amka"),
                rs.getString("comment"),
                rs.getDate("pickup_date"),
                rs.getDouble("total_price"),
                rs.getString("status")
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}


private void updateBookingStatus(int bookingId, String newStatus) {
    String sqlUpdate = "UPDATE bookings SET status = ? WHERE id = ?";
    String sqlNotif = "INSERT INTO notifications (user_email, message, is_read) VALUES (?, ?, 0)";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement updateStmt = conn.prepareStatement(sqlUpdate);
         PreparedStatement notifStmt = conn.prepareStatement(sqlNotif)) {

        updateStmt.setString(1, newStatus);
        updateStmt.setInt(2, bookingId);
        updateStmt.executeUpdate();

        if ("approve".equalsIgnoreCase(newStatus)) {
            Booking b = getBookingById(bookingId);
            if (b != null) {
                String message = "Η κράτησή σας για τις " +
                        b.getPickupDate().toLocalDate() +
                        " επιβεβαιώθηκε από το φαρμακείο.";
                notifStmt.setString(1, b.getCustomerEmail());
                notifStmt.setString(2, message);
                notifStmt.executeUpdate();
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}





@FXML private VBox approvedBookingsVBox;

private void displayApprovedBookings(List<Booking> bookings) {
    approvedBookingsVBox.getChildren().clear();
    DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    for (Booking b : bookings) {
        if (!"approve".equalsIgnoreCase(b.getStatus())) continue;

        VBox box = new VBox(5);
        box.setStyle("-fx-background-color: #f2f2f2; -fx-padding: 10; -fx-background-radius: 10;");

        Label client = new Label(b.getCustomerName() + " (" + b.getCustomerPhone() + ")");
        Label pickup = new Label("Παραλαβή: " + df.format(b.getPickupDate().toLocalDate()));
        Label total = new Label("Σύνολο: " + b.getTotalPrice() + " €");

        box.getChildren().addAll(client, pickup, total);
        approvedBookingsVBox.getChildren().add(box);
    }

    System.out.println("Total bookings loaded: " + bookings.size());
}


}

