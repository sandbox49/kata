package com.kata.estore.infra.rest.dto;

import com.kata.estore.domain.model.CustomerType;

import java.math.BigDecimal;
import java.util.UUID;

public record CustomerDto(UUID customerId,
                          CustomerType customerType,
                          String socialRaison,
                          String sirenCode,
                          BigDecimal turnover,
                          String vatNb,
                          String firstName,
                          String lastName) {
}
