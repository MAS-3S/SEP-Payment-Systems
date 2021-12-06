package com.example.bankservice.service;

import com.example.bankservice.dto.CreateTransactionDTO;
import com.example.bankservice.dto.TransactionResponseDTO;

public interface ITransactionService {

    TransactionResponseDTO createTransaction(CreateTransactionDTO dto) throws Exception;

}
