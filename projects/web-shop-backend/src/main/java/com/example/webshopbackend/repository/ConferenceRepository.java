package com.example.webshopbackend.repository;

import com.example.webshopbackend.model.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, String> {

    @Query(value = "SELECT c FROM Conference c WHERE c.webShop.id = :id")
    List<Conference> findAllByWebShopId(String id);
}
