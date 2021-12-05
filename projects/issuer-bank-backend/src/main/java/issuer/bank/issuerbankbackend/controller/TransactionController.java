package issuer.bank.issuerbankbackend.controller;

import issuer.bank.issuerbankbackend.dto.PccRequest;
import issuer.bank.issuerbankbackend.dto.PccResponse;
import issuer.bank.issuerbankbackend.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/transactions")
public class TransactionController {

    @Autowired
    private ITransactionService transactionService;

    @PostMapping(value="/handle", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PccResponse> handleTransactionRequest(@RequestBody PccRequest pccRequest) {
        PccResponse pccResponse = transactionService.handleTransactionRequest(pccRequest);
        return new ResponseEntity<>(pccResponse, HttpStatus.OK);
    }
}
