package com.example.pspservice.dto;

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

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
