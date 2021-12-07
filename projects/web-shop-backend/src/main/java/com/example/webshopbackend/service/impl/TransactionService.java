package com.example.webshopbackend.service.impl;

import com.example.webshopbackend.model.ShoppingCart;
import com.example.webshopbackend.model.Transaction;
import com.example.webshopbackend.repository.TransactionRepository;
import com.example.webshopbackend.service.ITransactionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService implements ITransactionService {

    protected final Log log = LogFactory.getLog(getClass());

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public ShoppingCart getShoppingCartForTransaction(String transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId).orElse(null);
        if(transaction == null) {
            return null;
        }
        return transaction.getShoppingCart();
    }

    @Override
    public Transaction getById(String id) {
        return transactionRepository.getById(id);
    }

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}
