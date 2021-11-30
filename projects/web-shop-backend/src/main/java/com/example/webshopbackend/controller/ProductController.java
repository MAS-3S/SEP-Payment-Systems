package com.example.webshopbackend.controller;

import com.example.webshopbackend.dto.AccommodationDto;
import com.example.webshopbackend.dto.ProductDto;
import com.example.webshopbackend.mapper.AccommodationMapper;
import com.example.webshopbackend.mapper.ProductMapper;
import com.example.webshopbackend.model.Accommodation;
import com.example.webshopbackend.model.Product;
import com.example.webshopbackend.service.IAccommodationService;
import com.example.webshopbackend.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping(value="/webshop/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductDto>> findAllProducts(@PathVariable String id) {
        List<ProductDto> result = new ArrayList<>();
        List<Product> products = productService.findAllByWebShopId(id);
//        "902a1e99-87e9-43b1-8927-9fbd4c2d9ca8"
        if(products.isEmpty()) {
            return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
        }

        for(Product product : products) {
            result.add(ProductMapper.convertToDto(product));
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
