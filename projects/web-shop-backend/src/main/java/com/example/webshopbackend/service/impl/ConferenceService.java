package com.example.webshopbackend.service.impl;

import com.example.webshopbackend.dto.ItemToPurchaseDto;
import com.example.webshopbackend.mapper.ConferenceMapper;
import com.example.webshopbackend.model.Conference;
import com.example.webshopbackend.model.ItemToPurchase;
import com.example.webshopbackend.model.ShoppingCart;
import com.example.webshopbackend.model.enums.TransactionStatus;
import com.example.webshopbackend.repository.ConferenceRepository;
import com.example.webshopbackend.repository.ShoppingCartRepository;
import com.example.webshopbackend.service.IConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ConferenceService implements IConferenceService {

    private final ConferenceRepository conferenceRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    @Autowired
    public ConferenceService(ConferenceRepository conferenceRepository, ShoppingCartRepository shoppingCartRepository) {
        this.conferenceRepository = conferenceRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public List<Conference> findAllByWebShopId(String id) {
        return conferenceRepository.findAllByWebShopId(id);
    }

    @Override
    public Set<ItemToPurchaseDto> findAllPayedConferencesByUserId(String id) {
        Set<ItemToPurchaseDto> conferencesResult = new HashSet<>();
        List<ShoppingCart> userShoppingCarts = shoppingCartRepository.findShoppingCartsForUser(id);
        for(ShoppingCart shoppingCart : userShoppingCarts) {
            if(shoppingCart.getTransaction().getStatus().equals(TransactionStatus.SUCCESS)) {
                for(ItemToPurchase item : shoppingCart.getItems()) {
                    ItemToPurchaseDto dto = new ItemToPurchaseDto();
                    dto.setProductId(item.getId());
                    dto.setConferenceDto(ConferenceMapper.convertToDto(this.findById(item.getProduct().getId())));
                    dto.setQuantity(item.getQuantity());
                    dto.setDate(shoppingCart.getCreateDate());
                    conferencesResult.add(dto);
                }
            }
        }
        return conferencesResult;
    }

    @Override
    public Conference findById(String id) {
        return conferenceRepository.findById(id).orElse(null);
    }
}
