package com.example.webshopbackend.repository;

import com.example.webshopbackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    @Query(value = "SELECT p FROM Product p WHERE p.webShop.id = :id")
    List<Product> findAllByWebShopId(String id);
}
