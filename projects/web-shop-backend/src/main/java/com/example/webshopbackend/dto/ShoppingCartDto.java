package com.example.webshopbackend.dto;

import java.util.List;

public class ShoppingCartDto {

    UserDto user;
    WebShopDto webShop;
    Double totalPrice;
    String currency;
    List<ItemToPurchaseDto> itemsToPurchase;

    public ShoppingCartDto() {
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public WebShopDto getWebShop() {
        return webShop;
    }

    public void setWebShop(WebShopDto webShop) {
        this.webShop = webShop;
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
