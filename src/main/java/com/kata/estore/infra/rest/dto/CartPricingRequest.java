package com.kata.estore.infra.rest.dto;

import com.kata.estore.domain.model.ItemType;
import com.kata.estore.infra.rest.validation.ValidCartRequest;

import java.util.Map;

@ValidCartRequest
public record CartPricingRequest(
        CustomerDto customer,
        Map<ItemType, ItemRequestDto> items
) {
}
