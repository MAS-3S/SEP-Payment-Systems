package com.example.webshopbackend.service;

import com.example.webshopbackend.model.Conference;

import java.util.List;

public interface IConferenceService {
    List<Conference> findAllByWebShopId(String id);
}
