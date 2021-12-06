package com.example.bankservice.controller;

import com.example.bankservice.dto.CreateTransactionDTO;
import com.example.bankservice.dto.TransactionResponseDTO;
import com.example.bankservice.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.PathParam;

@RestController
@CrossOrigin
@RequestMapping(name = "bank-service/")
public class TransactionController {

    @Autowired
    private ITransactionService transactionService;

    @PostMapping("/createTransaction")
    public ResponseEntity<TransactionResponseDTO> getStudents(@RequestBody CreateTransactionDTO createTransactionDTO) throws Exception {
        TransactionResponseDTO dto  = transactionService.createTransaction(createTransactionDTO);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


}