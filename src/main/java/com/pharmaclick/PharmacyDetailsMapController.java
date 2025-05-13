package com.pharmaclick;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import com.pharmaclick.Pharmacy;
import javafx.geometry.Bounds;

import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class PharmacyDetailsMapController {

    @FXML
    private Label pharmacyNameLabel;

    @FXML
    private Label pharmacyAddressLabel;

    @FXML
    private Label pharmacyPhoneLabel;

    @FXML
    private ImageView notificationsButton;

    @FXML
    private ImageView homeButton;

    @FXML
    private VBox medicineListVBox;

    @FXML
    private ImageView cartIcon;


    @FXML
    private TextField searchBar;

    private List<Medicine> allMedicines;
    private int currentUserId;

    public void setUserId(int userId) {
    this.currentUserId = userId;    
    }


    @FXML
    private ImageView filterButton;

    private ContextMenu filterMenu = new ContextMenu();
    private String currentSortOption = "";


    /**
     * Εμφανίζει τα στοιχεία του φαρμακείου και φορτώνει τα φάρμακα.
     */
    public void setPharmacyDetails(Pharmacy pharmacy) {
        pharmacyNameLabel.setText(pharmacy.getName());
        pharmacyAddressLabel.setText(pharmacy.getAddress());
        pharmacyPhoneLabel.setText(pharmacy.getPhone());
        loadMedicines(pharmacy.getId());
    }

    /**
     * Φορτώνει τα φάρμακα από τη βάση και προσθέτει listener αναζήτησης.
     */
    public void loadMedicines(int pharmacyId) {
        allMedicines = DatabaseHelper.getMedicinesByPharmacyId(pharmacyId);
        if (allMedicines == null) {
            System.out.println("❌ Δεν βρέθηκαν φάρμακα (null)");
        } else {
            System.out.println("📦 Συνολικά φάρμακα: " + allMedicines.size());
            allMedicines.forEach(m -> System.out.println("   ➜ " + m.getName()));
        }
        
        updateMedicineList("");

        searchBar.textProperty().addListener((obs, oldText, newText) -> {
            updateMedicineList(newText);
        });

        System.out.println("📦 Βρέθηκαν " + allMedicines.size() + " φάρμακα για pharmacyId = " + pharmacyId);


        
    }

    /**
     * Ενημερώνει τη λίστα φαρμάκων με βάση το φίλτρο.
     */
    private void updateMedicineList(String filter) {
    medicineListVBox.getChildren().clear();

    Stream<Medicine> stream = allMedicines.stream()
        .filter(m -> m.getName().toLowerCase().contains(filter.toLowerCase()));

    if (currentSortOption.equals("asc")) {
        stream = stream.sorted(Comparator.comparing(Medicine::getPrice));
    } else if (currentSortOption.equals("desc")) {
        stream = stream.sorted(Comparator.comparing(Medicine::getPrice).reversed());
    }

    stream.forEach(m -> medicineListVBox.getChildren().add(createMedicineItem(m)));
}


    /**
     * Δημιουργεί ένα HBox με τα στοιχεία του φαρμάκου.
     */
    private HBox createMedicineItem(Medicine m) {
        // 🧡 Εικονίδιο Καρδιάς
        Button favoriteButton = new Button();
        ImageView heartIcon = new ImageView(new Image(getClass().getResourceAsStream(
            m.isFavorite() ? "/images/heart.png" : "/images/heart-full.png"
        )));
        heartIcon.setFitHeight(16);
        heartIcon.setFitWidth(16);
        favoriteButton.setGraphic(heartIcon);
        favoriteButton.setStyle("-fx-background-color: transparent;");
        favoriteButton.setPrefWidth(20);
    
        // Εναλλαγή καρδιάς
        favoriteButton.setOnAction(e -> {
    m.setFavorite(!m.isFavorite());
    System.out.println("👉 Πάτησες καρδιά για: " + m.getName() + ", νέο status: " + m.isFavorite());

    String iconPath = m.isFavorite() ? "/images/heart.png" : "/images/heart-full.png";
    InputStream stream = getClass().getResourceAsStream(iconPath);
    if (stream == null) {
        System.out.println("❌ Η εικόνα ΔΕΝ βρέθηκε: " + iconPath);
        return;
    }
    heartIcon.setImage(new Image(stream));

    if (m.isFavorite()) {
        DatabaseHelper.addFavorite(currentUserId, m.getId());
    } else {
        DatabaseHelper.removeFavorite(currentUserId, m.getId());
    }
});

        
    
        // 📛 Όνομα Φαρμάκου
        Label name = new Label(m.getName());
        name.setStyle("-fx-font-weight: bold; -fx-font-size: 13;");
    
        // 💡 Τοποθέτηση ονόματος και καρδιάς σε HBox
        HBox nameRow = new HBox(name, favoriteButton);
        nameRow.setSpacing(5);
        nameRow.setAlignment(Pos.CENTER_LEFT);
    
        // 📝 Περιγραφή
        Label desc = new Label(m.getDescription());
        desc.setWrapText(true);
        desc.setMaxWidth(150);
        desc.setStyle("-fx-font-size: 12;");
    
        VBox textBox = new VBox(nameRow, desc);
        textBox.setSpacing(4);
        textBox.setPrefWidth(150);
    
        Label price = new Label(String.format("%.2f €", m.getPrice()));
        price.setMinWidth(50);
        price.setPrefWidth(50);
        price.setStyle("-fx-font-size: 12;");
    
        Spinner<Integer> quantity = new Spinner<>(1, 99, 1);
        quantity.setPrefWidth(85);
        quantity.setMaxWidth(85);
    
        Button cartButton = new Button();
        ImageView cartIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/shopping-cart-add.png")));
        cartIcon.setFitHeight(20);
        cartIcon.setFitWidth(20);
        cartButton.setGraphic(cartIcon);
        cartButton.setStyle("-fx-background-color: transparent;");
        cartButton.setPrefWidth(30);

        cartButton.setOnAction(e -> {
            int qty = quantity.getValue();
            CartManager.getInstance().addItem(m, qty);
            System.out.println("✅ Προστέθηκε: " + m.getName() + " x" + qty);
        });
        
    
        HBox item = new HBox(textBox, price, quantity, cartButton);
        item.setSpacing(10);
        item.setAlignment(Pos.CENTER_LEFT);
        item.setStyle("-fx-padding: 10; -fx-background-color: white; -fx-background-radius: 10;");
        item.setMaxWidth(310);
    
        return item;
    }
    
    
    

    

    @FXML
    private void openNotificationsPage(javafx.scene.input.MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/notifications_user.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println("⚠️ Σφάλμα ανοίγματος σελίδας ειδοποιήσεων: " + e.getMessage());
        }
    }

    @FXML
    private void goToFrontpage(javafx.scene.input.MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/frontpage_user.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println("⚠️ Σφάλμα επιστροφής στην αρχική σελίδα: " + e.getMessage());
        }
    }


    @FXML
    private void showFilterMenu(javafx.scene.input.MouseEvent event) {
        filterMenu.getItems().clear();
    
        MenuItem ascending = new MenuItem("Τιμή: Αύξουσα");
        MenuItem descending = new MenuItem("Τιμή: Φθίνουσα");
        MenuItem clear = new MenuItem("Καμία ταξινόμηση");
    
        ascending.setOnAction(e -> {
            currentSortOption = "asc";
            updateMedicineList(searchBar.getText());
        });
    
        descending.setOnAction(e -> {
            currentSortOption = "desc";
            updateMedicineList(searchBar.getText());
        });
    
        clear.setOnAction(e -> {
            currentSortOption = "";
            updateMedicineList(searchBar.getText());
        });
    
        ascending.getStyleClass().add("menu-item");
        descending.getStyleClass().add("menu-item");
        clear.getStyleClass().add("menu-item");
    
        filterMenu.getItems().addAll(ascending, descending, clear);
    
        filterMenu.setOnShowing(e -> {
            if (filterMenu.getScene() != null) {
                filterMenu.getScene().getStylesheets().add(
                    getClass().getResource("/styles/context-menu-style.css").toExternalForm()
                );
            }
        });
    
        Bounds bounds = filterButton.localToScreen(filterButton.getBoundsInLocal());
        filterMenu.show(filterButton, bounds.getMinX(), bounds.getMaxY());
    }


    @FXML
private void goToCartPage(javafx.scene.input.MouseEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/cart.fxml"));
        Parent root = loader.load();

        // Αν θέλεις να περάσεις userId ή άλλα δεδομένα:
        CartController controller = loader.getController();
        //controller.setUserId(currentUserId); // αν το υποστηρίζει

        Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    } catch (Exception e) {
        System.out.println("⚠️ Σφάλμα ανοίγματος σελίδας καλαθιού: " + e.getMessage());
    }
}



}    