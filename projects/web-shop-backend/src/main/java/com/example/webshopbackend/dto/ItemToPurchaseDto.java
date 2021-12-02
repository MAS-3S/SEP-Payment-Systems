package com.example.webshopbackend.dto;

public class ItemToPurchaseDto {

    String productId;
    int quantity;

    public ItemToPurchaseDto() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
