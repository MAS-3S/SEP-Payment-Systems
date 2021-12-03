package com.example.webshopbackend.mapper;

import com.example.webshopbackend.dto.PaymentDto;
import com.example.webshopbackend.model.ShoppingCart;
import com.example.webshopbackend.model.User;
import com.example.webshopbackend.model.WebShop;

import java.time.LocalDateTime;
import java.util.Date;

public class PaymentMapper {

    public static ShoppingCart convertToShoppingCart(PaymentDto dto, User user, WebShop webShop) {
        ShoppingCart shoppingCart = new ShoppingCart();

        shoppingCart.setCreateDate(LocalDateTime.now());
        shoppingCart.setTotalPrice(dto.getTotalPrice());
        shoppingCart.setUser(user);
        shoppingCart.setWebShop(webShop);

        return shoppingCart;
    }

}
