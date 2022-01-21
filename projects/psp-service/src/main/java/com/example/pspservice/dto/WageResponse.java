package com.example.pspservice.dto;

public class WageResponse {

    private boolean isSuccess;
    private String message;

    public WageResponse() {
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
