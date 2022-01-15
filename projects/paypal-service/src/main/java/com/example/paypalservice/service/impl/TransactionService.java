package com.example.paypalservice.service.impl;

import com.example.paypalservice.dto.CreateTransactionDTO;
import com.example.paypalservice.dto.PayPalTransactionDTO;
import com.example.paypalservice.dto.TransactionResponseDTO;
import com.example.paypalservice.model.Merchant;
import com.example.paypalservice.model.Transaction;
import com.example.paypalservice.model.TransactionStatus;
import com.example.paypalservice.repository.MerchantRepository;
import com.example.paypalservice.repository.TransactionRepository;
import com.example.paypalservice.service.ITransactionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService implements ITransactionService {

    protected final Log log = LogFactory.getLog(getClass());
    private static final String HTTP_PREFIX = "http://";
    private static final String HTTPS_PREFIX = "https://";

    @Value("${pspfront.port}")
    private String pspFrontPort;
    @Value("${pspfront.host}")
    private String pspFrontHost;

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

        Transaction transaction = new Transaction();
        transaction.setAmount(createTransactionDTO.getAmount());
        transaction.setCurrency(createTransactionDTO.getCurrency());
        transaction.setMerchant(merchant);
        transaction.setOrderId(createTransactionDTO.getMerchantOrderId());
        transaction.setPayPalOrderId(null);
        transaction.setTimestamp(createTransactionDTO.getTime().toString());
        transaction.setReturnUrl("");
        transaction.setStatus(TransactionStatus.IN_PROGRESS);
        transaction.setSuccessUrl(createTransactionDTO.getSuccessUrlWithOrderId());
        transaction.setCancelUrl(createTransactionDTO.getFailedUrlWithOrderId());
        transactionRepository.save(transaction);
        log.info("Saved transaction with merchantId " + transaction.getMerchant().getMerchantId() + " at " + transaction.getTimestamp());

        transaction.setReturnUrl(this.getPspPaymentReturnUrl(transaction.getId()));
        transactionRepository.save(transaction);

        return new TransactionResponseDTO(transaction.getId(),
                transaction.getReturnUrl(), true, "Successfully checkout to paypal payment");
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
    public PayPalTransactionDTO getTransactionForPayPalBy(String transactionId) throws Exception {
        log.info("Getting merchant for executing PayPal transaction");

        if (transactionId.trim().equals("")) {
            log.error("TransactionId: " + transactionId + " is not present");
            throw new Exception("TransactionId: " + transactionId + " is not present");
        }

        Transaction transaction = transactionRepository.getById(transactionId);
        Merchant merchant = merchantRepository.findById(transaction.getMerchant().getId()).orElse(null);

        if (merchant == null) {
            log.error("Merchant or/and transaction is null");
            throw new Exception("Merchant or/and transaction is null");
        }

        return new PayPalTransactionDTO(transactionId, merchant.getClientId(), transaction.getCurrency(), transaction.getAmount(), transaction.getSuccessUrl(), transaction.getCancelUrl());
    }

    @Override
    public void changeTransactionStatusToSuccess(String transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId).orElse(null);
        if(transaction == null) {
            log.error("Transaction with id:" + transactionId + " does not exist");
            return;
        }
        transaction.setStatus(TransactionStatus.SUCCESS);
        transactionRepository.save(transaction);
        log.info("Transaction with id:" + transactionId + " is successfully executed");
    }

    @Override
    public void changeTransactionStatusToCanceled(String transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId).orElse(null);
        if(transaction == null) {
            log.error("Transaction with id:" + transactionId + " does not exist");
            return;
        }
        transaction.setStatus(TransactionStatus.CANCELED);
        transactionRepository.save(transaction);
        log.info("Transaction with id:" + transactionId + " is canceled");
    }

    public String getPspPaymentReturnUrl(String transactionId) {
        return HTTPS_PREFIX + this.pspFrontHost + ":" + this.pspFrontPort + "/payment-method/pay-pal/" + transactionId;
    }

}
