package com.kata.estore.infra.rest.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CartRequestValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCartRequest {
    String message() default "Invalid cart request";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}