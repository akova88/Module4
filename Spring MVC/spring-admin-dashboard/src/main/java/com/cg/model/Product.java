package com.cg.model;

import java.util.Date;

public class Product {
    private Long id;
    private String name;
    private float price;
    private int quantity;
    private String imgLink;


    public Product() {
    }

    public Product(Long id, String name, float price, int quantity, String imgLink) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imgLink = imgLink;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }
}
