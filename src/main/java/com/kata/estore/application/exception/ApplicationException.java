package com.kata.estore.application.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

    private final ApplicationError error;

    public ApplicationException(ApplicationError error, Object... args) {
        super(error.getMessage(args));
        this.error = error;
    }
}
