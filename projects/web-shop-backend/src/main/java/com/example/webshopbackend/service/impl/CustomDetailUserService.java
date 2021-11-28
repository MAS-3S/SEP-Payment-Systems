package com.example.webshopbackend.service.impl;

import com.example.webshopbackend.model.User;
import com.example.webshopbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;

@Service
public class CustomDetailUserService implements UserDetailsService {


    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        User userAccount = userRepository.findByEmail(mail);
        if (userAccount == null) {
            throw new UsernameNotFoundException(String.format("No user found with mail '%s'.", mail));
        } else {
            return userAccount;
        }
    }
}
