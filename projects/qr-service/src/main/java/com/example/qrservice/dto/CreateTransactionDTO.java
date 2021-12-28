package com.example.qrservice.dto;

import java.time.LocalDateTime;

public class CreateTransactionDTO {

    String merchantId;
    String merchantOrderId;
    Double amount;
    LocalDateTime time;
    String successUrl;
    String failedUrl;
    String errorUrl;

    public CreateTransactionDTO() {
    }

    public String getMerchantId() {
        return merchantId;
    }

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public Double getAmount() {
        return amount;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public String getFailedUrl() {
        return failedUrl;
    }

    public String getErrorUrl() {
        return errorUrl;
    }

    public String getSuccessUrlWithOrderId() {
        return this.getSuccessUrl() + this.getMerchantOrderId();
    }

    public String getFailedUrlWithOrderId() {
        return this.getFailedUrl() + this.getMerchantOrderId();
    }

    public String getErrorUrlWithOrderId() {
        return this.getErrorUrl() + this.getMerchantOrderId();
    }

}
