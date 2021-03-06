package com.example.bankservice.controller;

import com.example.bankservice.dto.CreateTransactionDTO;
import com.example.bankservice.dto.PspResponseDTO;
import com.example.bankservice.dto.TransactionResponseDTO;
import com.example.bankservice.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping(value = "bank-service")
public class TransactionController {

    @Autowired
    private ITransactionService transactionService;


    @PostMapping("/checkTransaction")
    public ResponseEntity<?> checkTransaction(@RequestBody PspResponseDTO dto) throws Exception {
        transactionService.finishTransaction(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
