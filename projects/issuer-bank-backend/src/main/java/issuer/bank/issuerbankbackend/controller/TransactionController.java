package issuer.bank.issuerbankbackend.controller;

import issuer.bank.issuerbankbackend.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/transactions")
public class TransactionController {

    @Autowired
    private ITransactionService transactionService;
}
