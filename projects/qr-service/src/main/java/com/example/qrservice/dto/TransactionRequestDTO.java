package com.example.qrservice.dto;

import java.time.LocalDateTime;

public class TransactionRequestDTO {

    private String merchantId;
    private String merchantPassword;
    private String merchantOrderId;
    private LocalDateTime merchantTimestamp;
    private Double amount;
    private String description;
    private String successUrl;
    private String failedUrl;
    private String errorUrl;

    public TransactionRequestDTO() {
    }


    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }


    public void setMerchantPassword(String merchantPassword) {
        this.merchantPassword = merchantPassword;
    }


    public void setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public LocalDateTime getMerchantTimestamp() {
        return merchantTimestamp;
    }

    public void setMerchantTimestamp(LocalDateTime merchantTimestamp) {
        this.merchantTimestamp = merchantTimestamp;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public void setFailedUrl(String failedUrl) {
        this.failedUrl = failedUrl;
    }

    public void setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
    }
}
