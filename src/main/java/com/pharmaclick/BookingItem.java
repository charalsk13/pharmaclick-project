package com.pharmaclick.models;

public class BookingItem {
    private String medicineName;
    private String description;
    private double price;
    private int quantity;

    public BookingItem(String medicineName, String description, double price, int quantity) {
        this.medicineName = medicineName;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public String getMedicineName() { return medicineName; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
}
