package com.example.bitcoinservice.dto;

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

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getFailedUrl() {
        return failedUrl;
    }

    public void setFailedUrl(String failedUrl) {
        this.failedUrl = failedUrl;
    }

    public String getErrorUrl() {
        return errorUrl;
    }

    public void setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
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
