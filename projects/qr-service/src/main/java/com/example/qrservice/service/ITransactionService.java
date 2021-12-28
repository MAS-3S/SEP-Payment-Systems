package com.example.qrservice.service;

import com.example.qrservice.dto.CreateTransactionDTO;
import com.example.qrservice.dto.PspResponseDTO;
import com.example.qrservice.dto.TransactionResponseDTO;

public interface ITransactionService {

    TransactionResponseDTO createTransaction(CreateTransactionDTO dto) throws Exception;
    void finishTransaction(PspResponseDTO dto);
    boolean checkIfMerchantExists(String merchantId) throws Exception;

}
