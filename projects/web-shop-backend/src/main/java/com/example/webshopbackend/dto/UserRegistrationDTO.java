package com.example.webshopbackend.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserRegistrationDTO {

    @NotEmpty(message = "insert email")
    @Email
    private String email;
    @NotEmpty(message = "insert name")
    private String fullName;
    @NotEmpty(message = "insert address")
    private String address;
    @NotEmpty(message = "insert phone")
    private String phone;
    @NotEmpty(message = "insert password")
    private String password;
    @NotEmpty(message = "insert repeated password")
    private String repeatPassword;

    public UserRegistrationDTO() {
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }
}
