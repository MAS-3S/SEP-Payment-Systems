package com.example.pspservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SubscribeUrlDTO {


    private String merchantId;

    public SubscribeUrlDTO() {
    }

    public SubscribeUrlDTO(String merchantId) {
        this.merchantId = merchantId;
    }

    @JsonFormat(pattern = "merchantId")
    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }
}
