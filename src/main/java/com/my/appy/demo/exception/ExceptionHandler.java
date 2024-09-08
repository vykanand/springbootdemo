package com.my.appy.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExceptionHandler extends RuntimeException {
    public ExceptionHandler(String message) {
        super(message);
    }
}
