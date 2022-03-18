package com.example.foodhelper.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.ZonedDateTime;


@ControllerAdvice()
public class GlobalExceptionHandler {


    @ExceptionHandler(ItemDuplicateException.class)
    @ResponseBody
    public ResponseEntity<ExceptionDetails> handleItemDuplicate(ItemDuplicateException e) {
        var exc = new ExceptionDetails(e.getMessage(),
                HttpStatus.CONFLICT, ZonedDateTime.now());
        return new ResponseEntity<>(exc, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotLoggedException.class)
    @ResponseBody
    public ResponseEntity<ExceptionDetails> handleUserNotLogged(UserNotLoggedException e) {
        var exc = new ExceptionDetails(e.getMessage(),
                HttpStatus.CONFLICT, ZonedDateTime.now());
        return new ResponseEntity<>(exc, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseBody
    public ResponseEntity<ExceptionDetails> handleEmailAlreadyExists(EmailAlreadyExistsException e) {
        var exc = new ExceptionDetails(e.getMessage(),
                HttpStatus.CONFLICT, ZonedDateTime.now());
        return new ResponseEntity<>(exc, HttpStatus.CONFLICT);
    }





}
