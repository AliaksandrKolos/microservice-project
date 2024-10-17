package com.kolos.resourceservice.web;

import com.kolos.resourceservice.service.exception.UnsupportedTypeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public void serverError(Exception e) {
        log.error("Server error: ", e);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public void notFound(NoSuchElementException e) {
        log.info("Not found: ", e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnsupportedTypeException.class)
    public void badRequest(UnsupportedTypeException e) {
        log.info("Unsupported media type: " + e.getMessage());
    }
}



