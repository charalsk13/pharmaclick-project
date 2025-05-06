package com.pharmaclick;

public class Medicine {
    private int id;
    private String name;
    private String description;
    private double price;

    // 🧡 ΝΕΟ: Αν είναι αγαπημένο
    private boolean favorite = false;

    public Medicine(int id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description != null ? description : "";
    }

    public double getPrice() {
        return price;
    }

    // 🧡 ΝΕΟ: Favorite flag
    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
