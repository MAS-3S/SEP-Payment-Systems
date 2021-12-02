package com.example.webshopbackend.repository;

import com.example.webshopbackend.model.ItemToPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemToPurchaseRepository extends JpaRepository<ItemToPurchase, String> {
}
