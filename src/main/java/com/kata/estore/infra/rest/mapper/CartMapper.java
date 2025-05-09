package com.kata.estore.infra.rest.mapper;

import com.kata.estore.domain.model.Cart;
import com.kata.estore.infra.rest.dto.CartPricingRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CustomerMapper.class)
public interface CartMapper {
    @Mapping(source = "customer", target = "customer")
    Cart toEntity(CartPricingRequest request);
}
