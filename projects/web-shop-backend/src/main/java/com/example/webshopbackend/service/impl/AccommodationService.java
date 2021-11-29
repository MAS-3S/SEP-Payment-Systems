package com.example.webshopbackend.service.impl;

import com.example.webshopbackend.model.Accommodation;
import com.example.webshopbackend.repository.AccommodationRepository;
import com.example.webshopbackend.service.IAccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccommodationService implements IAccommodationService {

    private final AccommodationRepository accommodationRepository;

    @Autowired
    public AccommodationService(AccommodationRepository accommodationRepository) {
        this.accommodationRepository = accommodationRepository;
    }

    @Override
    public List<Accommodation> findAllByWebShopId(String id) {
        return accommodationRepository.findAllByWebShopId(id);
    }
}
