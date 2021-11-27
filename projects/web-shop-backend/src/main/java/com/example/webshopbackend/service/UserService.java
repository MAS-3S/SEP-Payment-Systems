package com.example.webshopbackend.service;

import com.example.webshopbackend.model.User;
import com.example.webshopbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {return userRepository.findAll();}
}
