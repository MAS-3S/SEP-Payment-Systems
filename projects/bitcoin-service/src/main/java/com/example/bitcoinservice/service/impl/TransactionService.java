package com.example.bitcoinservice.service.impl;

import com.example.bitcoinservice.dto.CreateTransactionDTO;
import com.example.bitcoinservice.dto.TransactionResponseDTO;
import com.example.bitcoinservice.model.Merchant;
import com.example.bitcoinservice.repository.MerchantRepository;
import com.example.bitcoinservice.repository.TransactionRepository;
import com.example.bitcoinservice.service.ITransactionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service
public class TransactionService implements ITransactionService {

    protected final Log log = LogFactory.getLog(getClass());
    private static final String HTTP_PREFIX = "http://";
    private static final String HTTPS_PREFIX = "https://";

    private final TransactionRepository transactionRepository;
    private final MerchantRepository merchantRepository;

    public TransactionService(TransactionRepository transactionRepository, MerchantRepository merchantRepository) {
        this.transactionRepository = transactionRepository;
        this.merchantRepository = merchantRepository;
    }

    @Override
    public TransactionResponseDTO createTransaction(CreateTransactionDTO createTransactionDTO) throws Exception {
        log.info("Creating new transaction");

        if (createTransactionDTO.getMerchantId().trim().equals("") || createTransactionDTO.getMerchantOrderId().trim().equals("")) {
            log.error("merchantId and/or merchantOrderId are null");
            throw new Exception("All ids for creating transaction must be present");
        }

        Merchant merchant = merchantRepository.findByMerchantId(createTransactionDTO.getMerchantId());
        log.info("Getting merchant with id " + createTransactionDTO.getMerchantOrderId());
        if (merchant == null) {
            log.error("Merchant with id " + createTransactionDTO.getMerchantOrderId() + " does not exists");
            throw new Exception("Merchant with id " + createTransactionDTO.getMerchantOrderId() + " does not exists");
        }

        

        return null;
    }
}
