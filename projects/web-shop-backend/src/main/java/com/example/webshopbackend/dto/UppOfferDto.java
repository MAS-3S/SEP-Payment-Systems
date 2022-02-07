package com.example.webshopbackend.dto;

import java.util.List;

public class UppOfferDto {
    List<UppOfferItemDto> offerItems;

    public UppOfferDto() {
    }

    public List<UppOfferItemDto> getOfferItems() {
        return offerItems;
    }

    public void setOfferItems(List<UppOfferItemDto> offerItems) {
        this.offerItems = offerItems;
    }
}
