package com.example.qrservice.dto;

public class TransactionResponseDTO {

    private String paymentId;
    private String paymentUrl;
    private boolean success;
    private String message;

    public TransactionResponseDTO() {
    }

    public TransactionResponseDTO(String paymentId, String paymentUrl, boolean success, String message) {
        this.paymentId = paymentId;
        this.paymentUrl = paymentUrl;
        this.success = success;
        this.message = message;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

}
