package pcc.pccbackend.service;

import pcc.pccbackend.dto.PccResponse;
import pcc.pccbackend.dto.PccRequest;
import pcc.pccbackend.dto.WageResponse;
import pcc.pccbackend.dto.WageTransactionRequest;

public interface ITransactionService {
    PccResponse forwardTransactionRequest(PccRequest pccResponse);
    WageResponse forwardWageTransactionRequest(WageTransactionRequest wageTransactionRequest);
}
