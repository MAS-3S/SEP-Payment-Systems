package acquirer.bank.acquirerbankbackend.service;

import acquirer.bank.acquirerbankbackend.dto.*;
import acquirer.bank.acquirerbankbackend.model.CreditCard;
import acquirer.bank.acquirerbankbackend.model.Transaction;

import java.net.URISyntaxException;

public interface ITransactionService {
    TransactionResponse checkTransactionAndReturnUrl(TransactionRequest transactionRequest, String type);
    TransactionResponse executeTransaction(String transactionId, CreditCardRequest creditCardRequest, String type) throws URISyntaxException;
    Transaction findById(String id);
    CreditCard findCreditCardByMerchantPan(String pan);
    WageResponse paymentWage(WageTransactionRequest wageTransactionRequest);
}
