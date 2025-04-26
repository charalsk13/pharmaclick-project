package com.pharmaclick;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class AddressessController {

    @FXML
    private WebView mapView;

    @FXML
    public void initialize() {
        WebEngine webEngine = mapView.getEngine();
        webEngine.load(getClass().getResource("/views/map.html").toExternalForm());
    }
}
