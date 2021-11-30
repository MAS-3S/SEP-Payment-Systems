package com.example.webshopbackend.mapper;

import com.example.webshopbackend.dto.ProductDto;
import com.example.webshopbackend.model.Product;
import com.example.webshopbackend.model.enums.Currency;

public class ProductMapper {

    public static ProductDto convertToDto(Product product) {
        ProductDto dto = new ProductDto();

        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setImage(product.getImage());
        dto.setPrice(product.getPrice());
        dto.setAvailableBalance(product.getAvailableBalance());
        if(product.getCurrency().equals(Currency.RSD)) {
            dto.setCurrency("RSD");
        } else if (product.getCurrency().equals(Currency.EUR)) {
            dto.setCurrency("EUR");
        }

        return dto;
    }

    public static Product convertToEntity(ProductDto dto) {
        Product product = new Product();

        return product;
    }
}
