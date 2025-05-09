package com.kata.estore.domain.model;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.function.Function;

@AllArgsConstructor
public enum ItemType {

    HIGH_END_PHONE(createPricing(
            new BigDecimal(1500),
            new BigDecimal(1000),
            new BigDecimal(1150)
    )),
    MID_RANGE_PHONE(createPricing(
            new BigDecimal(800),
            new BigDecimal(550),
            new BigDecimal(600)
    )),
    LAPTOP(createPricing(
            new BigDecimal(1200),
            new BigDecimal(900),
            new BigDecimal(1000)
    ));

    private final Function<Customer, BigDecimal> pricingFunction;

    public BigDecimal getPrice(Customer customer) {
        return pricingFunction.apply(customer);
    }

    private static Function<Customer, BigDecimal> createPricing(BigDecimal physicalPrice,
                                                                BigDecimal moralBigTurnoverPrice,
                                                                BigDecimal moralSmallTurnoverPrice) {
        return customer -> switch (customer) {
            case PhysicalCustomer p -> physicalPrice;
            case MoralCustomer m when m.getTurnover().compareTo(new BigDecimal(1_000_000)) >= 0 ->
                    moralBigTurnoverPrice;
            case MoralCustomer m -> moralSmallTurnoverPrice;
            default -> throw new IllegalArgumentException("Unknown customer type: " + customer);
        };
    }
}
