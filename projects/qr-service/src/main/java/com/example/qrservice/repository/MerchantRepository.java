package com.example.qrservice.repository;

import com.example.qrservice.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, String> {

    @Query(value = "SELECT m FROM Merchant m WHERE m.merchantId = :id")
    Merchant findByMerchantId(String id);

    @Query(value = "SELECT m FROM Merchant m WHERE m.bankMerchantId = :id")
    Merchant findByBankMerchantId(String id);
}
