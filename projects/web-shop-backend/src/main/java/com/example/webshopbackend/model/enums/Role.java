package com.example.webshopbackend.model.enums;

public enum Role {
    Admin(Values.Admin), Customer(Values.Customer);

    Role(String value) {
        if (!this.name().equals(value))
            throw new IllegalArgumentException("Incorrect use of Role!");
    }

    public static class Values {
        public static final String Customer = "Customer";
        public static final String Admin = "Admin";
    }
}
