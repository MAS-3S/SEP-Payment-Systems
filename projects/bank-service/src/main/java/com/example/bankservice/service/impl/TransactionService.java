package com.example.bankservice.service.impl;

import com.example.bankservice.dto.*;
import com.example.bankservice.model.Merchant;
import com.example.bankservice.model.TransactionStatus;
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
    private static final String HTTPS_PREFIX = "https://";

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
        log.info("Creating new transaction");

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
        transactionRequestDTO.setCurrency(createTransactionDTO.getCurrency());
        transactionRequestDTO.setSuccessUrl(createTransactionDTO.getSuccessUrlWithOrderId());
        transactionRequestDTO.setFailedUrl(createTransactionDTO.getFailedUrlWithOrderId());
        transactionRequestDTO.setErrorUrl(createTransactionDTO.getErrorUrlWithOrderId());

        HttpEntity<TransactionRequestDTO> transactionRequestEntity = new HttpEntity<>(transactionRequestDTO);
        ResponseEntity<TransactionResponseDTO> transactionResponseEntity;

        try {
            log.info("Calling acquirer bank to create payment id and url");
            transactionResponseEntity = restTemplate.postForEntity(getAcquirerUrl() + "/transactions/check", transactionRequestEntity, TransactionResponseDTO.class);
        } catch (Exception e) {
            log.error("Error while calling acquirer bank");
            return new TransactionResponseDTO("", createTransactionDTO.getErrorUrlWithOrderId(), false, "Error while calling acquirer bank");
        }

        if (transactionResponseEntity.getBody() == null || !Objects.requireNonNull(transactionResponseEntity.getBody()).isSuccess()) {
            log.error("Error response from acquirer bank");
            return new TransactionResponseDTO("", createTransactionDTO.getFailedUrlWithOrderId(), false, Objects.requireNonNull(transactionResponseEntity.getBody()).getMessage());
        }

        Transaction transaction = new Transaction();
        transaction.setBankTransactionId(transactionResponseEntity.getBody().getPaymentId());
        transaction.setAmount(createTransactionDTO.getAmount());
        transaction.setCurrency(createTransactionDTO.getCurrency());
        transaction.setStatus(TransactionStatus.IN_PROGRESS);
        transaction.setMerchant(merchant);
        transaction.setOrderId(createTransactionDTO.getMerchantOrderId());
        transaction.setTimestamp(transactionRequestDTO.getMerchantTimestamp());
        transaction.setReturnUrl(transactionResponseEntity.getBody().getPaymentUrl());
        transactionRepository.save(transaction);
        log.info("Saved transaction with merchantId " + transaction.getMerchant().getMerchantId() + " at " + transaction.getTimestamp());

        return new TransactionResponseDTO(transactionResponseEntity.getBody().getPaymentId(),
                transactionResponseEntity.getBody().getPaymentUrl(), true, transactionResponseEntity.getBody().getMessage());
    }

    @Override
    public void finishTransaction(PspResponseDTO dto) {
        log.info("Finishing transaction over bank");

        Transaction transaction = transactionRepository.findByOrderId(dto.getMerchantOrderId());
        if (transaction == null) {
            log.error("Transaction with id: " + dto.getMerchantOrderId() + " does not exists!");
            return;
        }

        if (dto.isSuccess()) {
            transaction.setStatus(TransactionStatus.DONE);
            log.info(String.format("Successfully executed transaction with PAYMENT_ID %s, ACQUIRER_TIMESTAMP %s, ACQUIRER_ORDER_ID %s, MERCHANT_ORDER_ID %s",
                    dto.getPaymentId(), dto.getAcquirerTimeStamp().toString(), dto.getAcquirerOrderId(), dto.getMerchantOrderId()));
        } else {
            log.error(String.format("Failed to execute transaction with with PAYMENT_ID %s, ACQUIRER_TIMESTAMP %s, ACQUIRER_ORDER_ID %s, MERCHANT_ORDER_ID %s",
                    dto.getPaymentId(), dto.getAcquirerTimeStamp().toString(), dto.getAcquirerOrderId(), dto.getMerchantOrderId()));
            transaction.setStatus(TransactionStatus.CANCELED);
        }

    }

    @Override
    public boolean checkIfMerchantExists(String merchantId) throws Exception {
        log.info("Checking if merchant exists");

        if (merchantId.equals("")) {
            throw new Exception("Merchant id is not present!");
        }

        return merchantRepository.findByMerchantId(merchantId) != null;

    }

    @Override
    public WageResponse paymentWage(WageTransactionRequest wageTransactionRequest) throws Exception {
        log.info("Creating new transaction");

        if (wageTransactionRequest.getMerchantId().trim().equals("") || wageTransactionRequest.getMerchantOrderId().trim().equals("")) {
            log.error("merchantId and/or merchantOrderId are null");
            throw new Exception("All ids for creating transaction must be present");
        }

        Merchant merchant = merchantRepository.findByMerchantId(wageTransactionRequest.getMerchantId());
        log.info("Getting merchant with id "+ wageTransactionRequest.getMerchantOrderId());
        if (merchant == null) {
            log.error("Merchant with id " + wageTransactionRequest.getMerchantOrderId() + " does not exists");
            throw new Exception("Merchant with id " + wageTransactionRequest.getMerchantOrderId() + " does not exists");
        }

        Transaction transaction = new Transaction();
        transaction.setBankTransactionId("");
        transaction.setMerchant(merchant);
        transaction.setAmount(wageTransactionRequest.getAmount());
        transaction.setCurrency(wageTransactionRequest.getCurrency());
        transaction.setStatus(TransactionStatus.IN_PROGRESS);
        transaction.setOrderId(wageTransactionRequest.getMerchantOrderId());
        transaction.setTimestamp(wageTransactionRequest.getTimestamp());
        transaction.setReturnUrl("");
        transactionRepository.save(transaction);
        log.info("Saved transaction with merchantId " + transaction.getMerchant().getMerchantId() + " at " + transaction.getTimestamp());

        WageTransactionRequest request = new WageTransactionRequest();
        request.setMerchantId(merchant.getBankMerchantId());
        request.setMerchantPassword(merchant.getBankMerchantPassword());
        request.setMerchantOrderId(wageTransactionRequest.getMerchantOrderId());
        request.setAmount(wageTransactionRequest.getAmount());
        request.setCurrency(wageTransactionRequest.getCurrency());
        request.setTimestamp(LocalDateTime.now());
        request.setAccountNumber(wageTransactionRequest.getAccountNumber());
        request.setBankNumber(wageTransactionRequest.getBankNumber());

        HttpEntity<WageTransactionRequest> requestEntity = new HttpEntity<>(request);
        ResponseEntity<WageResponse> response;

        try {
            log.info("Calling acquirer bank to create wage payment");
            response = restTemplate.postForEntity(getAcquirerUrl() + "/transactions/paymentWage", requestEntity, WageResponse.class);
        } catch (Exception e) {
            log.error("Error while calling acquirer bank");
            transaction.setStatus(TransactionStatus.CANCELED);
            transactionRepository.save(transaction);
            return new WageResponse(false, "Error while calling acquirer bank");
        }

        if (response.getBody() == null || !Objects.requireNonNull(response.getBody()).isSuccess()) {
            log.error("Error response from acquirer bank");
            transaction.setStatus(TransactionStatus.CANCELED);
            transactionRepository.save(transaction);
            return new WageResponse(false, Objects.requireNonNull(response.getBody()).getMessage());
        }

        transaction.setBankTransactionId(response.getBody().getBankTransactionId());
        transaction.setStatus(TransactionStatus.DONE);
        transactionRepository.save(transaction);

        return new WageResponse(true, response.getBody().getMessage());
    }

    private String getAcquirerUrl() {
        log.info("Getting acquirer url");
        return HTTPS_PREFIX + this.acquirerHost + ":" + this.acquirerPort + "/api";
    }
}
