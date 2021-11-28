package com.example.webshopbackend.dto;

import com.example.webshopbackend.security.UserTokenState;

import javax.persistence.Id;

public class AuthenticatedUserDTO {
    @Id
    private String id;
    private String email;
    private String role;
    private UserTokenState userTokenState;

    public AuthenticatedUserDTO() {
    }

    public AuthenticatedUserDTO(String id, String email, String role, UserTokenState userTokenState) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.userTokenState = userTokenState;
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
}