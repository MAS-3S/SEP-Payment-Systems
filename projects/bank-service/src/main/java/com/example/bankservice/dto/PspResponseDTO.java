package com.example.bankservice.dto;

import java.time.LocalDateTime;

public class PspResponseDTO {

    private String acquirerOrderId;
    private String paymentId;
    private String merchantOrderId;
    private LocalDateTime acquirerTimeStamp;
    private boolean isSuccess;

    public PspResponseDTO() {
    }

    public PspResponseDTO(String acquirerOrderId, String paymentId, String merchantOrderId, LocalDateTime acquirerOTimeStamp, boolean isSuccess) {
        this.acquirerOrderId = acquirerOrderId;
        this.paymentId = paymentId;
        this.merchantOrderId = merchantOrderId;
        this.acquirerTimeStamp = acquirerOTimeStamp;
        this.isSuccess = isSuccess;
    }

    public String getAcquirerOrderId() {
        return acquirerOrderId;
    }

    public void setAcquirerOrderId(String acquirerOrderId) {
        this.acquirerOrderId = acquirerOrderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public LocalDateTime getAcquirerOTimeStamp() {
        return acquirerTimeStamp;
    }

    public void setAcquirerOTimeStamp(LocalDateTime acquirerOTimeStamp) {
        this.acquirerTimeStamp = acquirerOTimeStamp;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
