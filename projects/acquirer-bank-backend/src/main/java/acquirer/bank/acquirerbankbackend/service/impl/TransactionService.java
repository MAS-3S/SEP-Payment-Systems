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

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    @Value("${acquirer.front.port}")
    private String acquirerBankFrontPort;
    @Value("${api.gateway.port}")
    private String apiGatewayPort;
    @Value("${api.gateway.host}")
    private String apiGatewayHost;

    private final TransactionRepository transactionRepository;
    private final CreditCardRepository creditCardRepository;


    @Autowired
    public TransactionService(TransactionRepository transactionRepository, CreditCardRepository creditCardRepository) {
        this.transactionRepository = transactionRepository;
        this.creditCardRepository = creditCardRepository;
    }

    @Override
    @Transactional
    public TransactionResponse checkTransactionAndReturnUrl(TransactionRequest transactionRequest) {
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
        transactionResponse.setPaymentUrl(HTTP_PREFIX + serverAddress + ":" + acquirerBankFrontPort + "/acquirer-bank/transaction/" + transaction.getId());
        transactionResponse.setSuccess(true);
        transactionResponse.setMessage("Transaction is successfully checked!");

        return  transactionResponse;
    }

    @Override
    @Transactional
    public TransactionResponse executeTransaction(String transactionId, CreditCardRequest creditCardRequest) throws URISyntaxException {
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
            sendRequestToPsp(transaction.getTimestamp(), transaction.getOrderId(), transaction.getId(), transaction.getId(), false);
            return transactionResponse;
        }

        if(creditCardRequest.getPan().startsWith(acquirerBankPan)) { // The same bank
            log.info("Trying to execute transaction in acquirer bank");
            CreditCard customerCreditCard = creditCardRepository.findByPanAndCcv(creditCardRequest.getPan(), creditCardRequest.getCcv());
            if(customerCreditCard == null || customerCreditCard.getExpirationDate().isBefore(LocalDate.now())) {
                log.error("Customer credit card not found or expired!");
                transactionResponse.setSuccess(false);
                transactionResponse.setMessage("Customer credit card not found or expired!");
                sendRequestToPsp(transaction.getTimestamp(), transaction.getOrderId(), transaction.getId(), transaction.getId(), false);
                return transactionResponse;
            }

            if (!customerCreditCard.getPan().equals(creditCardRequest.getPan()) || !customerCreditCard.getCcv().equals(creditCardRequest.getCcv()) ||
                    !customerCreditCard.getExpirationDate().equals(creditCardRequest.getExpirationDate())
                    || !(customerCreditCard.getClient().getFirstName() + " " + customerCreditCard.getClient().getLastName()).equals(creditCardRequest.getCardholderName())) {
                log.error("Inserted values of credit card are not matching the real one");
                transactionResponse.setPaymentUrl(transaction.getFailedUrl());
                transactionResponse.setSuccess(false);
                transactionResponse.setMessage("Inserted values of credit card are not matching the real one");
                return transactionResponse;
            }

            if(customerCreditCard.getAvailableAmount() - transaction.getAmount() < 0) {
                log.error("No enough available money on customer credit card");
                transaction.setStatus(TransactionStatus.FAILED);
                transactionRepository.save(transaction);

                transactionResponse.setPaymentUrl(transaction.getFailedUrl());
                transactionResponse.setSuccess(false);
                transactionResponse.setMessage("Payment failed! No enough available money on customer credit card");
                sendRequestToPsp(transaction.getTimestamp(), transaction.getOrderId(), transaction.getId(), transaction.getId(), false);
                return transactionResponse;
            }

            log.info("Paying with credit card's PAN: " + customerCreditCard.getPan().substring(0, 4) + " - **** - **** - " + customerCreditCard.getPan().substring(12));
            customerCreditCard.setAvailableAmount(customerCreditCard.getAvailableAmount() - transaction.getAmount());
            customerCreditCard.setReservedAmount(customerCreditCard.getReservedAmount() + transaction.getAmount());
            log.info("Amount " + transaction.getAmount() + " transfer from available to reserved amount");
        } else { // Different bank
            log.info("Payment is not from the same bank");
            log.info("Trying to execute transaction in issuer bank");
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
                sendRequestToPsp(transaction.getTimestamp(), transaction.getOrderId(), transaction.getId(), transaction.getId(), false);
                return transactionResponse;
            }

            log.info(String.format("Successfully executed transaction with ACQUIRER_TIMESTAMP %s, ACQUIRER_ORDER_ID %s, ISSUER_ORDER_ID %s, ISSUER_TIMESTAMP %s",
                    pccResponse.getAcquirerTimestamp().toString(), pccResponse.getAcquirerOrderId(), pccResponse.getIssuerOrderId(), pccResponse.getIssuerTimestamp().toString()));

        }

        transaction.setStatus(TransactionStatus.SUBMITTED);
        transaction.setCustomerPan(creditCardRequest.getPan());
        transactionRepository.save(transaction);
        log.info("Transaction " + transaction.getId() + " submitted!");

        transactionResponse.setPaymentUrl(transaction.getSuccessUrl());
        transactionResponse.setSuccess(true);
        transactionResponse.setMessage("Payment is successful!");
        sendRequestToPsp(transaction.getTimestamp(), transaction.getOrderId(), transaction.getId(), transaction.getId(), true);
        return transactionResponse;
    }

    private PccResponse sendRequestToPcc(PccRequest pccRequest) throws URISyntaxException {
        log.info("Sending request to PCC");

        RestTemplate restTemplate = new RestTemplate();
        final String url = HTTP_PREFIX + this.pccAddress + ":" + this.pccPort + "/api/pcc/forward";
        URI uri = new URI(url);

        ResponseEntity<PccResponse> result = restTemplate.postForEntity(uri, pccRequest, PccResponse.class);
        return result.getBody();
    }


    private void sendRequestToPsp(LocalDateTime acquirerTimeStamp, String merchantOrderId, String paymentId, String acquirerOrderId, boolean success) throws URISyntaxException {
        log.info("Sending request to PSP");

        PspResponse pspResponse = new PspResponse(acquirerOrderId, paymentId, merchantOrderId, acquirerTimeStamp, success);
        RestTemplate restTemplate = new RestTemplate();
        final String url = HTTP_PREFIX + this.apiGatewayHost + ":" + this.apiGatewayPort + "/bank-service/checkTransaction";
        URI uri = new URI(url);

        ResponseEntity<?> result = restTemplate.postForEntity(uri, pspResponse, PspResponse.class);

    }

}
