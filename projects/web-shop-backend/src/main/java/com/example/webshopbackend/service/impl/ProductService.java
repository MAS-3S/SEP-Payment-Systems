package com.example.webshopbackend.service.impl;

import com.example.webshopbackend.model.Product;
import com.example.webshopbackend.repository.ProductRepository;
import com.example.webshopbackend.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAllByWebShopId(String id) {
        return productRepository.findAllByWebShopId(id);
    }
}
