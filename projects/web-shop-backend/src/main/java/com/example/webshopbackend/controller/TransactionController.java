package com.example.webshopbackend.controller;

import com.example.webshopbackend.dto.ItemToPurchaseDto;
import com.example.webshopbackend.dto.ShoppingCartDto;
import com.example.webshopbackend.mapper.ProductMapper;
import com.example.webshopbackend.mapper.ShoppingCartMapper;
import com.example.webshopbackend.model.ItemToPurchase;
import com.example.webshopbackend.model.ShoppingCart;
import com.example.webshopbackend.service.IProductService;
import com.example.webshopbackend.service.ITransactionService;
import com.example.webshopbackend.service.impl.ProductService;
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
@RequestMapping(value = "api/transactions")
public class TransactionController {

    @Autowired
    private ITransactionService transactionService;

    @Autowired
    private IProductService productService;

    @GetMapping(value="/{id}/shoppingCart", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShoppingCartDto> getShoppingCartForTransaction(@PathVariable String id) {
        ShoppingCart shoppingCart = transactionService.getShoppingCartForTransaction(id);
        ShoppingCartDto dto = ShoppingCartMapper.convertToDto(shoppingCart);

        List<ItemToPurchaseDto> itemsToPurchase = new ArrayList<>();
        for(ItemToPurchase item : shoppingCart.getItems()) {
            ItemToPurchaseDto itemDto = new ItemToPurchaseDto();
            itemDto.setProductDto(ProductMapper.convertToDto(item.getProduct()));
            itemDto.setQuantity(item.getQuantity());
            itemsToPurchase.add(itemDto);
        }
        dto.setItemsToPurchase(itemsToPurchase);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
