package com.example.webshopbackend.controller;

import com.example.webshopbackend.dto.ConferenceDto;
import com.example.webshopbackend.mapper.ConferenceMapper;
import com.example.webshopbackend.model.Conference;
import com.example.webshopbackend.service.IConferenceService;
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
@RequestMapping(value = "api/conferences")
public class ConferenceController {

    @Autowired
    private IConferenceService conferenceService;

    @GetMapping(value="/webshop/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ConferenceDto>> findAllConferences(@PathVariable String id) {
        List<ConferenceDto> result = new ArrayList<>();
        List<Conference> conferences = conferenceService.findAllByWebShopId(id);
//        "999c0c7c-aff3-41df-957d-d0677d43007b"
        if(conferences.isEmpty()) {
            return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
        }

        for(Conference conference : conferences) {
            result.add(ConferenceMapper.convertToDto(conference));
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
