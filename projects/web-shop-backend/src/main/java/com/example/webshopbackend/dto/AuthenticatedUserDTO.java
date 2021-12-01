package com.example.webshopbackend.dto;

import com.example.webshopbackend.security.UserTokenState;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

public class AuthenticatedUserDTO {

    @Id
    private String id;
    private String email;
    private String role;
    private UserTokenState userTokenState;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime tokenExpiresDate;

    public AuthenticatedUserDTO() {
    }

    public AuthenticatedUserDTO(String id, String email, String role, UserTokenState userTokenState) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.userTokenState = userTokenState;
        this.tokenExpiresDate = LocalDateTime.now().plus(this.userTokenState.getExpiresIn(), ChronoField.MILLI_OF_DAY.getBaseUnit());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserTokenState getUserTokenState() {
        return userTokenState;
    }

    public void setUserTokenState(UserTokenState userTokenState) {
        this.userTokenState = userTokenState;
    }

    public LocalDateTime getTokenExpiresDate() {
        return tokenExpiresDate;
    }

    public void setTokenExpiresDate(LocalDateTime tokenExpiresDate) {
        this.tokenExpiresDate = tokenExpiresDate;
    }
}