package com.example.webshopbackend.mapper;

import com.example.webshopbackend.dto.AccommodationDto;
import com.example.webshopbackend.model.Accommodation;
import com.example.webshopbackend.model.enums.Currency;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

public class AccommodationMapper {

    public static AccommodationDto convertToDto(Accommodation accommodation) {
        AccommodationDto dto = new AccommodationDto();

        dto.setId(accommodation.getId());
        dto.setName(accommodation.getName());
        dto.setDescription(accommodation.getDescription());
        dto.setImage(decodeBase64(accommodation.getImage()));
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
        if(accommodation.getTransport().getCurrency().equals(Currency.RSD)) {
            dto.setTransportCurrency("RSD");
        } else if (accommodation.getTransport().getCurrency().equals(Currency.EUR)) {
            dto.setTransportCurrency("EUR");
        }
        return dto;
    }

    public static Accommodation convertToEntity(AccommodationDto dto) {
        Accommodation accommodation = new Accommodation();

        return accommodation;
    }

    private static String decodeBase64(String image) {
        File currDir = new File(System.getProperty("user.dir"));
        File assetFolder = new File(currDir, "src/main/java/com/example/webshopbackend/assets/images");
        File imagePath = new File(assetFolder, image);
        String encodedFile = "";
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(imagePath);
            byte[] bytes = new byte[(int)imagePath.length()];
            fileInputStreamReader.read(bytes);
            encodedFile = Base64.getEncoder().encodeToString(bytes);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "data:image/jpg;base64," + encodedFile;
    }
}