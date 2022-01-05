package com.example.paypalservice.controller;

import com.example.paypalservice.dto.PayPalTransactionDTO;
import com.example.paypalservice.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "paypal-service")
public class TransactionController {

    @Autowired
    private ITransactionService transactionService;

    @GetMapping("/transactions/{transactionId}")
    public ResponseEntity<PayPalTransactionDTO> getTransactionForPayPalBy(@PathVariable String transactionId) throws Exception {
        return new ResponseEntity<>(transactionService.getTransactionForPayPalBy(transactionId), HttpStatus.OK);
    }

    @PutMapping("/transactions/{transactionId}/success")
    public ResponseEntity<?> changeTransactionStatusToSuccess(@PathVariable String transactionId) {
        transactionService.changeTransactionStatusToSuccess(transactionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/transactions/{transactionId}/cancel")
    public ResponseEntity<?> changeTransactionStatusToCanceled(@PathVariable String transactionId) {
        transactionService.changeTransactionStatusToCanceled(transactionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
