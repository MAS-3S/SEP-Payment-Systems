package com.example.webshopbackend.service;

import com.example.webshopbackend.dto.UserRegistrationDTO;

public interface IUserService {

    void registerNewCustomer(UserRegistrationDTO dto, String siteUrl) throws Exception;
    boolean verifyCustomer(String verificationCode);

}
