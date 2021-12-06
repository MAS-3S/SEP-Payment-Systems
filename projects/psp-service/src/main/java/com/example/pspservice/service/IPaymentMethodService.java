package com.example.pspservice.service;

import com.example.pspservice.dto.*;
import com.example.pspservice.model.PaymentMethodType;

import java.util.List;

public interface IPaymentMethodService {

    List<PaymentMethodType> findAll();

    List<PaymentMethodTypeForMerchantDTO> findMerchantsPaymentMethodsForSubscription(String merchantId) throws Exception;

    String redirectMerchantToSubscribePage(String merchantId) throws Exception;

    String redirectMerchantToPaymentPage(RequestPaymentDTO dto) throws Exception;

    void changeSubscriptionToPaymentMethod(SubscribeToPaymentMethodDTO dto) throws Exception;

    List<PaymentMethodTypeDTO> findMerchantsPaymentMethodsForPayment(String merchantId) throws Exception;

    String choosePaymentMethod(ChoosePaymentMethodDTO dto) throws Exception;


}
