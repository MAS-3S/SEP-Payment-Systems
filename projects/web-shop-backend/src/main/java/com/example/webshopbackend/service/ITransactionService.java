package com.example.webshopbackend.service;

import com.example.webshopbackend.model.ShoppingCart;
import com.example.webshopbackend.model.Transaction;

public interface ITransactionService {
    ShoppingCart getShoppingCartForTransaction(String transactionId);
    Transaction getById(String id);
    Transaction save(Transaction transaction);
}
