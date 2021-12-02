package com.example.pspservice.repository;

import com.example.pspservice.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, String> {

    Merchant findByMerchantId(String id);
}
