package com.pharmaclick;

import java.util.ArrayList;
import java.util.List;


public class CartManager {
    private static CartManager instance = null;
    private List<CartItem> items = new ArrayList<>();

    private CartManager() {}

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addItem(Medicine medicine, int quantity) {
        items.add(new CartItem(medicine, quantity));
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void clearCart() {
        items.clear();
    }
}
