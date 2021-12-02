package com.example.pspservice.repository;

import com.example.pspservice.model.PaymentMethodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentMethodTypeRepository extends JpaRepository<PaymentMethodType, String> {



}
