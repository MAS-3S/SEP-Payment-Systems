package acquirer.bank.acquirerbankbackend.service;

import acquirer.bank.acquirerbankbackend.dto.TransactionRequest;
import acquirer.bank.acquirerbankbackend.dto.TransactionResponse;

public interface ITransactionService {
    TransactionResponse checkTransactionAndReturnUrl(TransactionRequest transactionRequest);
}
