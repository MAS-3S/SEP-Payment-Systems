package com.example.webshopbackend.service;

import com.example.webshopbackend.dto.ItemToPurchaseDto;
import com.example.webshopbackend.model.Accommodation;

import java.util.List;
import java.util.Set;

public interface IAccommodationService {
    List<Accommodation> findAllByWebShopId(String id);
    Set<ItemToPurchaseDto> findAllPayedAccommodationsByUserId(String userId, String webShopId);
    Accommodation findById(String id);
}
