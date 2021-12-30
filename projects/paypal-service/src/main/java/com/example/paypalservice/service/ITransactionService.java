package com.example.paypalservice.service;

import com.example.paypalservice.dto.CreateTransactionDTO;
import com.example.paypalservice.dto.PayPalTransactionDTO;
import com.example.paypalservice.dto.TransactionResponseDTO;

public interface ITransactionService {

    TransactionResponseDTO createTransaction(CreateTransactionDTO createTransactionDTO) throws Exception;
    boolean checkIfMerchantExists(String merchantId) throws Exception;
    PayPalTransactionDTO getTransactionForPayPalBy(String transactionId) throws Exception;

}
