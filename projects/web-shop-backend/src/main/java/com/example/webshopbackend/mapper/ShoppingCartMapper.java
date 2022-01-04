package com.example.webshopbackend.mapper;

import com.example.webshopbackend.dto.ShoppingCartDto;
import com.example.webshopbackend.model.ShoppingCart;

public class ShoppingCartMapper {

    public static ShoppingCartDto convertToDto(ShoppingCart shoppingCart) {
        ShoppingCartDto dto = new ShoppingCartDto();

        dto.setUser(UserMapper.convertToDto(shoppingCart.getUser()));
        dto.setWebShop(WebShopMapper.convertToDto(shoppingCart.getWebShop()));
        dto.setTotalPrice(shoppingCart.getTotalPrice());
        dto.setCurrency(shoppingCart.getTransaction().getCurrency());

        return dto;
    }
}
