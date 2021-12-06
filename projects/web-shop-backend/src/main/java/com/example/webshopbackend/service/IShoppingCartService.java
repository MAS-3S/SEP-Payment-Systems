package com.example.webshopbackend.service;

import com.example.webshopbackend.dto.PaymentDto;

public interface IShoppingCartService {
    String save(PaymentDto paymentDto) throws Exception;
}
