package com.kata.estore.infra.rest;


import com.kata.estore.application.exception.ApplicationException;
import com.kata.estore.application.exception.CartError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GeneralExceptionHandler {


    @ExceptionHandler(ApplicationException.class)
    @ResponseBody
    public ResponseEntity<?> handleException(ApplicationException e) {
        var responseStatus = switch (e.getError()) {
            case CartError cartError -> HttpStatus.BAD_REQUEST;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
        return new ResponseEntity<>(
                e.getMessage(),
                responseStatus);
    }
}
