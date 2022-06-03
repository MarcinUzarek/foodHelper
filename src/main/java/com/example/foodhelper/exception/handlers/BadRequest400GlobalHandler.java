package com.example.foodhelper.exception.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.ZonedDateTime;

@ControllerAdvice
@Slf4j
public class BadRequest400GlobalHandler {

    private final HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    @ResponseBody
    public ResponseEntity<ExceptionDetails> handleHttpClientException(HttpClientErrorException e) {
        log.info("Http Client error: " + e.toString());
        return new ResponseEntity<>(new ExceptionDetails(ZonedDateTime.now(),
                BAD_REQUEST, "Error in request body syntax"), BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public ResponseEntity<ExceptionDetails> handleArgumentMismatch(MethodArgumentTypeMismatchException e) {
        return new ResponseEntity<>(new ExceptionDetails(ZonedDateTime.now(),
                BAD_REQUEST, "Error in request parameter syntax"), BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseEntity<ExceptionDetails> handleArgumentMismatch(HttpMessageNotReadableException e) {
        return new ResponseEntity<>(new ExceptionDetails(ZonedDateTime.now(),
                BAD_REQUEST, "Error in JSON body syntax"), BAD_REQUEST);
    }

}
