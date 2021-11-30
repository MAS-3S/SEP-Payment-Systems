package com.example.webshopbackend.service;

import com.example.webshopbackend.model.Product;

import java.util.List;

public interface IProductService {
    List<Product> findAllByWebShopId(String id);
}
