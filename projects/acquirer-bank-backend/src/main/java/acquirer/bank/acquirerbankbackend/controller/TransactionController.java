package acquirer.bank.acquirerbankbackend.controller;

import acquirer.bank.acquirerbankbackend.dto.TransactionRequest;
import acquirer.bank.acquirerbankbackend.dto.TransactionResponse;
import acquirer.bank.acquirerbankbackend.service.ITransactionService;
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

    @PostMapping(value="/check", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponse> checkTransactionAndReturnUrl(@RequestBody TransactionRequest transactionRequest) {
        TransactionResponse transactionResponse = transactionService.checkTransactionAndReturnUrl(transactionRequest);
        return new ResponseEntity<>(transactionResponse, HttpStatus.OK);
    }
}
