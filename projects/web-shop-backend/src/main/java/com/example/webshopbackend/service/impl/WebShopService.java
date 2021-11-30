package com.example.webshopbackend.service.impl;

import com.example.webshopbackend.model.WebShop;
import com.example.webshopbackend.repository.WebShopRepository;
import com.example.webshopbackend.service.IWebShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebShopService implements IWebShopService {

    private final WebShopRepository webShopRepository;

    @Autowired
    public WebShopService(WebShopRepository webShopRepository) {
        this.webShopRepository = webShopRepository;
    }

    public List<WebShop> findAll() {
        return webShopRepository.findAll();
    }
}
