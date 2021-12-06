package com.example.webshopbackend.service;

import com.example.webshopbackend.model.ShoppingCart;

public interface ITransactionService {
    ShoppingCart getShoppingCartForTransaction(String transactionId);
}
