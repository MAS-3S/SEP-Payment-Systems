package com.example.bitcoinservice.service.impl;

import com.example.bitcoinservice.dto.*;
import com.example.bitcoinservice.model.Merchant;
import com.example.bitcoinservice.model.Transaction;
import com.example.bitcoinservice.model.TransactionStatus;
import com.example.bitcoinservice.repository.MerchantRepository;
import com.example.bitcoinservice.repository.TransactionRepository;
import com.example.bitcoinservice.service.ITransactionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpHeaders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class TransactionService implements ITransactionService {

    protected final Log log = LogFactory.getLog(getClass());

    @Autowired
    RestTemplate restTemplate;
    private final TransactionRepository transactionRepository;
    private final MerchantRepository merchantRepository;

    public TransactionService(TransactionRepository transactionRepository, MerchantRepository merchantRepository) {
        this.transactionRepository = transactionRepository;
        this.merchantRepository = merchantRepository;
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
        log.info("Getting merchant with id " + createTransactionDTO.getMerchantOrderId());
        if (merchant == null) {
            log.error("Merchant with id " + createTransactionDTO.getMerchantOrderId() + " does not exists");
            throw new Exception("Merchant with id " + createTransactionDTO.getMerchantOrderId() + " does not exists");
        }

        CoinGateTransactionRequestDTO coinGateTransactionRequestDTO = new CoinGateTransactionRequestDTO();
        coinGateTransactionRequestDTO.setPrice_amount(createTransactionDTO.getAmount());
        coinGateTransactionRequestDTO.setPrice_currency(createTransactionDTO.getCurrency());
        coinGateTransactionRequestDTO.setReceive_currency("BTC");
        coinGateTransactionRequestDTO.setTitle("Order #" + createTransactionDTO.getMerchantOrderId());
        coinGateTransactionRequestDTO.setDescription("Ordering items under id " + createTransactionDTO.getMerchantOrderId() + " and paying with BitCoin");
        coinGateTransactionRequestDTO.setSuccess_url(createTransactionDTO.getSuccessUrlWithOrderId());
        coinGateTransactionRequestDTO.setCancel_url(createTransactionDTO.getFailedUrlWithOrderId());
        coinGateTransactionRequestDTO.setToken(merchant.getTokenForCoinGate());
        log.info("Created coin gate request for order: " + createTransactionDTO.getMerchantOrderId());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Token " + merchant.getTokenForCoinGate());
        HttpEntity<CoinGateTransactionRequestDTO> requestDTOHttpEntity = new HttpEntity<>(coinGateTransactionRequestDTO, httpHeaders);

        log.info("Trying to execute payment over coin gate for merchant: " + createTransactionDTO.getMerchantId());
        ResponseEntity<CoinGateTransactionResponseDTO> transactionResponseEntity;

        try {
            transactionResponseEntity = this.restTemplate.postForEntity("https://api-sandbox.coingate.com/v2/orders", requestDTOHttpEntity, CoinGateTransactionResponseDTO.class);
        } catch (Exception e) {
            log.error("Error while calling coin gate payment api");
            return new TransactionResponseDTO("", createTransactionDTO.getErrorUrlWithOrderId(), false, "Error while calling coin gate payment api");
        }

        log.info("Approved order for merchant id: " + createTransactionDTO.getMerchantId() + " and order: " + createTransactionDTO.getMerchantOrderId());
        CoinGateTransactionResponseDTO coinGateTransactionResponseDTO = transactionResponseEntity.getBody();

        if (coinGateTransactionResponseDTO == null || transactionResponseEntity.getBody() == null) {
            log.error("Error response from coin gate");
            return new TransactionResponseDTO("", createTransactionDTO.getFailedUrlWithOrderId(), false, "Error while response is null or not defined");
        }

        Transaction transaction = new Transaction();
        transaction.setAmount(Double.parseDouble(coinGateTransactionResponseDTO.getPrice_amount()));
        transaction.setCurrency(coinGateTransactionResponseDTO.getPrice_currency());
        transaction.setCoinGateId(coinGateTransactionResponseDTO.getId());
        transaction.setMerchant(merchant);
        transaction.setOrderId(createTransactionDTO.getMerchantOrderId());
        transaction.setStatus(TransactionStatus.IN_PROGRESS);
        transaction.setTimestamp(coinGateTransactionResponseDTO.getCreated_at());
        transaction.setReturnUrl(coinGateTransactionResponseDTO.getPayment_url());
        transactionRepository.save(transaction);
        log.info("Saved transaction with merchantId " + transaction.getMerchant().getMerchantId() + " at " + transaction.getTimestamp());

        log.info("Starting to checkout coin gate transaction: " + transaction.getCoinGateId());
        CoinGateCheckOutDTO coinGateCheckOutDTO = new CoinGateCheckOutDTO();
        coinGateCheckOutDTO.setPay_currency("BTC");

        HttpEntity<CoinGateCheckOutDTO> coinGateCheckOutHttpEntity = new HttpEntity<>(coinGateCheckOutDTO, httpHeaders);
        ResponseEntity<CoinGateTransactionResponseDTO> checkOutResponseDTO;

        try {
            checkOutResponseDTO = this.restTemplate.postForEntity("https://api-sandbox.coingate.com/v2/orders/" + coinGateTransactionResponseDTO.getId() + "/checkout", coinGateCheckOutHttpEntity, CoinGateTransactionResponseDTO.class);
        } catch (Exception e) {
            log.error("Error while check outing coin gate payment api");
            return new TransactionResponseDTO("", createTransactionDTO.getErrorUrlWithOrderId(), false, "Error while check outing coin gate payment api");
        }


        log.info("Finished checkout for merchant id: " + createTransactionDTO.getMerchantId() + " and order: " + createTransactionDTO.getMerchantOrderId());
        CoinGateTransactionResponseDTO checkoutCoinGateResponse = checkOutResponseDTO.getBody();

        if (checkOutResponseDTO.getBody() == null || checkoutCoinGateResponse == null) {
            log.error("Error response from coin gate");
            return new TransactionResponseDTO("", createTransactionDTO.getFailedUrlWithOrderId(), false, "Error while response is null or not defined");
        }


        return new TransactionResponseDTO(checkoutCoinGateResponse.getOrder_id(),
                checkoutCoinGateResponse.getPayment_url(), true, "Successfully paid with bit coin over coin gate");

    }

    @Override
    public boolean checkIfMerchantExists(String merchantId) throws Exception {
        log.info("Checking if merchant exists");

        if (merchantId.equals("")) {
            throw new Exception("Merchant id is not present!");
        }

        return merchantRepository.findByMerchantId(merchantId) != null;

    }
}
