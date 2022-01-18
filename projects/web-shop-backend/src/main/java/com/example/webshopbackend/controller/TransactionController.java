package com.example.webshopbackend.controller;

import com.example.webshopbackend.dto.ItemToPurchaseDto;
import com.example.webshopbackend.dto.ShoppingCartDto;
import com.example.webshopbackend.dto.TransactionDto;
import com.example.webshopbackend.mapper.ProductMapper;
import com.example.webshopbackend.mapper.ShoppingCartMapper;
import com.example.webshopbackend.model.ItemToPurchase;
import com.example.webshopbackend.model.ShoppingCart;
import com.example.webshopbackend.model.Transaction;
import com.example.webshopbackend.model.enums.TransactionStatus;
import com.example.webshopbackend.service.IProductService;
import com.example.webshopbackend.service.ITransactionService;
import com.example.webshopbackend.service.impl.ProductService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "api/transactions")
public class TransactionController {

    protected final Log log = LogFactory.getLog(getClass());

    @Autowired
    private ITransactionService transactionService;

    @Autowired
    private IProductService productService;

    @GetMapping(value="/{id}/shoppingCart", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ShoppingCartDto> getShoppingCartForTransaction(@PathVariable String id) {
        ShoppingCart shoppingCart = transactionService.getShoppingCartForTransaction(id);
        if(shoppingCart == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ShoppingCartDto dto = ShoppingCartMapper.convertToDto(shoppingCart);

        List<ItemToPurchaseDto> itemsToPurchase = new ArrayList<>();
        for(ItemToPurchase item : shoppingCart.getItems()) {
            ItemToPurchaseDto itemDto = new ItemToPurchaseDto();
            itemDto.setProductDto(ProductMapper.convertToDto(item.getProduct()));
            itemDto.setQuantity(item.getQuantity());
            itemsToPurchase.add(itemDto);
        }
        dto.setItemsToPurchase(itemsToPurchase);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping(value="/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> handleTransactionRequest(@PathVariable String id, @RequestBody TransactionDto transactionDto) {
        Transaction transaction = transactionService.getById(id);

        if(transaction != null) {
            if(transactionDto.getStatus() == 1) {
                transaction.setStatus(TransactionStatus.SUCCESS);
                transactionService.changeAvailableBalance(transaction.getId());
            } else if (transactionDto.getStatus() == 2) {
                transaction.setStatus(TransactionStatus.FAILED);
            } else if (transactionDto.getStatus() == 3) {
                transaction.setStatus(TransactionStatus.ERROR);
            }

            log.info("Transaction (id:" + id + ") status was changed.");
            transactionService.save(transaction);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
