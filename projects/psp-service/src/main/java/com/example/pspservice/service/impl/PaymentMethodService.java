package com.example.pspservice.service.impl;

import com.example.pspservice.dto.PaymentMethodTypeForMerchantDTO;
import com.example.pspservice.dto.SubscribeToPaymentMethodDTO;
import com.example.pspservice.model.Merchant;
import com.example.pspservice.model.PaymentMethodType;
import com.example.pspservice.repository.MerchantRepository;
import com.example.pspservice.repository.PaymentMethodTypeRepository;
import com.example.pspservice.service.IPaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class PaymentMethodService implements IPaymentMethodService {

    protected final Log log = LogFactory.getLog(getClass());

    @Value("${pspfront.port}")
    private String pspFrontPort;
    @Value("${pspfront.host}")
    private String pspFrontHost;
    @Value("${pspfront.subscribe.route}")
    private String pspFrontSubscribeUrl;
    //izmeni posle u https
    private static final String HTTP_PREFIX = "http://";

    private final PaymentMethodTypeRepository paymentMethodTypeRepository;
    private final MerchantRepository merchantRepository;

    @Autowired
    public PaymentMethodService(PaymentMethodTypeRepository paymentMethodTypeRepository, MerchantRepository merchantRepository) {
        this.paymentMethodTypeRepository = paymentMethodTypeRepository;
        this.merchantRepository = merchantRepository;
    }

    @Override
    public List<PaymentMethodType> findAll() {
        log.info("Getting all payment methods");
        return paymentMethodTypeRepository.findAll();
    }

    @Override
    public List<PaymentMethodTypeForMerchantDTO> findMerchantsPaymentMethods(String merchantId) throws Exception {
        if (merchantId == null) {
            throw new Exception("Merchant id is null!");
        }

        Merchant merchant = merchantRepository.findById(merchantId).orElse(null);

        if (merchant == null) {
            log.error("Merchant with id " + merchantId + " doen't exists");
            throw  new Exception("Merchant does not exists!");
        }

        List<PaymentMethodType> paymentMethodTypes = findAll();
        List<PaymentMethodType> paymentMethodTypesForMerchant = merchant.getPaymentMethodTypes();
        List<PaymentMethodTypeForMerchantDTO> returnPaymentsDTO = new ArrayList<>();

        for (PaymentMethodType p : paymentMethodTypes) {
            PaymentMethodTypeForMerchantDTO dto = new PaymentMethodTypeForMerchantDTO();
            dto.setId(p.getId());
            dto.setName(p.getName());
            dto.setSubscribed(paymentMethodTypesForMerchant.stream().anyMatch(methodType -> methodType.getName().equals(p.getName())));
            dto.setImage(decodeBase64(p.getImage()));
            dto.setDescription(p.getDescription());
            returnPaymentsDTO.add(dto);
        }
        return returnPaymentsDTO;
    }

    @Override
    public String redirectMerchantToSubscribePage(String merchantId) throws Exception {
        log.info("Redirecting merchant to subscribe page from PSP");
        Merchant merchant = merchantRepository.findByMerchantId(merchantId);
        if (merchant == null) {
            throw new EntityNotFoundException("Merchant with id: " + merchantId + " doesn't exists.");
        }
        return HTTP_PREFIX + this.pspFrontHost + ":" + this.pspFrontPort + this.pspFrontSubscribeUrl + merchant.getId();
    }

    @Override
    public void changeSubscriptionToPaymentMethod(SubscribeToPaymentMethodDTO dto) throws Exception {
        if (!paymentMethodTypeRepository.findById(dto.getPaymentId()).isPresent()) {
            log.info("Failed to subscribe to payment method because payment method doesn't exists");
            throw new Exception("Payment doesn't exists");
        } else if (merchantRepository.findById(dto.getMerchantId()).orElse(null) == null) {
            log.info("Failed to subscribe to payment method because merchant doesn't exists");
            throw new Exception("Merchant doesn't exists");
        }

        PaymentMethodType paymentMethodType = paymentMethodTypeRepository.findById(dto.getPaymentId()).orElse(null);
        Merchant merchant = merchantRepository.findById(dto.getMerchantId()).orElse(null);

        assert merchant != null;
        if (merchant.getPaymentMethodTypes().stream().noneMatch(methodType -> methodType.getId().equals(dto.getPaymentId()))) {
            log.info("Subscribing to new payment method");
            merchant.addToPaymentMethod(paymentMethodType);
            merchantRepository.save(merchant);
        } else {
            assert paymentMethodType != null;
            log.info("Unsubscribing from payment method");
            merchant.deleteFromPaymentMethod(paymentMethodType);
            merchantRepository.save(merchant);
        }
    }

    private static String decodeBase64(String image) {
        File currDir = new File(System.getProperty("user.dir"));
        File assetFolder = new File(currDir, "src/main/java/com/example/pspservice/assets/images");
        File imagePath = new File(assetFolder, image);
        String encodedFile = "";
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(imagePath);
            byte[] bytes = new byte[(int)imagePath.length()];
            fileInputStreamReader.read(bytes);
            encodedFile = Base64.getEncoder().encodeToString(bytes);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "data:image/jpg;base64," + encodedFile;
    }
}
