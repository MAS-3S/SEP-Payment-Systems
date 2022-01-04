package com.example.webshopbackend.dto;

import java.util.List;

public class PaymentDto {

    String userId;
    String webShopId;
    Double totalPrice;
    String currency;
    List<ItemToPurchaseDto> itemsToPurchase;

    public PaymentDto() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWebShopId() {
        return webShopId;
    }

    public void setWebShopId(String webShopId) {
        this.webShopId = webShopId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<ItemToPurchaseDto> getItemsToPurchase() {
        return itemsToPurchase;
    }

    public void setItemsToPurchase(List<ItemToPurchaseDto> itemsToPurchase) {
        this.itemsToPurchase = itemsToPurchase;
    }
}
