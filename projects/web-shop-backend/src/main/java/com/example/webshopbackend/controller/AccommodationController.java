package com.example.webshopbackend.controller;

import com.example.webshopbackend.dto.AccommodationDto;
import com.example.webshopbackend.mapper.AccommodationMapper;
import com.example.webshopbackend.model.Accommodation;
import com.example.webshopbackend.model.User;
import com.example.webshopbackend.service.AccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/accommodations")
public class AccommodationController {

    @Autowired
    private AccommodationService accommodationService;

    @GetMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccommodationDto>> findAllAccommodations() {
        List<AccommodationDto> result = new ArrayList<>();

        for(Accommodation accommodation : accommodationService.findAll()) {
            result.add(AccommodationMapper.convertToDto(accommodation));
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
