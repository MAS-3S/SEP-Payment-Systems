package com.example.bankservice.repository;

import com.example.bankservice.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {


}
