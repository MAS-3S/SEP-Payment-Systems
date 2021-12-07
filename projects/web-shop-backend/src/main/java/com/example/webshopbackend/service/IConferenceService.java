package com.example.webshopbackend.service;

import com.example.webshopbackend.dto.ItemToPurchaseDto;
import com.example.webshopbackend.model.Conference;

import java.util.List;
import java.util.Set;

public interface IConferenceService {
    List<Conference> findAllByWebShopId(String id);
    Set<ItemToPurchaseDto> findAllPayedConferencesByUserId(String id);
    Conference findById(String id);
}
