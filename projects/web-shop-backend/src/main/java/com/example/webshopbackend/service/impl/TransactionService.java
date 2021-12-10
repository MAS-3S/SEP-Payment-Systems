package com.example.webshopbackend.service.impl;

import com.example.webshopbackend.model.ItemToPurchase;
import com.example.webshopbackend.model.Product;
import com.example.webshopbackend.model.ShoppingCart;
import com.example.webshopbackend.model.Transaction;
import com.example.webshopbackend.repository.ProductRepository;
import com.example.webshopbackend.repository.TransactionRepository;
import com.example.webshopbackend.service.ITransactionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService implements ITransactionService {

    protected final Log log = LogFactory.getLog(getClass());

    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, ProductRepository productRepository) {
        this.transactionRepository = transactionRepository;
        this.productRepository = productRepository;
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
    @Transactional
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public void changeAvailableBalance(String transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId).orElse(null);
        if (transaction == null) {
            return;
        }

        ShoppingCart shoppingCart = transaction.getShoppingCart();
        List<ItemToPurchase> items = shoppingCart.getItems();
        for (ItemToPurchase item : items) {
            if ((item.getProduct().getAvailableBalance() - item.getQuantity()) > 0) {
                Product product = item.getProduct();
                product.setAvailableBalance(product.getAvailableBalance() - item.getQuantity());
                productRepository.save(product);
            }

        }

    }
}
