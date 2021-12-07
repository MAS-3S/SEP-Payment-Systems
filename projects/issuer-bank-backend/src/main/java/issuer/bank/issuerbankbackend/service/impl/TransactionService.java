package issuer.bank.issuerbankbackend.service.impl;

import issuer.bank.issuerbankbackend.dto.PccRequest;
import issuer.bank.issuerbankbackend.dto.PccResponse;
import issuer.bank.issuerbankbackend.model.CreditCard;
import issuer.bank.issuerbankbackend.model.Transaction;
import issuer.bank.issuerbankbackend.model.enums.TransactionStatus;
import issuer.bank.issuerbankbackend.repository.CreditCardRepository;
import issuer.bank.issuerbankbackend.repository.TransactionRepository;
import issuer.bank.issuerbankbackend.service.ITransactionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class TransactionService implements ITransactionService {

    protected final Log log = LogFactory.getLog(getClass());

    private final TransactionRepository transactionRepository;
    private final CreditCardRepository creditCardRepository;

    public TransactionService(TransactionRepository transactionRepository, CreditCardRepository creditCardRepository) {
        this.transactionRepository = transactionRepository;
        this.creditCardRepository = creditCardRepository;
    }

    @Override
    public PccResponse handleTransactionRequest(PccRequest pccRequest) {
        log.info("Handling transaction request");

        PccResponse pccResponse = new PccResponse();

        if(pccRequest.getAcquirerOrderId().equals("") || pccRequest.getAcquirerTimestamp() == null || pccRequest.getAmount() == null ||
                pccRequest.getPan().equals("") || pccRequest.getCcv().equals("") || pccRequest.getCardholderName().equals("") || pccRequest.getExpirationDate() == null) {
            log.error("Some field was empty!");
            pccResponse.setSuccess(false);
            pccResponse.setMessage("Error, some field was empty!");
            return  pccResponse;
        }

        CreditCard creditCard = creditCardRepository.findByPanAndCcv(pccRequest.getPan(), pccRequest.getCcv());
        if(creditCard == null || creditCard.getExpirationDate().isBefore(LocalDate.now())) {
            log.error("Credit card does not exist or expired!");
            pccResponse.setAcquirerOrderId(pccRequest.getAcquirerOrderId());
            pccResponse.setAcquirerTimestamp(pccRequest.getAcquirerTimestamp());
            pccResponse.setSuccess(false);
            pccResponse.setMessage("Credit card does not exist or expired!");
            return pccResponse;
        }

        if (!creditCard.getPan().equals(pccRequest.getPan()) || !creditCard.getCcv().equals(pccRequest.getCcv()) ||
                !creditCard.getExpirationDate().equals(pccRequest.getExpirationDate())
                || !(creditCard.getClient().getFirstName() + " " + creditCard.getClient().getLastName()).equals(pccRequest.getCardholderName())) {
            log.error("Inserted values of credit card are not matching the real one");
            pccResponse.setAcquirerOrderId(pccRequest.getAcquirerOrderId());
            pccResponse.setAcquirerTimestamp(pccRequest.getAcquirerTimestamp());
            pccResponse.setSuccess(false);
            pccResponse.setMessage("Inserted values of credit card are not matching the real one");
            return pccResponse;
        }

        log.info("Checking available amount");
        if(creditCard.getAvailableAmount() - pccRequest.getAmount() < 0) {
            log.error("No enough money!");
            pccResponse.setAcquirerOrderId(pccRequest.getAcquirerOrderId());
            pccResponse.setAcquirerTimestamp(pccRequest.getAcquirerTimestamp());
            pccResponse.setSuccess(false);
            pccResponse.setMessage("No enough money!");
            return pccResponse;
        }

        log.info("Card found with available amount: " + creditCard.getAvailableAmount());
        log.info("Paying with credit card's PAN: " + creditCard.getPan().substring(0, 4) + " - **** - **** - " + creditCard.getPan().substring(12));
        creditCard.setAvailableAmount(creditCard.getAvailableAmount() - pccRequest.getAmount());
        creditCard.setReservedAmount(creditCard.getReservedAmount() + pccRequest.getAmount());
        creditCardRepository.save(creditCard);
        log.info("Amount: " + pccRequest.getAmount() + " transferred from available to reserved amount");

        Transaction transaction = new Transaction();
        transaction.setAmount(pccRequest.getAmount());
        transaction.setAcquirerOrderId(pccRequest.getAcquirerOrderId());
        transaction.setAcquirerTimestamp(pccRequest.getAcquirerTimestamp());
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setCreditCard(creditCard);
        transaction.setStatus(TransactionStatus.SUBMITTED);

        transactionRepository.save(transaction);

        log.info("Transaction is successfully executed and saved!");
        pccResponse.setAcquirerOrderId(pccRequest.getAcquirerOrderId());
        pccResponse.setAcquirerTimestamp(pccRequest.getAcquirerTimestamp());
        pccResponse.setIssuerOrderId(transaction.getId());
        pccResponse.setIssuerTimestamp(transaction.getTimestamp());
        pccResponse.setSuccess(true);

        log.info("Sending response to ACQUIRER");
        return pccResponse;
    }
}
