package acquirer.bank.acquirerbankbackend.service;

import acquirer.bank.acquirerbankbackend.dto.CreditCardRequest;
import acquirer.bank.acquirerbankbackend.dto.TransactionRequest;
import acquirer.bank.acquirerbankbackend.dto.TransactionResponse;
import acquirer.bank.acquirerbankbackend.model.Transaction;

import java.net.URISyntaxException;

public interface ITransactionService {
    TransactionResponse checkTransactionAndReturnUrl(TransactionRequest transactionRequest, String type);
    TransactionResponse executeTransaction(String transactionId, CreditCardRequest creditCardRequest, String type) throws URISyntaxException;
    Transaction findById(String id);
}
