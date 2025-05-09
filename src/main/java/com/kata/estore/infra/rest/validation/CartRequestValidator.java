package com.kata.estore.infra.rest.validation;

import com.kata.estore.domain.model.CustomerType;
import com.kata.estore.infra.rest.dto.CartPricingRequest;
import com.kata.estore.infra.rest.dto.CustomerDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static com.kata.estore.application.exception.CartError.*;

public class CartRequestValidator implements ConstraintValidator<ValidCartRequest, CartPricingRequest> {

    private static final Pattern TVA_PATTERN = Pattern.compile("^[A-Z]{2}\\d{2}[A-Z0-9]{1,30}$");

    @Override
    public boolean isValid(CartPricingRequest request, ConstraintValidatorContext context) {
        if (request == null) return true;

        var customer = request.customer();

        if (!validateField("Customer", customer, context, Objects::nonNull)) {
            return false;
        }

        boolean isValid = validateField("Customer Id", customer.customerId(), context, Objects::nonNull)
                && validateField("Customer Type", customer.customerType(), context, Objects::nonNull);

        if (customer.customerType() == CustomerType.MORAL) {
            isValid &= validateMoralCustomer(customer, context);
        } else if (customer.customerType() == CustomerType.PHYSICAL) {
            isValid &= validatePhysicalCustomer(customer, context);
        }

        return isValid;
    }

    private boolean validateMoralCustomer(CustomerDto customer, ConstraintValidatorContext context) {
        boolean isValid = validateField("Social Raison", customer.socialRaison(), context, value -> value != null && !value.isBlank());
        isValid &= validateField("SIREN Code", customer.sirenCode(), context, value -> value != null && !value.isBlank());
        isValid &= validateField("Turnover", customer.turnover(), context, value -> value != null && value.compareTo(BigDecimal.ZERO) > 0);
        isValid &= validateVatNumber(customer.vatNb(), context);
        return isValid;
    }

    private boolean validatePhysicalCustomer(CustomerDto customer, ConstraintValidatorContext context) {
        boolean isValid = validateField("First Name", customer.firstName(), context, value -> value != null && !value.isBlank());
        isValid &= validateField("Last Name", customer.lastName(), context, value -> value != null && !value.isBlank());
        return isValid;
    }

    private boolean validateVatNumber(String vatNb, ConstraintValidatorContext context) {
        if (vatNb != null && !vatNb.isBlank() && !TVA_PATTERN.matcher(vatNb).matches()) {
            addConstraintViolation(context, NOT_VALID_VTA.getMessage(), "VTA");
            return false;
        }
        return true;
    }

    private <T> boolean validateField(String fieldName, T fieldValue, ConstraintValidatorContext context, Predicate<T> predicate) {
        if (!predicate.test(fieldValue)) {
            addConstraintViolation(context, MISSING_MANDATORY_FIELD.getMessage(fieldName), fieldName);
            return false;
        }
        return true;
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String message, String fieldName) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(fieldName)
                .addConstraintViolation();
    }
}