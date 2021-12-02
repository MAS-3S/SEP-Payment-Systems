package com.example.pspservice.service;

import com.example.pspservice.dto.PaymentMethodTypeForMerchantDTO;
import com.example.pspservice.dto.SubscribeToPaymentMethodDTO;
import com.example.pspservice.model.PaymentMethodType;

import java.util.List;

public interface IPaymentMethodService {

    List<PaymentMethodType> findAll();

    List<PaymentMethodTypeForMerchantDTO> findMerchantsPaymentMethods(String merchantId) throws Exception;

    String redirectMerchantToSubscribePage(String merchantId);

    void changeSubscriptionToPaymentMethod(SubscribeToPaymentMethodDTO dto) throws Exception;


}
