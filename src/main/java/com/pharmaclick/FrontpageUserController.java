package com.pharmaclick;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class FrontpageUserController {

    @FXML
private WebView mapView;

@FXML
public void initialize() {
    try {
        var url = getClass().getResource("/views/map.html");
        if (url != null) {
            mapView.getEngine().load(url.toExternalForm());
            System.out.println("✅ Χάρτης φορτώθηκε");
        } else {
            System.out.println("❌ Δεν βρέθηκε το map.html");
        }
    } catch (Exception e) {
        System.out.println("⚠️ Σφάλμα φόρτωσης χάρτη: " + e.getMessage());
    }
}

    
}
