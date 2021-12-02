package com.example.webshopbackend.service.impl;

import com.example.webshopbackend.dto.UserRegistrationDTO;
import com.example.webshopbackend.general.EmailService;
import com.example.webshopbackend.model.Authority;
import com.example.webshopbackend.model.User;
import com.example.webshopbackend.model.enums.Role;
import com.example.webshopbackend.repository.UserRepository;
import com.example.webshopbackend.service.IUserService;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {

    protected final Log log = LogFactory.getLog(getClass());

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public void registerNewCustomer(UserRegistrationDTO dto, String siteUrl) throws Exception {
        log.info("Registering new customer with mail: " + dto.getEmail());
        if (userRepository.findByEmail(dto.getEmail()) != null) {
            log.error("Customer with mail already exists: " + dto.getEmail());
            throw new Exception("Unique mail required!");
        }

        if(!dto.getPassword().equals(dto.getRepeatPassword())) {
            log.error("Passwords are not matching");
            throw new Exception("Passwords are not matching");
        }

        List<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority("9fe91052-ac34-44cb-88a2-6915cfae2d20", "ROLE_CUSTOMER"));

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setAddress(dto.getAddress());
        user.setFullName(dto.getFullName());
        user.setPhone(dto.getPhone());
        user.setRole(Role.Customer);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        String randomCodeForVerification = RandomString.make(64);
        user.setVerificationCode(randomCodeForVerification);
        user.setRegistered(false);
        user.setAuthorities(authorities);
        userRepository.save(user);

        log.info("Sending mail to customer: " + user.getFullName());
        emailService.sendVerificationEmail(user, siteUrl);
    }

    @Override
    public boolean verifyCustomer(String verificationCode) {
        log.info("Verifying customer for successful registration");
        User u = userRepository.getByVerificationCode(verificationCode);

        if (u == null) {
            log.error("Customer not exists with verificationCode");
            return false;
        }

        if (u.isRegistered()) {
            log.error("Customer already registered");
            return false;
        } else {
            u.setVerificationCode(null);
            u.setRegistered(true);
            userRepository.save(u);
            return true;
        }
    }

}
