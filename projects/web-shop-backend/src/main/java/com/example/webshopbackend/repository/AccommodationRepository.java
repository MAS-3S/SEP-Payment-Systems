package com.example.webshopbackend.repository;

import com.example.webshopbackend.model.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccommodationRepository extends JpaRepository<Accommodation, String> {
}
