package com.example.testservice.controller;

import com.example.testservice.TestServiceApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/test")
public class TestController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/message")
    public String test() {
        log.info("------------------- test -------------------");
        return "Radi test servis";
    }
}
