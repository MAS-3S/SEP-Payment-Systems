package com.example.webshopbackend.service.impl;

import com.example.webshopbackend.dto.ItemToPurchaseDto;
import com.example.webshopbackend.mapper.AccommodationMapper;
import com.example.webshopbackend.model.Accommodation;
import com.example.webshopbackend.model.ItemToPurchase;
import com.example.webshopbackend.model.ShoppingCart;
import com.example.webshopbackend.model.enums.TransactionStatus;
import com.example.webshopbackend.repository.AccommodationRepository;
import com.example.webshopbackend.repository.ShoppingCartRepository;
import com.example.webshopbackend.service.IAccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AccommodationService implements IAccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    @Autowired
    public AccommodationService(AccommodationRepository accommodationRepository, ShoppingCartRepository shoppingCartRepository) {
        this.accommodationRepository = accommodationRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public List<Accommodation> findAllByWebShopId(String id) {
        return accommodationRepository.findAllByWebShopId(id);
    }

    @Override
    public Set<ItemToPurchaseDto> findAllPayedAccommodationsByUserId(String id) {
        Set<ItemToPurchaseDto> accommodationsResult = new HashSet<>();
        List<ShoppingCart> userShoppingCarts = shoppingCartRepository.findShoppingCartsForUser(id);
        for(ShoppingCart shoppingCart : userShoppingCarts) {
            if(shoppingCart.getTransaction().getStatus().equals(TransactionStatus.SUCCESS)) {
                for(ItemToPurchase item : shoppingCart.getItems()) {
                    ItemToPurchaseDto dto = new ItemToPurchaseDto();
                    dto.setProductId(item.getId());
                    dto.setAccommodationDto(AccommodationMapper.convertToDto(this.findById(item.getProduct().getId())));
                    dto.setQuantity(item.getQuantity());
                    dto.setDate(shoppingCart.getCreateDate());
                    accommodationsResult.add(dto);
                }
            }
        }
        return accommodationsResult;
    }

    @Override
    public Accommodation findById(String id) {
        return accommodationRepository.findById(id).orElse(null);
    }
}
