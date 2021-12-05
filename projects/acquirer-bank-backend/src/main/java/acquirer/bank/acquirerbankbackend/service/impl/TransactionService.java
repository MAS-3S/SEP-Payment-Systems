package acquirer.bank.acquirerbankbackend.service.impl;

import acquirer.bank.acquirerbankbackend.dto.*;
import acquirer.bank.acquirerbankbackend.model.CreditCard;
import acquirer.bank.acquirerbankbackend.model.Transaction;
import acquirer.bank.acquirerbankbackend.model.enums.TransactionStatus;
import acquirer.bank.acquirerbankbackend.repository.CreditCardRepository;
import acquirer.bank.acquirerbankbackend.repository.TransactionRepository;
import acquirer.bank.acquirerbankbackend.service.ITransactionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;

@Service
public class TransactionService implements ITransactionService {

    protected final Log log = LogFactory.getLog(getClass());

    private static final String HTTP_PREFIX = "http://";
    @Value("${server.ipaddress}")
    private String serverAddress;
    @Value("${server.port}")
    private String serverPort;
    @Value("${pcc.ipaddress}")
    private String pccAddress;
    @Value("${pcc.port}")
    private String pccPort;
    @Value("${acquirer.bank.pan}")
    private String acquirerBankPan;

    private final TransactionRepository transactionRepository;
    private final CreditCardRepository creditCardRepository;


    @Autowired
    public TransactionService(TransactionRepository transactionRepository, CreditCardRepository creditCardRepository) {
        this.transactionRepository = transactionRepository;
        this.creditCardRepository = creditCardRepository;
    }

    @Override
    public TransactionResponse checkTransactionAndReturnUrl(TransactionRequest transactionRequest) {
        log.info("Check transaction started.");
        TransactionResponse transactionResponse = new TransactionResponse();

        if(transactionRequest.getMerchantId().equals("") || transactionRequest.getMerchantPassword().equals("") || transactionRequest.getMerchantOrderId().equals("") || transactionRequest.getMerchantTimestamp() == null || transactionRequest.getAmount() == null ||
            transactionRequest.getSuccessUrl().equals("") || transactionRequest.getFailedUrl().equals("") || transactionRequest.getErrorUrl().equals("")) {
            log.error("Some field was empty!");
            transactionResponse.setSuccess(false);
            transactionResponse.setMessage("Some field was empty!");
            return transactionResponse;
        }

        CreditCard creditCard = creditCardRepository.findByMerchantIdAndMerchantPassword(transactionRequest.getMerchantId(), transactionRequest.getMerchantPassword());
        if(creditCard == null) {
            log.error("Credit card with merchant id: " + transactionRequest.getMerchantId() + " does not exist!");
            transactionResponse.setSuccess(false);
            transactionResponse.setMessage("Credit card with merchant id: " + transactionRequest.getMerchantId() + " does not exist!");
            return transactionResponse;
        }

        log.info("Card is successfully found!");
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setTimestamp(transactionRequest.getMerchantTimestamp());
        transaction.setOrderId(transactionRequest.getMerchantOrderId());
        transaction.setMerchantPan(creditCard.getPan());
        transaction.setSuccessUrl(transactionRequest.getSuccessUrl());
        transaction.setFailedUrl(transactionRequest.getFailedUrl());
        transaction.setErrorUrl(transactionRequest.getErrorUrl());
        transaction.setStatus(TransactionStatus.OPEN);

        transactionRepository.save(transaction);
        log.info("Transaction is successfully saved!");

        transactionResponse.setPaymentId(transaction.getId());
        transactionResponse.setPaymentUrl(HTTP_PREFIX + serverAddress + ":" + serverPort + "/transaction/" + transaction.getId());
        transactionResponse.setSuccess(true);
        transactionResponse.setMessage("Transaction is successfully checked!");

        return  transactionResponse;
    }

    @Override
    public TransactionResponse executeTransaction(String transactionId, CreditCardRequest creditCardRequest) {
        log.info("Execute transaction started.");
        TransactionResponse transactionResponse = new TransactionResponse();

        if(creditCardRequest.getPan().equals("") || creditCardRequest.getCcv().equals("") || creditCardRequest.getExpirationDate() == null || creditCardRequest.getCardholderName().equals("")) {
            log.error("Some field was empty!");
            transactionResponse.setSuccess(false);
            transactionResponse.setMessage("Some field was empty!");
            return transactionResponse;
        }

        Transaction transaction = transactionRepository.getById(transactionId);
        if(transaction.getStatus().equals(TransactionStatus.OPEN)) {
            log.info("Transaction has already executed!");
            transactionResponse.setSuccess(false);
            transactionResponse.setMessage("Transaction has already executed!");
            return transactionResponse;
        }

        if(creditCardRequest.getPan().startsWith(acquirerBankPan)) { // The same bank
            CreditCard customerCreditCard = creditCardRepository.findByPanAndCcv(creditCardRequest.getPan(), creditCardRequest.getCcv());
            if(customerCreditCard == null || customerCreditCard.getExpirationDate().isBefore(LocalDate.now())) {
                log.error("Customer credit card not found or expired!");
                transactionResponse.setSuccess(false);
                transactionResponse.setMessage("Customer credit card not found or expired!");
                return transactionResponse;
            }

            if(customerCreditCard.getAvailableAmount() - transaction.getAmount() < 0) {
                log.error("No enough available money on customer credit card");
                transaction.setStatus(TransactionStatus.FAILED);
                transactionRepository.save(transaction);

                transactionResponse.setPaymentUrl(transaction.getFailedUrl());
                transactionResponse.setSuccess(false);
                transactionResponse.setMessage("Payment failed! No enough available money on customer credit card");
                return transactionResponse;
            }

            customerCreditCard.setAvailableAmount(customerCreditCard.getAvailableAmount() - transaction.getAmount());
            customerCreditCard.setReservedAmount(customerCreditCard.getReservedAmount() + transaction.getAmount());
            log.info("Amount " + transaction.getAmount() + " transfer from available to reserved amount");
        } else { // Different bank
            log.info("Payment is not from the same bank");
            PccRequest pccRequest = new PccRequest();
            pccRequest.setAcquirerOrderId(transactionId);
            pccRequest.setAcquirerTimestamp(transaction.getTimestamp());
            pccRequest.setAmount(transaction.getAmount());
            pccRequest.setPan(creditCardRequest.getPan());
            pccRequest.setCcv(creditCardRequest.getCcv());
            pccRequest.setExpirationDate(creditCardRequest.getExpirationDate());
            pccRequest.setCardholderName(creditCardRequest.getCardholderName());

            PccResponse pccResponse = new PccResponse();
            try {
                sendRequestToPcc(pccRequest);
            } catch (Exception e) {
                log.error("Pcc redirection error!");
            }

            if(!pccResponse.isSuccess()) {
                boolean failed = pccResponse.getAcquirerOrderId() != null;
                if (failed) {
                    transaction.setStatus(TransactionStatus.FAILED);
                    transactionRepository.save(transaction);
                    log.info("Transaction failed!");
                }

                transactionResponse.setPaymentUrl(failed ? transaction.getFailedUrl() : null);
                transactionResponse.setSuccess(false);
                transactionResponse.setMessage(pccResponse.getMessage());
                return transactionResponse;
            }

        }

        transaction.setStatus(TransactionStatus.SUBMITTED);
        transaction.setCustomerPan(creditCardRequest.getPan());
        transactionRepository.save(transaction);
        log.info("Transaction " + transaction.getId() + " submitted!");

        transactionResponse.setPaymentUrl(transaction.getSuccessUrl());
        transactionResponse.setSuccess(true);
        transactionResponse.setMessage("Payment is successful!");
        return transactionResponse;
    }

    private PccResponse sendRequestToPcc(PccRequest pccRequest) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        final String url = HTTP_PREFIX + this.pccAddress + ":" + this.pccPort + "/api/pcc/forward";
        URI uri = new URI(url);

        ResponseEntity<PccResponse> result = restTemplate.postForEntity(uri, pccRequest, PccResponse.class);
        return result.getBody();
    }

}
