package com.example.webshopbackend.controller;

import com.example.webshopbackend.dto.PaymentDto;
import com.example.webshopbackend.service.IShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/payment")
public class PaymentController {

    @Autowired
    private IShoppingCartService shoppingCartService;

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody PaymentDto dto) {
        try {
            shoppingCartService.save(dto);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
