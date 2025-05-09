package com.kata.estore.application.services;

import com.kata.estore.domain.model.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartService {

    public BigDecimal priceCart(Cart cart) {
        return cart.getItems().entrySet().stream()
                .map(entry -> entry.getValue().getType().getPrice(cart.getCustomer())
                        .multiply(BigDecimal.valueOf(entry.getValue().getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
