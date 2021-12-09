package com.example.webshopbackend.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="items_to_purchase")
public class ItemToPurchase {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "quantity", nullable = false)
    private int quantity;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "product_id", referencedColumnName = "id")
//    private Product product;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Product product;

    //**
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ShoppingCart shoppingCart;

    public ItemToPurchase() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}
