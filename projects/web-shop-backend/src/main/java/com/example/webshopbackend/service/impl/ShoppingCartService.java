package com.example.webshopbackend.service.impl;

import com.example.webshopbackend.dto.ItemToPurchaseDto;
import com.example.webshopbackend.dto.PaymentDto;
import com.example.webshopbackend.dto.RequestPaymentDTO;
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

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class ShoppingCartService implements IShoppingCartService {

    protected final Log log = LogFactory.getLog(getClass());
    private static final String HTTP_PREFIX = "http://";
    private static final String HTTPS_PREFIX = "https://";

//    @Autowired
//    RestTemplate restTemplate;

    @Value("${pspback.port}")
    private String pspBackPort;
    @Value("${pspback.host}")
    private String pspBackHost;
    @Value("${pspback.paymentUrl.route}")
    private String pspBackPaymentUrl;

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

    private String getPspPaymentUrl(Transaction transaction, String merchantId, String currency, boolean isPossibleSubscription) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
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
}
