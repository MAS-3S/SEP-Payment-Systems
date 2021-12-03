package acquirer.bank.acquirerbankbackend.service.impl;

import acquirer.bank.acquirerbankbackend.dto.TransactionRequest;
import acquirer.bank.acquirerbankbackend.dto.TransactionResponse;
import acquirer.bank.acquirerbankbackend.model.CreditCard;
import acquirer.bank.acquirerbankbackend.model.Transaction;
import acquirer.bank.acquirerbankbackend.model.enums.TransactionStatus;
import acquirer.bank.acquirerbankbackend.repository.CreditCardRepository;
import acquirer.bank.acquirerbankbackend.repository.TransactionRepository;
import acquirer.bank.acquirerbankbackend.service.ITransactionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService implements ITransactionService {

    protected final Log log = LogFactory.getLog(getClass());

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
            transactionRequest.setDescription("Some field was empty!");
            return transactionResponse;
        }

        CreditCard creditCard = creditCardRepository.findByMerchantIdAndMerchantPassword(transactionRequest.getMerchantId(), transactionRequest.getMerchantPassword());
        if(creditCard == null) {
            log.error("Credit card with merchant id: " + transactionRequest.getMerchantId() + " does not exist!");
            transactionResponse.setSuccess(false);
            transactionRequest.setDescription("Credit card with merchant id: " + transactionRequest.getMerchantId() + " does not exist!");
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
        transactionResponse.setPaymentUrl(""); // Setovati URL!!!
        transactionResponse.setSuccess(true);
        transactionResponse.setMessage("Transaction is successfully checked!");

        return  transactionResponse;
    }
}
