package com.pharmaclick.models;

import java.sql.Date;

public class Booking {
    private int id;
    private int pharmacyId;
    private String customerName;
    private String customerAddress;
    private String customerPhone;
    private String customerEmail;
    private String customerAmka;
    private String comment;
    private Date pickupDate;
    private double totalPrice;

    public Booking(int id, int pharmacyId, String customerName, String customerAddress, String customerPhone,
                   String customerEmail, String customerAmka, String comment, Date pickupDate, double totalPrice) {
        this.id = id;
        this.pharmacyId = pharmacyId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
        this.customerAmka = customerAmka;
        this.comment = comment;
        this.pickupDate = pickupDate;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public int getPharmacyId() {
        return pharmacyId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getCustomerAmka() {
        return customerAmka;
    }

    public String getComment() {
        return comment;
    }

    public Date getPickupDate() {
        return pickupDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
