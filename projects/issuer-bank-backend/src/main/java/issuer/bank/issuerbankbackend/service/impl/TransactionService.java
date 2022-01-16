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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class TransactionService implements ITransactionService {

    private static final Double RSDtoEUR = 0.0085;
    private static final Double USDtoEUR = 0.89;
    private static final Double YUANtoEUR = 0.14;

    protected final Log log = LogFactory.getLog(getClass());

    @Value("${encryption.key}")
    private String encryptionKey;
    @Value("${encryption.vector}")
    private String encryptionVector;

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
        if(creditCard.getAvailableAmount() - convertTransactionAmountToEUR(pccRequest.getAmount(), pccRequest.getCurrency()) < 0) {
            log.error("No enough money!");
            pccResponse.setAcquirerOrderId(pccRequest.getAcquirerOrderId());
            pccResponse.setAcquirerTimestamp(pccRequest.getAcquirerTimestamp());
            pccResponse.setSuccess(false);
            pccResponse.setMessage("No enough money!");
            return pccResponse;
        }

        log.info("Card found with available amount: " + creditCard.getAvailableAmount());
        String decryptedCreditCardPan = this.decryptPan(creditCard.getPan());
        log.info("Paying with credit card's PAN: " + decryptedCreditCardPan.substring(0, 4) + " - **** - **** - " + decryptedCreditCardPan.substring(12));
        creditCard.setAvailableAmount(creditCard.getAvailableAmount() - convertTransactionAmountToEUR(pccRequest.getAmount(), pccRequest.getCurrency()));
        creditCard.setReservedAmount(creditCard.getReservedAmount() + convertTransactionAmountToEUR(pccRequest.getAmount(), pccRequest.getCurrency()));
        creditCardRepository.save(creditCard);
        log.info("Amount: " + pccRequest.getAmount() + " transferred from available to reserved amount");

        Transaction transaction = new Transaction();
        transaction.setAmount(pccRequest.getAmount());
        transaction.setCurrency(pccRequest.getCurrency());
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
