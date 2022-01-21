package com.example.bankservice.service;

import com.example.bankservice.dto.*;

public interface ITransactionService {

    TransactionResponseDTO createTransaction(CreateTransactionDTO dto) throws Exception;
    void finishTransaction(PspResponseDTO dto);
    boolean checkIfMerchantExists(String merchantId) throws Exception;
    WageResponse paymentWage(WageTransactionRequest wageTransactionRequest) throws  Exception;
}
