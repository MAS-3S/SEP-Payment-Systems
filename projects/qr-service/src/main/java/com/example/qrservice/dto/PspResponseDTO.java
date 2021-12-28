package com.example.qrservice.dto;

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

    public String getPaymentId() {
        return paymentId;
    }

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public LocalDateTime getAcquirerOTimeStamp() {
        return acquirerTimeStamp;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

}
