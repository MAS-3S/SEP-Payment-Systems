package com.example.webshopbackend.model;

import com.example.webshopbackend.model.enums.WebShopType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="web_shops")
public class WebShop {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private WebShopType type;

    @OneToMany(mappedBy = "webShop", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<User> users;

    @OneToMany(mappedBy = "webShop", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Product> products;

    @OneToMany(mappedBy = "webShop", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Conference> conferences;

    @OneToMany(mappedBy = "webShop", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Accommodation> accommodations;

    //**
    @OneToOne(mappedBy = "webShop")
    private ShoppingCart shoppingCart;

    public WebShop() {
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public ShoppingCart getShoppingCard() {
        return shoppingCart;
    }

    public void setShoppingCard(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public WebShopType getType() {
        return type;
    }

    public void setType(WebShopType type) {
        this.type = type;
    }

    public List<Conference> getConferences() {
        return conferences;
    }

    public void setConferences(List<Conference> conferences) {
        this.conferences = conferences;
    }

    public List<Accommodation> getAccommodations() {
        return accommodations;
    }

    public void setAccommodations(List<Accommodation> accommodations) {
        this.accommodations = accommodations;
    }
}
