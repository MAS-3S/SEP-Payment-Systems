package com.example.webshopbackend.repository;

import com.example.webshopbackend.model.WebShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebShopRepository extends JpaRepository<WebShop, String> {
    List<WebShop> findAll();
}
