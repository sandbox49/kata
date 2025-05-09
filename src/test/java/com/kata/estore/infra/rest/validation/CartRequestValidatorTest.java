package com.kata.estore.infra.rest.validation;

import com.kata.estore.domain.model.CustomerType;
import com.kata.estore.infra.rest.dto.CartPricingRequest;
import com.kata.estore.infra.rest.dto.CustomerDto;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ConstraintValidatorContext.ConstraintViolationBuilder;
import jakarta.validation.ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CartRequestValidatorTest {

    private CartRequestValidator validator;
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        validator = new CartRequestValidator();
        context = Mockito.mock(ConstraintValidatorContext.class);
        ConstraintViolationBuilder violationBuilder = Mockito.mock(ConstraintViolationBuilder.class);
        NodeBuilderCustomizableContext nodeBuilder = Mockito.mock(NodeBuilderCustomizableContext.class);

        Mockito.when(context.buildConstraintViolationWithTemplate(ArgumentMatchers.anyString()))
                .thenReturn(violationBuilder);
        Mockito.when(violationBuilder.addPropertyNode(ArgumentMatchers.anyString()))
                .thenReturn(nodeBuilder);
        Mockito.when(nodeBuilder.addConstraintViolation())
                .thenReturn(context);
    }

    @Test
    void testValidMoralCustomer() {
        CustomerDto customer = new CustomerDto(
                UUID.randomUUID(),
                CustomerType.MORAL,
                "Valid Raison",
                "123456789",
                BigDecimal.valueOf(1000),
                "FR12345678901",
                null,
                null
        );
        CartPricingRequest request = new CartPricingRequest(customer, Map.of());

        assertTrue(validator.isValid(request, context));
        Mockito.verify(context, Mockito.never()).buildConstraintViolationWithTemplate(ArgumentMatchers.anyString());
    }

    @Test
    void testInvalidMoralCustomer() {
        CustomerDto customer = new CustomerDto(
                UUID.randomUUID(),
                CustomerType.MORAL,
                "",
                null,
                BigDecimal.ZERO,
                "INVALID_VAT",
                null,
                null
        );
        CartPricingRequest request = new CartPricingRequest(customer, Map.of());

        assertFalse(validator.isValid(request, context));
        Mockito.verify(context, Mockito.atLeastOnce()).buildConstraintViolationWithTemplate(ArgumentMatchers.anyString());
    }

    @Test
    void testValidPhysicalCustomer() {
        CustomerDto customer = new CustomerDto(
                UUID.randomUUID(),
                CustomerType.PHYSICAL,
                null,
                null,
                null,
                null,
                "John",
                "Doe"
        );
        CartPricingRequest request = new CartPricingRequest(customer, Map.of());

        assertTrue(validator.isValid(request, context));
        Mockito.verify(context, Mockito.never()).buildConstraintViolationWithTemplate(ArgumentMatchers.anyString());
    }

    @Test
    void testInvalidPhysicalCustomer() {
        CustomerDto customer = new CustomerDto(
                UUID.randomUUID(),
                CustomerType.PHYSICAL,
                null,
                null,
                null,
                null,
                "",
                ""
        );
        CartPricingRequest request = new CartPricingRequest(customer, Map.of());

        assertFalse(validator.isValid(request, context));
        Mockito.verify(context, Mockito.atLeastOnce()).buildConstraintViolationWithTemplate(ArgumentMatchers.anyString());
    }

    @Test
    void testNullCustomer() {
        CartPricingRequest request = new CartPricingRequest(null, Map.of());

        assertFalse(validator.isValid(request, context));
        Mockito.verify(context).buildConstraintViolationWithTemplate(ArgumentMatchers.anyString());
    }

    @Test
    void testNullCustomerType() {
        CustomerDto customer = new CustomerDto(
                UUID.randomUUID(),
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        CartPricingRequest request = new CartPricingRequest(customer, Map.of());

        assertFalse(validator.isValid(request, context));
        Mockito.verify(context).buildConstraintViolationWithTemplate(ArgumentMatchers.anyString());
    }

    @Test
    void testInvalidVatNumberForMoralCustomer() {
        CustomerDto customer = new CustomerDto(
                UUID.randomUUID(),
                CustomerType.MORAL,
                "Valid Raison",
                "123456789",
                BigDecimal.valueOf(1000),
                "INVALID_VAT",
                null,
                null
        );
        CartPricingRequest request = new CartPricingRequest(customer, Map.of());

        assertFalse(validator.isValid(request, context));
        Mockito.verify(context).buildConstraintViolationWithTemplate("Invalid VTA Number");
    }

    @Test
    void testNullRequest() {
        assertTrue(validator.isValid(null, context));
        Mockito.verify(context, Mockito.never()).buildConstraintViolationWithTemplate(ArgumentMatchers.anyString());
    }
}