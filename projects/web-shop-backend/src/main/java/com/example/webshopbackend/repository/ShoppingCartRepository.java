package com.example.webshopbackend.repository;

import com.example.webshopbackend.model.ShoppingCart;
import com.example.webshopbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, String> {
    @Query(value = "SELECT sc from ShoppingCart sc WHERE sc.user.id = :userId")
    List<ShoppingCart> findShoppingCartsForUser(String userId);
}
