package com.example.pspservice.controller;

import com.example.pspservice.dto.*;
import com.example.pspservice.mapper.PaymentMethodTypeMapper;
import com.example.pspservice.service.IPaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/psp/paymentMethods")
public class PaymentMethodController {

    @Autowired
    private IPaymentMethodService paymentMethodService;
    @Autowired
    RestTemplate restTemplate;


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
    public ResponseEntity<?> sendMerchantToPaymentPage(@RequestBody RequestPaymentDTO dto) throws Exception {
        return new ResponseEntity<>(paymentMethodService.redirectMerchantToPaymentPage(dto), HttpStatus.OK);
    }

    @PostMapping(value = "/changeSubscription", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeMerchantSubscription(@RequestBody SubscribeToPaymentMethodDTO dto) throws Exception {
        paymentMethodService.changeSubscriptionToPaymentMethod(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/choosePaymentMethod", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> choosePaymentMethod(@RequestBody ChoosePaymentMethodDTO dto) throws Exception {
        String s = paymentMethodService.choosePaymentMethod(dto);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    @PostMapping(value = "/paymentWage", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WageResponse> paymentWage(@RequestBody WageRequest dto) throws Exception {
        WageResponse wageResponse = paymentMethodService.paymentWage(dto);
        return new ResponseEntity<>(wageResponse, HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<?> getStudents() {
        String objects = restTemplate.getForObject("http://test-service/test/message", String.class);
        return new ResponseEntity<>(objects, HttpStatus.OK);
    }

}
