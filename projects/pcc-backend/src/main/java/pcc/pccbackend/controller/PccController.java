package pcc.pccbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pcc.pccbackend.service.ITransactionService;

@RestController
@RequestMapping(value = "api/pcc")
public class PccController {

    @Autowired
    private ITransactionService transactionService;

}
