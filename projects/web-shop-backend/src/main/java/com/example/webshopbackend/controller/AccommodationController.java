package com.example.webshopbackend.controller;

import com.example.webshopbackend.dto.AccommodationDto;
import com.example.webshopbackend.mapper.AccommodationMapper;
import com.example.webshopbackend.model.Accommodation;
import com.example.webshopbackend.service.IAccommodationService;
import com.example.webshopbackend.service.impl.AccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/accommodations")
public class AccommodationController {

    @Autowired
    private IAccommodationService accommodationService;

    @GetMapping(value="/webshop/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccommodationDto>> findAllAccommodations(@PathVariable String id) {
        List<AccommodationDto> result = new ArrayList<>();
        List<Accommodation> accommodations = accommodationService.findAllByWebShopId(id);
//        "4086f1d4-4002-4f1e-9bf8-ab0e82048584"
        if(accommodations.isEmpty()) {
            return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
        }

        for(Accommodation accommodation : accommodations) {
            result.add(AccommodationMapper.convertToDto(accommodation));
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
