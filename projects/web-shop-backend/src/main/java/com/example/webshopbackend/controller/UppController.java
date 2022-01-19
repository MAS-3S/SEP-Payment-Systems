package com.example.webshopbackend.controller;

import com.example.webshopbackend.dto.UppDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/upp")
public class UppController {

    @PostMapping("/pay")
    public ResponseEntity<UppDto> pay(@RequestBody UppDto dto) {
        UppDto result = new UppDto();
        result.setPrice(dto.getPrice());

        if(dto.getPrice() < 500.0) {
            result.setSuccess(true);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<UppDto> test() {
        UppDto result = new UppDto();
        result.setPrice(500.0);
        result.setSuccess(true);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
