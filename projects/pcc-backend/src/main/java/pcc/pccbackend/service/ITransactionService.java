package pcc.pccbackend.service;

import pcc.pccbackend.dto.PccResponse;
import pcc.pccbackend.dto.PccRequest;

public interface ITransactionService {
    PccResponse forwardTransactionRequest(PccRequest pccResponse);
}
