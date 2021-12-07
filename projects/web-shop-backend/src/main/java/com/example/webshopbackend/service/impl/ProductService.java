package com.example.webshopbackend.service.impl;

import com.example.webshopbackend.dto.ItemToPurchaseDto;
import com.example.webshopbackend.mapper.ProductMapper;
import com.example.webshopbackend.model.ItemToPurchase;
import com.example.webshopbackend.model.Product;
import com.example.webshopbackend.model.ShoppingCart;
import com.example.webshopbackend.model.enums.TransactionStatus;
import com.example.webshopbackend.repository.ProductRepository;
import com.example.webshopbackend.repository.ShoppingCartRepository;
import com.example.webshopbackend.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ShoppingCartRepository shoppingCartRepository) {
        this.productRepository = productRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public List<Product> findAllByWebShopId(String id) {
        return productRepository.findAllByWebShopId(id);
    }

    @Override
    public Set<ItemToPurchaseDto> findAllPayedProductsByUserId(String id) {
        Set<ItemToPurchaseDto> productsResult = new HashSet<>();
        List<ShoppingCart> userShoppingCarts = shoppingCartRepository.findShoppingCartsForUser(id);
        for(ShoppingCart shoppingCart : userShoppingCarts) {
            if(shoppingCart.getTransaction().getStatus().equals(TransactionStatus.SUCCESS)) {
                for(ItemToPurchase item : shoppingCart.getItems()) {
                    ItemToPurchaseDto dto = new ItemToPurchaseDto();
                    dto.setProductId(item.getId());
                    dto.setProductDto(ProductMapper.convertToDto(item.getProduct()));
                    dto.setQuantity(item.getQuantity());
                    dto.setDate(shoppingCart.getCreateDate());
                    productsResult.add(dto);
                }
            }
        }
        return productsResult;
    }
}
