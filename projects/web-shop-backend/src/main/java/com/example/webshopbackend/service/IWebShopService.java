package com.example.webshopbackend.service;

import com.example.webshopbackend.model.WebShop;

import java.util.List;

public interface IWebShopService {
    List<WebShop> findAll();
}
