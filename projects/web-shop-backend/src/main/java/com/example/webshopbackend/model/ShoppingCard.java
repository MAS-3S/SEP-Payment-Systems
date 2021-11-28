package com.example.webshopbackend.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="shopping_cards")
public class ShoppingCard {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @OneToMany(mappedBy = "shoppingCard", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ItemToPurchase> items;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "web_shop_id", referencedColumnName = "id")
    private WebShop webShop;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    //**
    @OneToOne(mappedBy = "shoppingCard")
    private Transaction transaction;

    public ShoppingCard() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<ItemToPurchase> getItems() {
        return items;
    }

    public void setItems(List<ItemToPurchase> items) {
        this.items = items;
    }

    public WebShop getWebShop() {
        return webShop;
    }

    public void setWebShop(WebShop webShop) {
        this.webShop = webShop;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}