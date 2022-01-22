package com.example.webshopbackend.service;

import com.example.webshopbackend.dto.PaymentDto;
import com.example.webshopbackend.dto.WageDto;
import com.example.webshopbackend.dto.WageResponse;

public interface IShoppingCartService {
    String save(PaymentDto paymentDto) throws Exception;
    WageResponse paymentWage(WageDto paymentDto) throws Exception;
}
