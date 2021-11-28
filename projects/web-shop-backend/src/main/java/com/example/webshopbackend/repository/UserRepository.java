package com.example.webshopbackend.repository;

import com.example.webshopbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Query(value = "SELECT u FROM User u WHERE u.id = :id")
    User findById(String id);

}
