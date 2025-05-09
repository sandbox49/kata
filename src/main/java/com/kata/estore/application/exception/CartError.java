package com.kata.estore.application.exception;

import lombok.AllArgsConstructor;

import java.text.MessageFormat;

@AllArgsConstructor
public enum CartError implements ApplicationError {
    NOT_VALID_VTA("Invalid VTA Number"),
    MISSING_MANDATORY_FIELD("Mandatory field {0} is missing");

    private final String pattern;


    @Override
    public String getMessage(Object... args) {
        return MessageFormat.format(pattern, args);
    }
}
