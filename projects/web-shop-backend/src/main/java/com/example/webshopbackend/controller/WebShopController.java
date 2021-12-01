package com.example.webshopbackend.controller;

import com.example.webshopbackend.dto.WebShopDto;
import com.example.webshopbackend.mapper.WebShopMapper;
import com.example.webshopbackend.model.WebShop;
import com.example.webshopbackend.service.IWebShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/webshops")
public class WebShopController {

    @Autowired
    private IWebShopService webShopService;

    @GetMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WebShopDto>> findAllWebShops() {
        List<WebShopDto> result = new ArrayList<>();

        for(WebShop webShop : webShopService.findAll()) {
            result.add(WebShopMapper.convertToDto(webShop));
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
