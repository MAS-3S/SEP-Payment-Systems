package com.example.webshopbackend.service.impl;

import com.example.webshopbackend.model.Conference;
import com.example.webshopbackend.repository.ConferenceRepository;
import com.example.webshopbackend.service.IConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConferenceService implements IConferenceService {

    private final ConferenceRepository conferenceRepository;

    @Autowired
    public ConferenceService(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
    }

    @Override
    public List<Conference> findAllByWebShopId(String id) {
        return conferenceRepository.findAllByWebShopId(id);
    }
}
