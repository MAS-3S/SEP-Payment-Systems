package com.example.webshopbackend.dto;

import java.util.List;

public class ShoppingCartDto {

    UserDto user;
    WebShopDto webShop;
    Double totalPrice;
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

    public List<ItemToPurchaseDto> getItemsToPurchase() {
        return itemsToPurchase;
    }

    public void setItemsToPurchase(List<ItemToPurchaseDto> itemsToPurchase) {
        this.itemsToPurchase = itemsToPurchase;
    }
}
