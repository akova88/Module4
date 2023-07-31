package com.cg.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="nam_product", nullable = false)
    private String name;
    @Column(nullable = false)
    private float price;
    @Column(nullable = false)
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
