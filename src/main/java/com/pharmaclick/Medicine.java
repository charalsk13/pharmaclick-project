package com.pharmaclick;

public class Medicine {
    private int id;
    private String name;
    private String description;
    private double price;

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
}
