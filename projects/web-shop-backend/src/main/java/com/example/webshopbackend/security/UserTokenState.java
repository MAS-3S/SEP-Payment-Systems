package com.example.webshopbackend.security;

public class UserTokenState {

    private String accessToken;
    private Long expiresIn;
    private String refreshToken;

    public UserTokenState() {
        this.accessToken = null;
        this.expiresIn = null;
        this.refreshToken = null;
    }

    public UserTokenState(String accessToken, long expiresIn, String refreshToken) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
