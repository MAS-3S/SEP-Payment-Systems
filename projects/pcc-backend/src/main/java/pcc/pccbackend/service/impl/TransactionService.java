package pcc.pccbackend.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import pcc.pccbackend.dto.PccResponse;
import pcc.pccbackend.dto.PccRequest;
import pcc.pccbackend.model.Transaction;
import pcc.pccbackend.repository.TransactionRepository;
import pcc.pccbackend.service.ITransactionService;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

@Service
public class TransactionService implements ITransactionService {

    protected final Log log = LogFactory.getLog(getClass());

    private static final String HTTP_PREFIX = "http://";
    @Value("${issuer.bank.port}")
    private String issuerBankPort;
    @Value("${issuer.bank.host}")
    private String issuerBankHost;
    @Value("${issuer.bank.transaction.route}")
    private String issuerBankTransactionUrl;

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional
    public PccResponse forwardTransactionRequest(PccRequest pccRequest) {
        log.info("Forwarding transaction from acquirer to issuer for acquirerOrderId: " + pccRequest.getAcquirerOrderId());
        PccResponse pccResponse = new PccResponse();

        if(pccRequest.getAcquirerOrderId().equals("") || pccRequest.getAcquirerTimestamp() == null || pccRequest.getAmount() == null ||
                pccRequest.getPan().equals("") || pccRequest.getCcv().equals("") || pccRequest.getCardholderName().equals("") || pccRequest.getExpirationDate() == null) {
            log.error("Some field was empty!");
            pccResponse.setSuccess(false);
            pccResponse.setMessage("Error, some field was empty!");
            return  pccResponse;
        }

        Transaction transaction = new Transaction();
        transaction.setAcquirerOrderId(pccRequest.getAcquirerOrderId());
        transaction.setAcquirerTimestamp(LocalDateTime.now());
        transaction.setAmount(pccRequest.getAmount());
        transaction.setCurrency(pccRequest.getCurrency());
        transaction.setPan(pccRequest.getPan());
        transaction.setCcv(pccRequest.getCcv());
        transaction.setExpirationDate(pccRequest.getExpirationDate());
        transaction.setCardholderName(pccRequest.getCardholderName());
        transactionRepository.save(transaction);

        pccRequest.setAcquirerOrderId(transaction.getId());
        pccRequest.setAcquirerTimestamp(transaction.getAcquirerTimestamp());

        try{
            pccResponse = forwardToIssuerBank(pccRequest);
        } catch (Exception e) {
            log.error("Issuer bank redirection error!");
        }

        transaction.setAcquirerOrderId(transaction.getId());
        transaction.setSuccess(pccResponse.isSuccess());
        transaction.setMessage(pccResponse.getMessage());
        transactionRepository.save(transaction);

        log.info("Transaction with id:" + transaction.getId() +" executed and saved!");
        return pccResponse;
    }

    private PccResponse forwardToIssuerBank(PccRequest pccRequest) throws URISyntaxException {
        log.info("Forwarding request to ISSUER");

        RestTemplate restTemplate = new RestTemplate();
        final String url = HTTP_PREFIX + this.issuerBankHost + ":" + this.issuerBankPort + this.issuerBankTransactionUrl;
        URI uri = new URI(url);

        ResponseEntity<PccResponse> result = restTemplate.postForEntity(uri, pccRequest, PccResponse.class);
        return result.getBody();
    }
}
