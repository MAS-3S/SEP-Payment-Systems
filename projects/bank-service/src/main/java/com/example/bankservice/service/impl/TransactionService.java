package com.example.bankservice.service.impl;

import com.example.bankservice.dto.CreateTransactionDTO;
import com.example.bankservice.dto.TransactionRequestDTO;
import com.example.bankservice.dto.TransactionResponseDTO;
import com.example.bankservice.model.Merchant;
import com.example.bankservice.model.OrderStatusForMerchant;
import com.example.bankservice.model.Transaction;
import com.example.bankservice.repository.MerchantRepository;
import com.example.bankservice.repository.TransactionRepository;
import com.example.bankservice.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class TransactionService implements ITransactionService {

    protected final Log log = LogFactory.getLog(getClass());

    private static final String HTTP_PREFIX = "http://";

    @Value("${front.host}")
    private String frontHost;
    @Value("${front.port}")
    private String frontPort;
    @Value("${acquirer.host}")
    private String acquirerHost;
    @Value("${acquirer.port}")
    private String acquirerPort;


    @Autowired
    RestTemplate restTemplate;
    private final MerchantRepository merchantRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(MerchantRepository merchantRepository, TransactionRepository transactionRepository) {
        this.merchantRepository = merchantRepository;
        this.transactionRepository = transactionRepository;
    }


    @Override
    @Transactional
    public TransactionResponseDTO createTransaction(CreateTransactionDTO createTransactionDTO) throws Exception {
        if (createTransactionDTO.getMerchantId().trim().equals("") || createTransactionDTO.getMerchantOrderId().trim().equals("")) {
            log.error("merchantId and/or merchantOrderId are null");
            throw new Exception("All ids for creating transaction must be present");
        }

        Merchant merchant = merchantRepository.findByMerchantId(createTransactionDTO.getMerchantId());
        log.info("Getting merchant with id "+ createTransactionDTO.getMerchantOrderId());
        if (merchant == null) {
            log.error("Merchant with id " + createTransactionDTO.getMerchantOrderId() + " does not exists");
            throw new Exception("Merchant with id " + createTransactionDTO.getMerchantOrderId() + " does not exists");
        }

        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO();
        transactionRequestDTO.setDescription("");
        transactionRequestDTO.setAmount(createTransactionDTO.getAmount());
        transactionRequestDTO.setMerchantId(merchant.getBankMerchantId());
        transactionRequestDTO.setMerchantPassword(merchant.getBankMerchantPassword());
        transactionRequestDTO.setMerchantOrderId(createTransactionDTO.getMerchantOrderId());
        transactionRequestDTO.setMerchantTimestamp(LocalDateTime.now());
        transactionRequestDTO.setSuccessUrl(HTTP_PREFIX + this.frontHost + ":" + this.frontPort + "/successTransaction/" + createTransactionDTO.getMerchantOrderId());
        transactionRequestDTO.setFailedUrl(HTTP_PREFIX + this.frontHost + ":" + this.frontPort + "/failTransaction/" + createTransactionDTO.getMerchantOrderId());
        transactionRequestDTO.setErrorUrl(HTTP_PREFIX + this.frontHost + ":" + this.frontPort + "/errorTransaction/" + createTransactionDTO.getMerchantOrderId());

        HttpEntity<TransactionRequestDTO> transactionRequestEntity = new HttpEntity<>(transactionRequestDTO);
        ResponseEntity<TransactionResponseDTO> transactionResponseEntity;

        try {
            log.info("Calling acquirer bank to create payment id and url");
            transactionResponseEntity = restTemplate.postForEntity(getAcquirerUrl() + "/transactions/check", transactionRequestEntity, TransactionResponseDTO.class);
        } catch (Exception e) {
            log.error("Error while calling acquirer bank");
            return new TransactionResponseDTO("", "", false, "Error while calling acquirer bank");
        }


        if (transactionResponseEntity.getBody() == null || !Objects.requireNonNull(transactionResponseEntity.getBody()).isSuccess()) {
            log.error("Error response from acquirer bank");
            return new TransactionResponseDTO("", "", false, Objects.requireNonNull(transactionResponseEntity.getBody()).getMessage());
        }

        Transaction transaction = new Transaction();
        transaction.setBankTransactionId(transactionResponseEntity.getBody().getPaymentId());
        transaction.setAmount(createTransactionDTO.getAmount());
        transaction.setStatus(OrderStatusForMerchant.IN_PROGRESS);
        transaction.setMerchant(merchant);
        transaction.setOrderId(createTransactionDTO.getMerchantOrderId());
        transaction.setTimestamp(transactionRequestDTO.getMerchantTimestamp());
        transaction.setReturnUrl(transactionResponseEntity.getBody().getPaymentUrl());
        transactionRepository.save(transaction);
        log.info("Saved transaction with merchanId " + transaction.getMerchant().getMerchantId() + " at " + transaction.getTimestamp());

        return new TransactionResponseDTO(transactionResponseEntity.getBody().getPaymentId(),
                transactionResponseEntity.getBody().getPaymentUrl(), true, transactionResponseEntity.getBody().getMessage());

    }

    private String getAcquirerUrl() {
        return HTTP_PREFIX + this.acquirerHost + ":" + this.acquirerPort + "/api";
    }
}
