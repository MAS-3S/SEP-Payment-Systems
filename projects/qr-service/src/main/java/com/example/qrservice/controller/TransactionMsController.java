package com.example.qrservice.controller;

import com.example.qrservice.dto.CreateTransactionDTO;
import com.example.qrservice.dto.TransactionResponseDTO;
import com.example.qrservice.service.ITransactionService;
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
}
