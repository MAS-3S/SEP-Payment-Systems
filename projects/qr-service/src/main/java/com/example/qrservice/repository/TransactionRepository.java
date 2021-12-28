package com.example.qrservice.repository;

import com.example.qrservice.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    Transaction findByOrderId(String orderId);
}
