package com.example.rahul.inventoryproject;

/**
 * Created by rahul on 2017-12-27.
 */

public class Inventory {

    private long id;
    private String productName;
    private String price;
    private String quantity;
    private String image;
    private String phoneNumber;

    public Inventory() {
    }

    public Inventory(String productName, String price, String quantity, String image, String phoneNumber) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.phoneNumber = phoneNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
