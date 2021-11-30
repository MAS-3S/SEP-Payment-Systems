package com.example.webshopbackend.mapper;

import com.example.webshopbackend.dto.ConferenceDto;
import com.example.webshopbackend.dto.WebShopDto;
import com.example.webshopbackend.model.Conference;
import com.example.webshopbackend.model.WebShop;
import com.example.webshopbackend.model.enums.Currency;
import com.example.webshopbackend.model.enums.WebShopType;

public class WebShopMapper {
    public static WebShopDto convertToDto(WebShop webShop) {
        WebShopDto dto = new WebShopDto();

        dto.setId(webShop.getId());
        dto.setName(webShop.getName());
        if(webShop.getType().equals(WebShopType.PRODUCT)) {
            dto.setType("PRODUCT");
        } else if(webShop.getType().equals(WebShopType.CONFERENCE)) {
            dto.setType("CONFERENCE");
        } else if(webShop.getType().equals(WebShopType.ACCOMMODATION)) {
            dto.setType("ACCOMMODATION");
        }

        return dto;
    }

    public static WebShop convertToEntity(WebShopDto dto) {
        WebShop webShop = new WebShop();

        return webShop;
    }
}
