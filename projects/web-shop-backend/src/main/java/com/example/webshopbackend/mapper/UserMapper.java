package com.example.webshopbackend.mapper;

import com.example.webshopbackend.dto.ProductDto;
import com.example.webshopbackend.dto.UserDto;
import com.example.webshopbackend.dto.UserRegistrationDTO;
import com.example.webshopbackend.model.Product;
import com.example.webshopbackend.model.User;
import com.example.webshopbackend.model.enums.Currency;
import com.example.webshopbackend.model.enums.Role;

public class UserMapper {

    public static UserDto convertToDto(User user) {
        UserDto dto = new UserDto();

        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setAddress(user.getAddress());
        dto.setPhone(user.getPhone());
        if(user.getRole().equals(Role.Admin)) {
            dto.setRole("ADMIN");
        } else if(user.getRole().equals(Role.Customer)) {
            dto.setRole("CUSTOMER");
        }

        return dto;
    }

    public static User convertToEntity(UserDto dto) {
        User user = new User();

        return user;
    }
}
