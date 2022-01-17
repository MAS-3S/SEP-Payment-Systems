package com.example.pspservice.service.impl;

import com.example.pspservice.dto.*;
import com.example.pspservice.mapper.PaymentMethodTypeMapper;
import com.example.pspservice.model.Merchant;
import com.example.pspservice.model.Payment;
import com.example.pspservice.model.PaymentMethodType;
import com.example.pspservice.repository.MerchantRepository;
import com.example.pspservice.repository.PaymentMethodTypeRepository;
import com.example.pspservice.repository.PaymentRepository;
import com.example.pspservice.service.IPaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Service
public class PaymentMethodService implements IPaymentMethodService {

    protected final Log log = LogFactory.getLog(getClass());

    @Autowired
    RestTemplate restTemplate;

    private static final String HTTP_PREFIX = "http://";
    private static final String HTTPS_PREFIX = "https://";

    @Value("${pspfront.port}")
    private String pspFrontPort;
    @Value("${pspfront.host}")
    private String pspFrontHost;
    @Value("${pspfront.subscribe.route}")
    private String pspFrontSubscribeUrl;
    @Value("${pspfront.payment.route}")
    private String pspFrontPaymentUrl;

    private final PaymentMethodTypeRepository paymentMethodTypeRepository;
    private final MerchantRepository merchantRepository;
    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentMethodService(PaymentMethodTypeRepository paymentMethodTypeRepository, MerchantRepository merchantRepository, PaymentRepository paymentRepository) {
        this.paymentMethodTypeRepository = paymentMethodTypeRepository;
        this.merchantRepository = merchantRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public List<PaymentMethodType> findAll() {
        log.info("Getting all payment methods");
        return paymentMethodTypeRepository.findAll();
    }

    //izlistavanje svih nacina placanja webshop-a prilikom potrebe za subscribe-ovanjem
    @Override
    public List<PaymentMethodTypeForMerchantDTO> findMerchantsPaymentMethodsForSubscription(String merchantId) throws Exception {
        log.info("Finding merchant's payment methods for subscription");
        if (merchantId == null) {
            throw new Exception("Merchant id is null!");
        }

        Merchant merchant = merchantRepository.findById(merchantId).orElse(null);

        if (merchant == null) {
            log.error("Merchant with id " + merchantId + " doesn't exists");
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

        log.info("Returning payment methods");
        return returnPaymentsDTO;
    }

    //slanje webshop (admina) na stranicu u kojoj se vrsi subscribe
    @Override
    public String redirectMerchantToSubscribePage(String merchantId) throws Exception {
        log.info("Redirecting merchant to subscribe page from PSP");
        Merchant merchant = merchantRepository.findByMerchantId(merchantId);
        if (merchant == null) {
            log.error("Failed to get url from psp subscribe page");
            log.error("Merchant with id: " + merchantId + " doesn't exists.");
            throw new EntityNotFoundException("Merchant with id: " + merchantId + " doesn't exists.");
        }
        return HTTPS_PREFIX + this.pspFrontHost + ":" + this.pspFrontPort + this.pspFrontSubscribeUrl + merchant.getId();
    }

    //slanje kupca da odabere nacin placanja
    @Override
    public String redirectMerchantToPaymentPage(RequestPaymentDTO dto) throws Exception {
        log.info("Redirecting merchant to payment page from PSP");
        Merchant merchant = merchantRepository.findByMerchantId(dto.getMerchantId());
        if (merchant == null) {
            log.error("Failed to get url from psp payment page");
            log.error("Merchant with id: " + dto.getMerchantId() + " doesn't exists.");
            throw new EntityNotFoundException("Merchant with id: " + dto.getMerchantId() + " doesn't exists.");
        }

        Payment payment = new Payment();
        payment.setMerchantOrderId(dto.getTransactionId());
        payment.setMerchantTimeStamp(dto.getTimestamp());
        payment.setAmount(dto.getAmount());
        payment.setCurrency(dto.getCurrency());
        payment.setPossibleSubscription(dto.isPossibleSubscription());
        payment.setReturnUrl("");
        paymentRepository.save(payment);

        return HTTPS_PREFIX + this.pspFrontHost + ":" + this.pspFrontPort + this.pspFrontPaymentUrl  + merchant.getId() +
                "/" + payment.getId();
    }

    //menjanje pretplate na odredjeni nacin placanja
    @Override
    @Transactional
    public void changeSubscriptionToPaymentMethod(SubscribeToPaymentMethodDTO dto) throws Exception {
        log.info("Changing subscribed payment methods");

        if (!paymentMethodTypeRepository.findById(dto.getPaymentId()).isPresent()) {
            log.error("Failed to subscribe to payment method because payment method doesn't exists");
            throw new Exception("Payment doesn't exists");
        } else if (merchantRepository.findById(dto.getMerchantId()).orElse(null) == null) {
            log.error("Failed to subscribe to payment method because merchant doesn't exists");
            throw new Exception("Merchant doesn't exists");
        }

        PaymentMethodType paymentMethodType = paymentMethodTypeRepository.findById(dto.getPaymentId()).orElse(null);
        Merchant merchant = merchantRepository.findById(dto.getMerchantId()).orElse(null);

        assert merchant != null;
        if (merchant.getPaymentMethodTypes().stream().noneMatch(methodType -> methodType.getId().equals(dto.getPaymentId()))) {
            assert paymentMethodType != null;
            log.info("Subscribing to new payment method: " + paymentMethodType.getName());

            try {
                ResponseEntity<Boolean> isMerchantPresent = restTemplate.getForEntity("https://" + paymentMethodType.getServiceName() + "/checkIfMerchantExists/" + merchant.getMerchantId(), Boolean.class);
                log.info("Successfully redirected to " + paymentMethodType.getServiceName());
                if (Objects.equals(isMerchantPresent.getBody(), true)) {
                    merchant.addToPaymentMethod(paymentMethodType);
                    merchantRepository.save(merchant);
                }
            } catch (Exception e) {
                log.error("Failed to redirect to " + paymentMethodType.getServiceName() + " payment service");
                throw new Exception("Failed to redirect to " + paymentMethodType.getServiceName() + " payment service");
            }


        } else {
            assert paymentMethodType != null;
            log.info("Unsubscribing from payment method: " +paymentMethodType.getName());
            merchant.deleteFromPaymentMethod(paymentMethodType);
            merchantRepository.save(merchant);
        }
    }

    //izlistavanje svih nacina placanja webshop-a prilikom potrebe za konkretnim placanjem
    @Override
    public List<PaymentMethodTypeDTO> findMerchantsPaymentMethodsForPayment(String merchantId) throws Exception {
        log.info("Finding merchant's payment methods for payment choosing");

        if (merchantId == null || merchantId.trim().equals("")) {
            log.error("Merchant with id: " + merchantId + " is null");
            throw new Exception("Merchant id is null!");
        }

        Merchant merchant = merchantRepository.findById(merchantId).orElse(null);

        if (merchant == null) {
            log.error("Merchant with id " + merchantId + " doesn't exists");
            throw  new Exception("Merchant does not exists!");
        }

        List<PaymentMethodTypeDTO> returnPaymentsDTO = new ArrayList<>();
        for (PaymentMethodType p : merchant.getPaymentMethodTypes()) {
            PaymentMethodTypeDTO dto = new PaymentMethodTypeDTO();
            dto.setId(p.getId());
            dto.setName(p.getName());
            dto.setImage(decodeBase64(p.getImage()));
            dto.setDescription(p.getDescription());
            returnPaymentsDTO.add(dto);
        }
        log.info("Returning payment methods");
        return returnPaymentsDTO;
    }

    //odabiranje nacina placanje i redirektovanje na sledeci servis u okviru PSP-a
    @Override
    @Transactional
    public String choosePaymentMethod(ChoosePaymentMethodDTO choosePaymentMethodDTO) throws Exception {
        log.info("Choosing payment method to execute payment");

        if (choosePaymentMethodDTO.getPaymentMethodId() == null || choosePaymentMethodDTO.getPaymentMethodId().trim().equals("") ||
        choosePaymentMethodDTO.getPaymentId() == null || choosePaymentMethodDTO.getPaymentId().trim().equals("") ||
        choosePaymentMethodDTO.getMerchantId() == null || choosePaymentMethodDTO.getMerchantId().trim().equals("")) {
            log.error("ChoosePaymentDTO fields are null");
            throw new NullPointerException("Missing ChoosePaymentDTO fields");
        }

        if (!merchantRepository.findById(choosePaymentMethodDTO.getMerchantId()).isPresent()) {
            log.error("Merchant in psp with id " + choosePaymentMethodDTO.getMerchantId() + " does not exists");
            throw new Exception("Merchant in psp with id " + choosePaymentMethodDTO.getMerchantId() + " does not exists");
        }

        PaymentMethodType paymentMethodType = paymentMethodTypeRepository.findById(choosePaymentMethodDTO.getPaymentMethodId()).orElse(null);
        if (paymentMethodType == null) {
            log.error("PaymentMethodType with id " + choosePaymentMethodDTO.getPaymentMethodId() + " is null");
            throw new NullPointerException("Missing ChoosePaymentDTO fields");
        }

        Payment payment = paymentRepository.findById(choosePaymentMethodDTO.getPaymentId()).orElse(null);

        if (payment != null) {
            payment.setPaymentMethodType(paymentMethodType);
            paymentRepository.save(payment);
        } else {
            log.error("Failed to set payment method type: " + paymentMethodType.getName());
            return "";
        }

        Merchant merchant = merchantRepository.getById(choosePaymentMethodDTO.getMerchantId());
        TransactionResponseDTO httpResponse;

        try {
            CreateTransactionDTO createTransactionDTO = new CreateTransactionDTO();
            createTransactionDTO.setMerchantOrderId(payment.getMerchantOrderId());
            createTransactionDTO.setMerchantId(merchant.getMerchantId());
            createTransactionDTO.setAmount(payment.getAmount());
            createTransactionDTO.setCurrency(payment.getCurrency());
            createTransactionDTO.setPossibleSubscription(payment.isPossibleSubscription());
            createTransactionDTO.setTime(payment.getMerchantTimeStamp());
            createTransactionDTO.setSuccessUrl(merchant.getSuccessUrl());
            createTransactionDTO.setFailedUrl(merchant.getFailedUrl());
            createTransactionDTO.setErrorUrl(merchant.getErrorUrl());
            log.info("Trying to redirect to " + paymentMethodType.getServiceName());
            httpResponse = restTemplate.postForObject("https://" + paymentMethodType.getServiceName() + "/createTransaction", createTransactionDTO, TransactionResponseDTO.class);
            log.info("Successfully redirected to " + paymentMethodType.getServiceName());
        } catch (Exception e) {
            log.error("Failed to redirect to " + paymentMethodType.getServiceName() + " payment service");
            throw new Exception("Failed to redirect to " + paymentMethodType.getServiceName() + " payment service");
        }

        assert httpResponse != null;
        return httpResponse.getPaymentUrl();

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
