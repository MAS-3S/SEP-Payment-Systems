package com.example.webshopbackend.mapper;

import com.example.webshopbackend.dto.AccommodationDto;
import com.example.webshopbackend.model.Accommodation;

public class AccommodationMapper {

    public static AccommodationDto convertToDto(Accommodation accommodation) {
        AccommodationDto dto = new AccommodationDto();

        dto.setName(accommodation.getName());
        dto.setDescription(accommodation.getDescription());
        dto.setImage(accommodation.getImage());
        dto.setPrice(accommodation.getPrice());
        dto.setAvailableBalance(accommodation.getAvailableBalance());
        dto.setAddress(accommodation.getAddress());
        dto.setStartDate(accommodation.getStartDate());
        dto.setDays(accommodation.getDays());
        dto.setNumberOfBads(accommodation.getNumberOfBeds());
        dto.setTransportName(accommodation.getTransport().getName());
        dto.setTransportPrice(accommodation.getTransport().getPrice());

        return dto;
    }

    public static Accommodation convertToEntity(AccommodationDto dto) {
        Accommodation accommodation = new Accommodation();

        return accommodation;
    }
}