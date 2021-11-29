package com.example.webshopbackend.repository;

import com.example.webshopbackend.model.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, String> {

    @Query(value = "SELECT a FROM Accommodation a WHERE a.webShop.id = :id")
    List<Accommodation> findAllByWebShopId(String id);
}
