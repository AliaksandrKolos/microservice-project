package com.kolos.resourceservice.web;

import com.kolos.resourceservice.service.exception.InvalidInputException;
import com.kolos.resourceservice.service.exception.UnsupportedTypeException;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {


    @ExceptionHandler
    public ResponseEntity<Map<String, String>>invalidInputException(InvalidInputException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnsupportedTypeException.class)
    public void badRequest(UnsupportedTypeException e) {
        log.info("Unsupported media type: {}", e.getMessage());
    }

    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    @ExceptionHandler(RequestNotPermitted.class)
    public String notPermitted(RequestNotPermitted e) {
        return "Too many requests go home.";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UnknownHostException.class)
    public String hostException(UnknownHostException e) {
        return "Unknown host: " + e.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public void notFound(NoSuchElementException e) {
        log.info("Not found: ", e);
    }
}



