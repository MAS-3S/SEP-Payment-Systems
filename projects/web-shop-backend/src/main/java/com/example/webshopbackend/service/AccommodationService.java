package com.example.webshopbackend.service;

import com.example.webshopbackend.model.Accommodation;
import com.example.webshopbackend.repository.AccommodationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccommodationService {

    @Autowired
    private AccommodationRepository accommodationRepository;

    public List<Accommodation> findAll() { return accommodationRepository.findAll(); }
}
