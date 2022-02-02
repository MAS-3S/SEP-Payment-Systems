package com.example.webshopbackend.controller;

import com.example.webshopbackend.dto.UppDto;
import com.example.webshopbackend.dto.UppOnlineConferenceDTO;
import com.example.webshopbackend.model.*;
import com.example.webshopbackend.model.enums.Currency;
import com.example.webshopbackend.model.enums.TransactionStatus;
import com.example.webshopbackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RestController
@RequestMapping(value = "api/upp")
public class UppController {

    private final ConferenceRepository conferenceRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ItemToPurchaseRepository itemToPurchaseRepository;
    private final TransactionRepository transactionRepository;
    private final AccommodationRepository accommodationRepository;
    private final TransportRepository transportRepository;

    @Autowired
    public UppController(ConferenceRepository conferenceRepository, ShoppingCartRepository shoppingCartRepository, ItemToPurchaseRepository itemToPurchaseRepository, TransactionRepository transactionRepository, AccommodationRepository accommodationRepository, TransportRepository transportRepository) {
        this.conferenceRepository = conferenceRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.itemToPurchaseRepository = itemToPurchaseRepository;
        this.transactionRepository = transactionRepository;
        this.accommodationRepository = accommodationRepository;
        this.transportRepository = transportRepository;
    }

    @PostMapping("/pay")
    public ResponseEntity<UppDto> pay(@RequestBody UppDto dto) {
        UppDto result = new UppDto();
        result.setPrice(dto.getPrice());

        if(dto.getPrice() < 500.0) {
            result.setSuccess(true);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/payOnlineConference")
    public ResponseEntity<UppDto> payOnlineConference(@RequestBody UppOnlineConferenceDTO dto) {
        UppDto result = new UppDto();
        result.setPrice(dto.getPrice());

        Conference conference = new Conference();
        conference.setCourse(dto.isCourse());
        conference.setAddress("");
        conference.setStartTime(Date.from(dto.getTime().atZone(ZoneId.systemDefault()).toInstant()));
        conference.setEndTime(Date.from(dto.getTime().atZone(ZoneId.systemDefault()).toInstant()));
        conference.setSubscription(false);
        conference.setWebShop(null);
        conference.setAvailableBalance(1);
        conference.setCurrency(Currency.USD);
        if (dto.isCourse()) {
            conference.setDescription("Online course");
        } else {
            conference.setDescription("Online conference");
        }
        conference.setImage("");
        conference.setName(dto.getName());
        conference.setPrice(dto.getPrice());
        conferenceRepository.save(conference);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setWebShop(null);
        shoppingCart.setCreateDate(LocalDateTime.now());
        shoppingCart.setUser(null);
        shoppingCart.setTotalPrice(dto.getPrice());
        shoppingCartRepository.save(shoppingCart);

        ItemToPurchase itemToPurchase = new ItemToPurchase();
        itemToPurchase.setShoppingCart(shoppingCart);
        itemToPurchase.setProduct(conference);
        itemToPurchase.setQuantity(1);
        itemToPurchaseRepository.save(itemToPurchase);

        Transaction transaction = new Transaction();
        transaction.setAmount(shoppingCart.getTotalPrice());
        transaction.setCurrency(Currency.USD.toString());
        transaction.setTimestamp(shoppingCart.getCreateDate());
        transaction.setStatus(TransactionStatus.IN_PROGRESS);
        transaction.setShoppingCart(shoppingCart);
        transactionRepository.save(transaction);

        result.setSuccess(true);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/payLiveConference")
    public ResponseEntity<UppDto> payLiveConference(@RequestBody UppOnlineConferenceDTO dto) {
        UppDto result = new UppDto();
        result.setPrice(dto.getPrice());

        Conference conference = new Conference();
        conference.setCourse(dto.isCourse());
        conference.setAddress("");
        conference.setStartTime(Date.from(dto.getTime().atZone(ZoneId.systemDefault()).toInstant()));
        conference.setEndTime(Date.from(dto.getTime().atZone(ZoneId.systemDefault()).toInstant()));
        conference.setSubscription(false);
        conference.setWebShop(null);
        conference.setAvailableBalance(1);
        conference.setCurrency(Currency.USD);
        if (dto.isCourse()) {
            conference.setDescription("Online course");
        } else {
            conference.setDescription("Online conference");
        }
        conference.setImage("");
        conference.setName(dto.getName());
        conference.setPrice(dto.getPrice());
        conferenceRepository.save(conference);

        Transport transport = new Transport();
        transport.setCurrency(Currency.USD);
        transport.setName(dto.getTransport_name());
        transport.setPrice(dto.getTransport_price());
        transportRepository.save(transport);

        Accommodation accommodation = new Accommodation();
        accommodation.setAddress(dto.getAccommodation_address());
        accommodation.setDays(dto.getAccommodation_days());
        accommodation.setWebShop(null);
        accommodation.setCurrency(Currency.USD);
        accommodation.setDescription("Accommodation for live conference");
        accommodation.setNumberOfBeds(1);
        accommodation.setStartDate(Date.from(dto.getTime().atZone(ZoneId.systemDefault()).toInstant()));
        accommodation.setImage("");
        accommodation.setAvailableBalance(1);
        accommodation.setName(dto.getAccommodation_name());
        accommodation.setPrice(dto.getAccommodation_price());
        accommodation.setTransport(transport);
        transport.setAccommodation(accommodation);
        accommodationRepository.save(accommodation);


        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setWebShop(null);
        shoppingCart.setCreateDate(LocalDateTime.now());
        shoppingCart.setUser(null);
        shoppingCart.setTotalPrice(dto.getPrice());
        shoppingCartRepository.save(shoppingCart);

        ItemToPurchase itemToPurchase = new ItemToPurchase();
        itemToPurchase.setShoppingCart(shoppingCart);
        itemToPurchase.setProduct(conference);
        itemToPurchase.setQuantity(1);
        itemToPurchaseRepository.save(itemToPurchase);

        ItemToPurchase itemToPurchaseAcc = new ItemToPurchase();
        itemToPurchase.setShoppingCart(shoppingCart);
        itemToPurchase.setProduct(accommodation);
        itemToPurchase.setQuantity(1);
        itemToPurchaseRepository.save(itemToPurchaseAcc);


        Transaction transaction = new Transaction();
        transaction.setAmount(shoppingCart.getTotalPrice());
        transaction.setCurrency(Currency.USD.toString());
        transaction.setTimestamp(shoppingCart.getCreateDate());
        transaction.setStatus(TransactionStatus.IN_PROGRESS);
        transaction.setShoppingCart(shoppingCart);
        transactionRepository.save(transaction);

        result.setSuccess(true);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<UppDto> test() {
        UppDto result = new UppDto();
        result.setPrice(500.0);
        result.setSuccess(true);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
