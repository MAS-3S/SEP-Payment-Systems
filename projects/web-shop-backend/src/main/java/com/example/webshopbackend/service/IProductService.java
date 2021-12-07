package com.example.webshopbackend.service;

import com.example.webshopbackend.dto.ItemToPurchaseDto;
import com.example.webshopbackend.model.Product;

import java.util.List;
import java.util.Set;

public interface IProductService {
    List<Product> findAllByWebShopId(String id);
    Set<ItemToPurchaseDto> findAllPayedProductsByUserId(String id);
}
