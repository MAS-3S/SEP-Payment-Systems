package pcc.pccbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pcc.pccbackend.dto.PccRequest;
import pcc.pccbackend.dto.PccResponse;
import pcc.pccbackend.service.ITransactionService;

@RestController
@RequestMapping(value = "api/pcc")
public class PccController {

    @Autowired
    private ITransactionService transactionService;

    @PostMapping(value="/forward", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PccResponse> forwardTransactionRequest(@RequestBody PccRequest pccRequest) {
        PccResponse pccResponse = transactionService.forwardTransactionRequest(pccRequest);
        return new ResponseEntity<>(pccResponse, HttpStatus.OK);
    }
}
