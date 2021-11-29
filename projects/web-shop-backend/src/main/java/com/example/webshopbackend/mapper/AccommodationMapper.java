package com.example.webshopbackend.mapper;

import com.example.webshopbackend.dto.AccommodationDto;
import com.example.webshopbackend.model.Accommodation;
import com.example.webshopbackend.model.enums.Currency;

public class AccommodationMapper {

    public static AccommodationDto convertToDto(Accommodation accommodation) {
        AccommodationDto dto = new AccommodationDto();

        dto.setName(accommodation.getName());
        dto.setDescription(accommodation.getDescription());
        dto.setImage(accommodation.getImage());
        dto.setPrice(accommodation.getPrice());
        dto.setAvailableBalance(accommodation.getAvailableBalance());
        if(accommodation.getCurrency().equals(Currency.RSD)) {
            dto.setCurrency("RSD");
        } else if (accommodation.getCurrency().equals(Currency.EUR)) {
            dto.setCurrency("EUR");
        }
        dto.setAddress(accommodation.getAddress());
        dto.setStartDate(accommodation.getStartDate());
        dto.setDays(accommodation.getDays());
        dto.setNumberOfBeds(accommodation.getNumberOfBeds());
        dto.setTransportName(accommodation.getTransport().getName());
        dto.setTransportPrice(accommodation.getTransport().getPrice());

        return dto;
    }

    public static Accommodation convertToEntity(AccommodationDto dto) {
        Accommodation accommodation = new Accommodation();

        return accommodation;
    }
}