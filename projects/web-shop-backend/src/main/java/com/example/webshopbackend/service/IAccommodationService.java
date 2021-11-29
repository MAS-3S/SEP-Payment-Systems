package com.example.webshopbackend.service;

import com.example.webshopbackend.model.Accommodation;

import java.util.List;

public interface IAccommodationService {
    public List<Accommodation> findAllByWebShopId(String id);
}
