package issuer.bank.issuerbankbackend.service;

import issuer.bank.issuerbankbackend.dto.PccRequest;
import issuer.bank.issuerbankbackend.dto.PccResponse;
import issuer.bank.issuerbankbackend.dto.WageResponse;
import issuer.bank.issuerbankbackend.dto.WageTransactionRequest;

public interface ITransactionService {
    PccResponse handleTransactionRequest(PccRequest pccRequest);
    WageResponse handleWageTransactionRequest(WageTransactionRequest wageTransactionRequest);
}
