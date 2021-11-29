package com.example.webshopbackend.model;

import com.example.webshopbackend.model.enums.Currency;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import static javax.persistence.InheritanceType.JOINED;

@Entity
@Table(name="products")
@Inheritance(strategy=JOINED)
public class Product {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "available_balance", nullable = false)
    private int availableBalance;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "currency", nullable = false)
    private Currency currency;

    //**
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private WebShop webShop;

    @OneToOne(mappedBy = "product")
    private ItemToPurchase itemToPurchase;

    public Product() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(int availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public WebShop getWebShop() {
        return webShop;
    }

    public void setWebShop(WebShop webShop) {
        this.webShop = webShop;
    }

    public ItemToPurchase getItemToPurchase() {
        return itemToPurchase;
    }

    public void setItemToPurchase(ItemToPurchase itemToPurchase) {
        this.itemToPurchase = itemToPurchase;
    }
}
