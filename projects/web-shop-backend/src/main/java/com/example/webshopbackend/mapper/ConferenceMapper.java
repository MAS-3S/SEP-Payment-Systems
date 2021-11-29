package com.example.webshopbackend.mapper;

import com.example.webshopbackend.dto.ConferenceDto;
import com.example.webshopbackend.model.Conference;
import com.example.webshopbackend.model.enums.Currency;

public class ConferenceMapper {

    public static ConferenceDto convertToDto(Conference conference) {
        ConferenceDto dto = new ConferenceDto();

        dto.setName(conference.getName());
        dto.setDescription(conference.getDescription());
        dto.setImage(conference.getImage());
        dto.setPrice(conference.getPrice());
        dto.setAvailableBalance(conference.getAvailableBalance());
        if(conference.getCurrency().equals(Currency.RSD)) {
            dto.setCurrency("RSD");
        } else if (conference.getCurrency().equals(Currency.EUR)) {
            dto.setCurrency("EUR");
        }
        dto.setAddress(conference.getAddress());
        dto.setStartTime(conference.getStartTime());
        dto.setEndTime(conference.getEndTime());
        dto.setCourse(conference.getCourse());
        dto.setSubscription(conference.getSubscription());

        return dto;
    }

    public static Conference convertToEntity(ConferenceDto dto) {
        Conference conference = new Conference();

        return conference;
    }
}
