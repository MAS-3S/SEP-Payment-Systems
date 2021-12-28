package com.example.qrservice.controller;

import com.example.qrservice.dto.PspResponseDTO;
import com.example.qrservice.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "qr-service")
public class TransactionController {

    @Autowired
    private ITransactionService transactionService;

    @PostMapping("/checkTransaction")
    public ResponseEntity<?> checkTransaction(@RequestBody PspResponseDTO dto) throws Exception {
        transactionService.finishTransaction(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
