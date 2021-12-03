package com.example.webshopbackend.service;

import com.example.webshopbackend.dto.PaymentDto;
import com.example.webshopbackend.model.ShoppingCart;

public interface IShoppingCartService {
    String save(PaymentDto paymentDto) throws Exception;
}
