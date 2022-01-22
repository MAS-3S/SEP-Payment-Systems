package com.example.webshopbackend.service.impl;

import com.example.webshopbackend.dto.*;
import com.example.webshopbackend.mapper.PaymentMapper;
import com.example.webshopbackend.model.*;
import com.example.webshopbackend.model.enums.TransactionStatus;
import com.example.webshopbackend.repository.*;
import com.example.webshopbackend.service.IShoppingCartService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class ShoppingCartService implements IShoppingCartService {

    protected final Log log = LogFactory.getLog(getClass());
    private static final String HTTP_PREFIX = "http://";
    private static final String HTTPS_PREFIX = "https://";

    @Autowired
    RestTemplate restTemplate;

    @Value("${pspback.port}")
    private String pspBackPort;
    @Value("${pspback.host}")
    private String pspBackHost;
    @Value("${pspback.paymentUrl.route}")
    private String pspBackPaymentUrl;
    @Value("${pspback.paymentWageUrl.route}")
    private String pspBackPaymentWageUrl;
    @Value("${encryption.key}")
    private String encryptionKey;
    @Value("${encryption.vector}")
    private String encryptionVector;

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final WebShopRepository webShopRepository;
    private final ProductRepository productRepository;
    private final ItemToPurchaseRepository itemToPurchaseRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, UserRepository userRepository, WebShopRepository webShopRepository, ProductRepository productRepository, ItemToPurchaseRepository itemToPurchaseRepository, TransactionRepository transactionRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.webShopRepository = webShopRepository;
        this.productRepository = productRepository;
        this.itemToPurchaseRepository = itemToPurchaseRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional
    public String save(PaymentDto paymentDto) throws Exception {
        log.info("Accepted shopping card data.");
        User user = userRepository.findById(paymentDto.getUserId());
        WebShop webShop = webShopRepository.findById(paymentDto.getWebShopId()).orElse(null);
        if (user == null) {
            log.error("User with id: " + paymentDto.getUserId() + " does not exist!");
            throw new Exception("Invalid user id!");
        }

        if (webShop == null) {
            log.error("Web shop with id: " + paymentDto.getWebShopId() + " does not exist!");
            throw new Exception("Invalid web shop id!");
        }

        ShoppingCart shoppingCart = PaymentMapper.convertToShoppingCart(paymentDto, user, webShop);
        shoppingCartRepository.save(shoppingCart);
        log.info("Shopping card " + shoppingCart.getId() + " is saved.");

        boolean isPossibleSubscription = false;
        for (ItemToPurchaseDto dto : paymentDto.getItemsToPurchase()) {
            ItemToPurchase itemToPurchase = new ItemToPurchase();

            Product product = productRepository.findById(dto.getProductId()).orElse(null);
            if (product == null) {
                log.error("Product with id: " + dto.getProductId() + " does not exist!");
                throw new Exception("Invalid product id!");
            }

            if(dto.isPossibleSubscription()) {
                isPossibleSubscription = true;
            }
            itemToPurchase.setShoppingCart(shoppingCart);
            itemToPurchase.setProduct(product);
            itemToPurchase.setQuantity(dto.getQuantity());
            itemToPurchaseRepository.save(itemToPurchase);
        }

        Transaction transaction = new Transaction();

        transaction.setAmount(shoppingCart.getTotalPrice());
        transaction.setCurrency(paymentDto.getCurrency());
        transaction.setTimestamp(shoppingCart.getCreateDate());
        transaction.setStatus(TransactionStatus.IN_PROGRESS);
        transaction.setShoppingCart(shoppingCart);

        transactionRepository.save(transaction);
        log.info("Transaction " + transaction.getId() + " is saved.");

        return getPspPaymentUrl(transaction, paymentDto.getWebShopId(), paymentDto.getCurrency(), isPossibleSubscription);
    }

    @Override
    @Transactional
    public WageResponse paymentWage(WageDto wageDto) throws Exception {
        log.info("Accepted wage data.");
        Transaction transaction = new Transaction();

        transaction.setAmount(wageDto.getAmount());
        transaction.setCurrency(wageDto.getCurrency());
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setStatus(TransactionStatus.IN_PROGRESS);

        transactionRepository.save(transaction);
        log.info("Transaction " + transaction.getId() + " is saved.");

        wageDto.setAccountNumber(this.encrypt(wageDto.getAccountNumber()));
        wageDto.setBankNumber(this.encrypt(wageDto.getBankNumber()));
        WageResponse wageResponse = sendPaymentWageRequest(wageDto, transaction);

        if(wageResponse.isSuccess()) {
            transaction.setStatus(TransactionStatus.SUCCESS);
        } else {
            transaction.setStatus(TransactionStatus.FAILED);
        }
        transactionRepository.save(transaction);
        log.info("Transaction " + transaction.getId() + " status is changed.");

        return wageResponse;
    }

    private String getPspPaymentUrl(Transaction transaction, String merchantId, String currency, boolean isPossibleSubscription) throws URISyntaxException {
        //RestTemplate restTemplate = new RestTemplate();
        final String url = HTTPS_PREFIX + this.pspBackHost + ":" + this.pspBackPort + this.pspBackPaymentUrl;
        URI uri = new URI(url);

        RequestPaymentDTO requestPaymentDto = new RequestPaymentDTO();
        requestPaymentDto.setMerchantId(merchantId);
        requestPaymentDto.setTransactionId(transaction.getId());
        requestPaymentDto.setAmount(transaction.getAmount());
        requestPaymentDto.setTimestamp(transaction.getTimestamp());
        requestPaymentDto.setCurrency(currency);
        requestPaymentDto.setPossibleSubscription(isPossibleSubscription);

        ResponseEntity<String> result = restTemplate.postForEntity(uri, requestPaymentDto, String.class);
        return result.getBody();
    }

    private WageResponse sendPaymentWageRequest(WageDto wageDto, Transaction transaction) throws URISyntaxException {
        //RestTemplate restTemplate = new RestTemplate();
        final String url = HTTPS_PREFIX + this.pspBackHost + ":" + this.pspBackPort + this.pspBackPaymentWageUrl;
        URI uri = new URI(url);

        WageRequest wageRequest = new WageRequest();
        wageRequest.setAccountNumber(wageDto.getAccountNumber());
        wageRequest.setBankNumber(wageDto.getBankNumber());
        wageRequest.setAmount(wageDto.getAmount());
        wageRequest.setCurrency(wageDto.getCurrency());
        wageRequest.setTimestamp(transaction.getTimestamp());
        wageRequest.setMerchantId(wageDto.getWebShopId());
        wageRequest.setTransactionId(transaction.getId());

        ResponseEntity<WageResponse> result = restTemplate.postForEntity(uri, wageRequest, WageResponse.class);
        return result.getBody();
    }

    private String encrypt(String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(this.encryptionVector.getBytes("UTF-8"));
            SecretKeySpec keySpec = new SecretKeySpec(this.encryptionKey.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.getEncoder()
                    .encodeToString(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
