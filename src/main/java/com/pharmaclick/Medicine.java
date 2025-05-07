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

public Medicine(int id, String name, String description, double price, int pharmacyId) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
    this.pharmacyId = pharmacyId;
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

    private int pharmacyId;

public int getPharmacyId() {
    return pharmacyId;
}

// Αν χρειάζεται, πρόσθεσε και setter:
public void setPharmacyId(int pharmacyId) {
    this.pharmacyId = pharmacyId;
}

}
