package com.kata.estore.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;


@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class MoralCustomer extends Customer {

    private String socialRaison;
    private String vatNb;
    private String sirenCode;
    private BigDecimal turnover;
}
