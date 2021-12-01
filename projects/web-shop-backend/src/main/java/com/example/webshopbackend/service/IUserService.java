package com.example.webshopbackend.service;

import com.example.webshopbackend.dto.UserRegistrationDTO;
import com.example.webshopbackend.model.User;

public interface IUserService {

    User findById(String id);
    void registerNewCustomer(UserRegistrationDTO dto, String siteUrl) throws Exception;
    boolean verifyCustomer(String verificationCode);

}
