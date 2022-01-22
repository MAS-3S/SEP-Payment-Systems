package com.example.bankservice.controller;

import com.example.bankservice.dto.CreateTransactionDTO;
import com.example.bankservice.dto.TransactionResponseDTO;
import com.example.bankservice.dto.WageResponse;
import com.example.bankservice.dto.WageTransactionRequest;
import com.example.bankservice.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(name = "bank-service")
public class TransactionMsController {

    @Autowired
    private ITransactionService transactionService;

    @PostMapping("/createTransaction")
    public ResponseEntity<TransactionResponseDTO> createTransaction(@RequestBody CreateTransactionDTO createTransactionDTO) throws Exception {
        TransactionResponseDTO dto  = transactionService.createTransaction(createTransactionDTO);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/checkIfMerchantExists/{merchantId}")
    public ResponseEntity<Boolean> checkIfMerchantExists(@PathVariable String merchantId) throws Exception { ;
        return new ResponseEntity<>(transactionService.checkIfMerchantExists(merchantId), HttpStatus.OK);
    }

    @PostMapping("/paymentWage")
    public ResponseEntity<WageResponse> paymentWage(@RequestBody WageTransactionRequest wageTransactionRequest) throws Exception {
        WageResponse wageResponse  = transactionService.paymentWage(wageTransactionRequest);
        return new ResponseEntity<>(wageResponse, HttpStatus.OK);
    }
}
