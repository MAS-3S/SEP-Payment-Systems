package com.example.webshopbackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.regex.Pattern;

public class ItemToPurchaseDto {

    String productId;
    ProductDto productDto;
    ConferenceDto conferenceDto;
    AccommodationDto accommodationDto;
    int quantity;
    @JsonFormat(pattern = "dd.MM.yyyy.")
    private LocalDateTime date;
    @JsonFormat(pattern = "isPossibleSubscription")
    boolean isPossibleSubscription;

    public ItemToPurchaseDto() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public ProductDto getProductDto() {
        return productDto;
    }

    public void setProductDto(ProductDto productDto) {
        this.productDto = productDto;
    }

    public ConferenceDto getConferenceDto() {
        return conferenceDto;
    }

    public void setConferenceDto(ConferenceDto conferenceDto) {
        this.conferenceDto = conferenceDto;
    }

    public AccommodationDto getAccommodationDto() {
        return accommodationDto;
    }

    public void setAccommodationDto(AccommodationDto accommodationDto) {
        this.accommodationDto = accommodationDto;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean isPossibleSubscription() {
        return isPossibleSubscription;
    }

    public void setPossibleSubscription(boolean possibleSubscription) {
        isPossibleSubscription = possibleSubscription;
    }
}
