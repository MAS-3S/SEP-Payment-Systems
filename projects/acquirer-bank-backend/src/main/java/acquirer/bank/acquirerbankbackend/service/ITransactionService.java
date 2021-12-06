package acquirer.bank.acquirerbankbackend.service;

import acquirer.bank.acquirerbankbackend.dto.CreditCardRequest;
import acquirer.bank.acquirerbankbackend.dto.TransactionRequest;
import acquirer.bank.acquirerbankbackend.dto.TransactionResponse;

import java.net.URISyntaxException;

public interface ITransactionService {
    TransactionResponse checkTransactionAndReturnUrl(TransactionRequest transactionRequest);
    TransactionResponse executeTransaction(String transactionId, CreditCardRequest creditCardRequest) throws URISyntaxException;
}
