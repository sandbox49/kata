package com.kata.estore.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cart {
    private Customer customer;
    private Map<ItemType, Item> items;
}
