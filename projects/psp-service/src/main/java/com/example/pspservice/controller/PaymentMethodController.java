package com.example.pspservice.controller;

import com.example.pspservice.dto.PaymentMethodTypeDTO;
import com.example.pspservice.dto.PaymentMethodTypeForMerchantDTO;
import com.example.pspservice.dto.SubscribeToPaymentMethodDTO;
import com.example.pspservice.dto.SubscribeUrlDTO;
import com.example.pspservice.mapper.PaymentMethodTypeMapper;
import com.example.pspservice.service.IPaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/psp/paymentMethods")
public class PaymentMethodController {

    @Autowired
    private IPaymentMethodService paymentMethodService;
    
    @GetMapping(name = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PaymentMethodTypeDTO>> getAllPaymentMethodTypes() {
        return new ResponseEntity<>(PaymentMethodTypeMapper.mapEntitiesToDTOs(paymentMethodService.findAll()), HttpStatus.OK);
    }

    @GetMapping(value = "/{merchantId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PaymentMethodTypeForMerchantDTO>> getMerchantsPaymentMethodsForSubscription(@PathVariable String merchantId) throws Exception {
        return new ResponseEntity<>(paymentMethodService.findMerchantsPaymentMethodsForSubscription(merchantId), HttpStatus.OK);
    }

    @GetMapping(value = "/payment/{merchantId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PaymentMethodTypeDTO>> getMerchantsPaymentMethodsForPayment(@PathVariable String merchantId) throws Exception {
        return new ResponseEntity<>(paymentMethodService.findMerchantsPaymentMethodsForPayment(merchantId), HttpStatus.OK);
    }

    @PostMapping(value = "/subscribeUrl", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendMerchantToSubscribePage(@RequestBody SubscribeUrlDTO dto) throws Exception {
        return new ResponseEntity<>(paymentMethodService.redirectMerchantToSubscribePage(dto.getMerchantId()), HttpStatus.OK);
    }

    @PostMapping(value = "/paymentUrl", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendMerchantToPaymentPage(@RequestBody SubscribeUrlDTO dto) throws Exception {
        return new ResponseEntity<>(paymentMethodService.redirectMerchantToPaymentPage(dto.getMerchantId()), HttpStatus.OK);
    }

    @PostMapping(value = "/changeSubscription", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeMerchantSubscription(@RequestBody SubscribeToPaymentMethodDTO dto) throws Exception {
        paymentMethodService.changeSubscriptionToPaymentMethod(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
