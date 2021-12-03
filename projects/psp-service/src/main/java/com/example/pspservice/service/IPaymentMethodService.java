package com.example.pspservice.service;

import com.example.pspservice.dto.PaymentMethodTypeDTO;
import com.example.pspservice.dto.PaymentMethodTypeForMerchantDTO;
import com.example.pspservice.dto.SubscribeToPaymentMethodDTO;
import com.example.pspservice.model.PaymentMethodType;

import java.util.List;

public interface IPaymentMethodService {

    List<PaymentMethodType> findAll();

    List<PaymentMethodTypeForMerchantDTO> findMerchantsPaymentMethodsForSubscription(String merchantId) throws Exception;

    String redirectMerchantToSubscribePage(String merchantId) throws Exception;

    String redirectMerchantToPaymentPage(String merchantId) throws Exception;

    void changeSubscriptionToPaymentMethod(SubscribeToPaymentMethodDTO dto) throws Exception;

    List<PaymentMethodTypeDTO> findMerchantsPaymentMethodsForPayment(String merchantId) throws Exception;


}
