package issuer.bank.issuerbankbackend.service;

import issuer.bank.issuerbankbackend.dto.PccRequest;
import issuer.bank.issuerbankbackend.dto.PccResponse;

public interface ITransactionService {
    PccResponse handleTransactionRequest(PccRequest pccRequest);
}
