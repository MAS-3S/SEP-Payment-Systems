package com.example.bitcoinservice.service;

import com.example.bitcoinservice.dto.CreateTransactionDTO;
import com.example.bitcoinservice.dto.TransactionResponseDTO;

public interface ITransactionService {

    TransactionResponseDTO createTransaction(CreateTransactionDTO dto) throws Exception;
}
