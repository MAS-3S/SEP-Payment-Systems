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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;


@Service
public class TransactionService implements ITransactionService {

    private static final Double RSDtoEUR = 0.0085;
    private static final Double USDtoEUR = 0.89;
    private static final Double YUANtoEUR = 0.14;

    protected final Log log = LogFactory.getLog(getClass());
    private static final String HTTP_PREFIX = "http://";
    private static final String HTTPS_PREFIX = "https://";

    @Autowired
    RestTemplate restTemplate;

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
    @Value("${acquirer.front.port}")
    private String acquirerBankFrontPort;
    @Value("${api.gateway.port}")
    private String apiGatewayPort;
    @Value("${api.gateway.host}")
    private String apiGatewayHost;
    @Value("${encryption.key}")
    private String encryptionKey;
    @Value("${encryption.vector}")
    private String encryptionVector;

    private final TransactionRepository transactionRepository;
    private final CreditCardRepository creditCardRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, CreditCardRepository creditCardRepository) {
        this.transactionRepository = transactionRepository;
        this.creditCardRepository = creditCardRepository;
    }

    @Override
    @Transactional
    public TransactionResponse checkTransactionAndReturnUrl(TransactionRequest transactionRequest, String type) {
        log.info("Checking transaction has started.");
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
        transaction.setCurrency(transactionRequest.getCurrency());
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
        if(type.equals("creditCard")) {
            transactionResponse.setPaymentUrl(HTTPS_PREFIX + serverAddress + ":" + acquirerBankFrontPort + "/acquirer-bank/transaction/" + transaction.getId());
        } else if(type.equals("qrCode")) {
            transactionResponse.setPaymentUrl(HTTPS_PREFIX + serverAddress + ":" + acquirerBankFrontPort + "/acquirer-bank/qr-code/" + transaction.getId());
        }
        transactionResponse.setSuccess(true);
        transactionResponse.setMessage("Transaction is successfully checked!");

        return transactionResponse;
    }

    @Override
    @Transactional
    public TransactionResponse executeTransaction(String transactionId, CreditCardRequest creditCardRequest, String type) throws URISyntaxException {
        log.info("Executing transaction with id:" + transactionId + " started.");
        TransactionResponse transactionResponse = new TransactionResponse();

        if(creditCardRequest.getPan().equals("") || creditCardRequest.getCcv().equals("") || creditCardRequest.getExpirationDate() == null || creditCardRequest.getCardholderName().equals("")) {
            log.error("Some field was empty!");
            transactionResponse.setSuccess(false);
            transactionResponse.setMessage("Some field was empty!");
            return transactionResponse;
        }

        Transaction transaction = transactionRepository.getById(transactionId);
        if(!transaction.getStatus().equals(TransactionStatus.OPEN)) {
            log.info("Transaction with id: " + transactionId + " has already executed!");
            transactionResponse.setSuccess(false);
            transactionResponse.setMessage("Transaction has already executed!");
            sendRequestToPsp(transaction.getTimestamp(), transaction.getOrderId(), transaction.getId(), transaction.getId(), false, type);
            return transactionResponse;
        }

        if(creditCardRequest.getPan().startsWith(acquirerBankPan)) { // The same bank
            log.info("Trying to execute transaction in acquirer bank");
            String encryptedCreditCardPan = this.encryptPan(creditCardRequest.getPan());
            CreditCard customerCreditCard = creditCardRepository.findByPanAndCcv(encryptedCreditCardPan, creditCardRequest.getCcv());
            if(customerCreditCard == null || customerCreditCard.getExpirationDate().isBefore(LocalDate.now())) {
                log.error("Customer credit card not found or expired!");
                transactionResponse.setPaymentUrl(transaction.getFailedUrl());
                transactionResponse.setSuccess(false);
                transactionResponse.setMessage("Customer credit card not found or expired!");
                sendRequestToPsp(transaction.getTimestamp(), transaction.getOrderId(), transaction.getId(), "", false, type);
                return transactionResponse;
            }

            if (!customerCreditCard.getPan().equals(encryptedCreditCardPan) || !customerCreditCard.getCcv().equals(creditCardRequest.getCcv()) ||
                    !customerCreditCard.getExpirationDate().equals(creditCardRequest.getExpirationDate())
                    || !(customerCreditCard.getClient().getFirstName() + " " + customerCreditCard.getClient().getLastName()).equals(creditCardRequest.getCardholderName())) {
                log.error("Inserted values of credit card are not matching the real one");
                transactionResponse.setPaymentUrl(transaction.getFailedUrl());
                transactionResponse.setSuccess(false);
                transactionResponse.setMessage("Inserted values of credit card are not matching the real one");
                return transactionResponse;
            }

            if (customerCreditCard.getAvailableAmount() - convertTransactionAmountToEUR(transaction.getAmount(), transaction.getCurrency()) < 0) {
                log.error("No enough available money on customer credit card");
                transaction.setStatus(TransactionStatus.FAILED);
                transactionRepository.save(transaction);

                transactionResponse.setPaymentUrl(transaction.getFailedUrl());
                transactionResponse.setSuccess(false);
                transactionResponse.setMessage("Payment failed! No enough available money on customer credit card");
                sendRequestToPsp(transaction.getTimestamp(), transaction.getOrderId(), transaction.getId(), "", false, type);
                return transactionResponse;
            }

            String decryptedCustomerCreditCardPan = this.decryptPan(customerCreditCard.getPan());
            log.info("Paying with credit card's PAN: " + decryptedCustomerCreditCardPan.substring(0, 4) + " - **** - **** - " + decryptedCustomerCreditCardPan.substring(12));
            customerCreditCard.setAvailableAmount(customerCreditCard.getAvailableAmount() - convertTransactionAmountToEUR(transaction.getAmount(), transaction.getCurrency()));
            customerCreditCard.setReservedAmount(customerCreditCard.getReservedAmount() + convertTransactionAmountToEUR(transaction.getAmount(), transaction.getCurrency()));
            log.info("Amount " + transaction.getAmount() + " transfer from available to reserved amount");
            sendRequestToPsp(transaction.getTimestamp(), transaction.getOrderId(), transaction.getId(), transaction.getId(), true, type);
        } else { // Different bank
            log.info("Payment is not from the same bank");
            log.info("Trying to execute transaction in issuer bank");
            PccRequest pccRequest = new PccRequest();
            pccRequest.setAcquirerOrderId(transactionId);
            pccRequest.setAcquirerTimestamp(transaction.getTimestamp());
            pccRequest.setAmount(transaction.getAmount());
            pccRequest.setCurrency(transaction.getCurrency());
            pccRequest.setPan(this.encryptPan(creditCardRequest.getPan()));
            pccRequest.setCcv(creditCardRequest.getCcv());
            pccRequest.setExpirationDate(creditCardRequest.getExpirationDate());
            pccRequest.setCardholderName(creditCardRequest.getCardholderName());

            PccResponse pccResponse = new PccResponse();
            try {
                pccResponse = sendRequestToPcc(pccRequest);
            } catch (Exception e) {
                log.error("Pcc redirection error!");
            }

            if(!pccResponse.isSuccess()) {

                if (pccResponse.getAcquirerOrderId() != null && pccResponse.getAcquirerTimestamp() != null && pccResponse.getIssuerTimestamp() != null && pccResponse.getIssuerOrderId() != null) {
                    log.info(String.format("Failed to executed transaction with ACQUIRER_TIMESTAMP %s, ACQUIRER_ORDER_ID %s, ISSUER_ORDER_ID %s, ISSUER_TIMESTAMP %s",
                            pccResponse.getAcquirerTimestamp().toString(), pccResponse.getAcquirerOrderId(), pccResponse.getIssuerOrderId(), pccResponse.getIssuerTimestamp().toString()));
                }

                boolean failed = pccResponse.getAcquirerOrderId() != null;
                if (failed) {
                    transaction.setStatus(TransactionStatus.FAILED);
                    transactionRepository.save(transaction);
                    log.info("Transaction failed!");
                }

                transactionResponse.setPaymentUrl(failed ? transaction.getFailedUrl() : null);
                transactionResponse.setSuccess(false);
                transactionResponse.setMessage(pccResponse.getMessage());
                sendRequestToPsp(pccResponse.getAcquirerTimestamp(), transaction.getOrderId(), transaction.getId(), pccResponse.getAcquirerOrderId(), false, type);
                return transactionResponse;
            }

            log.info(String.format("Successfully executed transaction with ACQUIRER_TIMESTAMP %s, ACQUIRER_ORDER_ID %s, ISSUER_ORDER_ID %s, ISSUER_TIMESTAMP %s",
                    pccResponse.getAcquirerTimestamp().toString(), pccResponse.getAcquirerOrderId(), pccResponse.getIssuerOrderId(), pccResponse.getIssuerTimestamp().toString()));

            sendRequestToPsp(pccResponse.getAcquirerTimestamp(), transaction.getOrderId(), transaction.getId(), pccResponse.getAcquirerOrderId(), true, type);
        }

        transaction.setStatus(TransactionStatus.SUBMITTED);
        transaction.setCustomerPan(this.encryptPan(creditCardRequest.getPan()));
        transactionRepository.save(transaction);
        log.info("Transaction " + transaction.getId() + " submitted!");

        transactionResponse.setPaymentUrl(transaction.getSuccessUrl());
        transactionResponse.setSuccess(true);
        transactionResponse.setMessage("Payment is successful!");

        return transactionResponse;
    }

    @Override
    public WageResponse paymentWage(WageTransactionRequest wageTransactionRequest) {
        log.info("Wage transaction has started.");
        WageResponse wageResponse = new WageResponse();

        CreditCard merchantCreditCard = creditCardRepository.findByMerchantIdAndMerchantPassword(wageTransactionRequest.getMerchantId(), wageTransactionRequest.getMerchantPassword());
        if(merchantCreditCard == null) {
            log.error("Credit card with merchant id: " + wageTransactionRequest.getMerchantId() + " does not exist!");
            wageResponse.setSuccess(false);
            wageResponse.setMessage("Credit card with merchant id: " + wageTransactionRequest.getMerchantId() + " does not exist!");
            return wageResponse;
        }

        log.info("Merchant card is successfully found!");
        Transaction transaction = new Transaction();
        transaction.setAmount(wageTransactionRequest.getAmount());
        transaction.setCurrency(wageTransactionRequest.getCurrency());
        transaction.setTimestamp(wageTransactionRequest.getTimestamp());
        transaction.setOrderId(wageTransactionRequest.getMerchantOrderId());
        transaction.setMerchantPan(merchantCreditCard.getPan());
        transaction.setStatus(TransactionStatus.OPEN);
        transaction.setSuccessUrl("");
        transaction.setFailedUrl("");
        transaction.setErrorUrl("");
        transactionRepository.save(transaction);
        log.info("Transaction is successfully saved!");

        wageResponse.setBankTransactionId(transaction.getId());
        if(wageTransactionRequest.getBankNumber().equals(acquirerBankPan)) { // The same bank
            log.info("Trying to execute transaction in acquirer bank");
            CreditCard customerCreditCard = creditCardRepository.findByAccountNumber(wageTransactionRequest.getAccountNumber());
            if(customerCreditCard == null || customerCreditCard.getExpirationDate().isBefore(LocalDate.now())) {
                log.error("Customer credit card not found or expired!");
                wageResponse.setSuccess(false);
                wageResponse.setMessage("Customer credit card not found or expired!");
                return wageResponse;
            }

            if (merchantCreditCard.getAvailableAmount() - convertTransactionAmountToEUR(transaction.getAmount(), transaction.getCurrency()) < 0) {
                log.error("No enough available money on merchant credit card");
                transaction.setStatus(TransactionStatus.FAILED);
                transactionRepository.save(transaction);
                wageResponse.setSuccess(false);
                wageResponse.setMessage("Payment failed! No enough available money on customer credit card");
                return wageResponse;
            }

            String decryptedMerchantCreditCardPan = this.decryptPan(merchantCreditCard.getPan());
            log.info("Paying with credit card's PAN: " + decryptedMerchantCreditCardPan.substring(0, 4) + " - **** - **** - " + decryptedMerchantCreditCardPan.substring(12));
            merchantCreditCard.setAvailableAmount(merchantCreditCard.getAvailableAmount() - convertTransactionAmountToEUR(transaction.getAmount(), transaction.getCurrency()));
            creditCardRepository.save(merchantCreditCard);

            String decryptedCustomerCreditCardPan = this.decryptPan(customerCreditCard.getPan());
            log.info("Wage payed - credit card's PAN: " + decryptedCustomerCreditCardPan.substring(0, 4) + " - **** - **** - " + decryptedMerchantCreditCardPan.substring(12));
            customerCreditCard.setAvailableAmount(customerCreditCard.getAvailableAmount() + convertTransactionAmountToEUR(transaction.getAmount(), transaction.getCurrency()));
            creditCardRepository.save(customerCreditCard);
            transaction.setCustomerPan(customerCreditCard.getPan());
        } else { // Different bank
//            log.info("Payment is not from the same bank");
//            log.info("Trying to execute transaction in issuer bank");
//            PccRequest pccRequest = new PccRequest();
//            pccRequest.setAcquirerOrderId(transactionId);
//            pccRequest.setAcquirerTimestamp(transaction.getTimestamp());
//            pccRequest.setAmount(transaction.getAmount());
//            pccRequest.setCurrency(transaction.getCurrency());
//            pccRequest.setPan(this.encryptPan(creditCardRequest.getPan()));
//            pccRequest.setCcv(creditCardRequest.getCcv());
//            pccRequest.setExpirationDate(creditCardRequest.getExpirationDate());
//            pccRequest.setCardholderName(creditCardRequest.getCardholderName());
//
//            PccResponse pccResponse = new PccResponse();
//            try {
//                pccResponse = sendRequestToPcc(pccRequest);
//            } catch (Exception e) {
//                log.error("Pcc redirection error!");
//            }
//
//            if(!pccResponse.isSuccess()) {
//
//                if (pccResponse.getAcquirerOrderId() != null && pccResponse.getAcquirerTimestamp() != null && pccResponse.getIssuerTimestamp() != null && pccResponse.getIssuerOrderId() != null) {
//                    log.info(String.format("Failed to executed transaction with ACQUIRER_TIMESTAMP %s, ACQUIRER_ORDER_ID %s, ISSUER_ORDER_ID %s, ISSUER_TIMESTAMP %s",
//                            pccResponse.getAcquirerTimestamp().toString(), pccResponse.getAcquirerOrderId(), pccResponse.getIssuerOrderId(), pccResponse.getIssuerTimestamp().toString()));
//                }
//
//                boolean failed = pccResponse.getAcquirerOrderId() != null;
//                if (failed) {
//                    transaction.setStatus(TransactionStatus.FAILED);
//                    transactionRepository.save(transaction);
//                    log.info("Transaction failed!");
//                }
//
//                transactionResponse.setPaymentUrl(failed ? transaction.getFailedUrl() : null);
//                transactionResponse.setSuccess(false);
//                transactionResponse.setMessage(pccResponse.getMessage());
//                sendRequestToPsp(pccResponse.getAcquirerTimestamp(), transaction.getOrderId(), transaction.getId(), pccResponse.getAcquirerOrderId(), false, type);
//                return transactionResponse;
//            }
//
//            log.info(String.format("Successfully executed transaction with ACQUIRER_TIMESTAMP %s, ACQUIRER_ORDER_ID %s, ISSUER_ORDER_ID %s, ISSUER_TIMESTAMP %s",
//                    pccResponse.getAcquirerTimestamp().toString(), pccResponse.getAcquirerOrderId(), pccResponse.getIssuerOrderId(), pccResponse.getIssuerTimestamp().toString()));
//
//            sendRequestToPsp(pccResponse.getAcquirerTimestamp(), transaction.getOrderId(), transaction.getId(), pccResponse.getAcquirerOrderId(), true, type);
        }

        transaction.setStatus(TransactionStatus.SUBMITTED);
        transactionRepository.save(transaction);
        log.info("Transaction " + transaction.getId() + " submitted!");

        wageResponse.setSuccess(true);
        wageResponse.setMessage("Payment is successful!");
        return wageResponse;
    }

    private PccResponse sendRequestToPcc(PccRequest pccRequest) throws URISyntaxException {
        log.info("Sending request to PCC");

        //RestTemplate restTemplate = new RestTemplate();
        final String url = HTTPS_PREFIX + this.pccAddress + ":" + this.pccPort + "/api/pcc/forward";
        URI uri = new URI(url);

        ResponseEntity<PccResponse> result = restTemplate.postForEntity(uri, pccRequest, PccResponse.class);
        return result.getBody();
    }


    private void sendRequestToPsp(LocalDateTime acquirerTimeStamp, String merchantOrderId, String paymentId, String acquirerOrderId, boolean success, String type) throws URISyntaxException {
        log.info("Sending request to PSP");

        PspResponse pspResponse = new PspResponse(acquirerOrderId, paymentId, merchantOrderId, acquirerTimeStamp, success);
        //RestTemplate restTemplate = new RestTemplate();
        final String url = type.equals("creditCard") ? HTTPS_PREFIX + this.apiGatewayHost + ":" + this.apiGatewayPort + "/bank-service/checkTransaction" :
                HTTPS_PREFIX + this.apiGatewayHost + ":" + this.apiGatewayPort + "/qr-service/checkTransaction";
        URI uri = new URI(url);

        ResponseEntity<?> result = restTemplate.postForEntity(uri, pspResponse, PspResponse.class);
    }

    @Override
    public Transaction findById(String id) {
        return transactionRepository.findById(id).orElse(null);
    }

    private Double convertTransactionAmountToEUR(Double amount, String transactionCurrency) {
        switch (transactionCurrency) {
            case "USD":
                return amount * USDtoEUR;
            case "RSD":
                return amount * RSDtoEUR;
            case "YUAN":
                return amount * YUANtoEUR;
            default:
                return amount; //EUR original
        }
    }

    public CreditCard findCreditCardByMerchantPan(String pan) {
        return creditCardRepository.findByPan(pan);
    }

    private String encryptPan(String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(this.encryptionVector.getBytes("UTF-8"));
            SecretKeySpec keySpec = new SecretKeySpec(this.encryptionKey.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.getEncoder()
                    .encodeToString(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String decryptPan(String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(this.encryptionVector.getBytes("UTF-8"));
            SecretKeySpec keySpec = new SecretKeySpec(this.encryptionKey.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

}
