package com.example.webshopbackend.mapper;

import com.example.webshopbackend.dto.ConferenceDto;
import com.example.webshopbackend.model.Conference;
import com.example.webshopbackend.model.enums.Currency;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

public class ConferenceMapper {

    public static ConferenceDto convertToDto(Conference conference) {
        ConferenceDto dto = new ConferenceDto();

        dto.setId(conference.getId());
        dto.setName(conference.getName());
        dto.setDescription(conference.getDescription());
        dto.setImage(decodeBase64(conference.getImage()));
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
