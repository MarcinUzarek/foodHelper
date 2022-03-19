package com.example.foodhelper.exception.handlers;

import com.example.foodhelper.exception.custom.EmailAlreadyExistsException;
import com.example.foodhelper.exception.custom.ItemDuplicateException;
import com.example.foodhelper.exception.custom.UserNotLoggedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.ZonedDateTime;

@ControllerAdvice
@Slf4j
public class Conflict409GlobalHandler {

    private final HttpStatus CONFLICT = HttpStatus.CONFLICT;

    @ExceptionHandler(ItemDuplicateException.class)
    @ResponseBody
    public ResponseEntity<ExceptionDetails> handleItemDuplicate(ItemDuplicateException e) {

        var exc = new ExceptionDetails(ZonedDateTime.now(),
                CONFLICT, e.getMessage());
        return new ResponseEntity<>(exc, CONFLICT);
    }

    @ExceptionHandler(UserNotLoggedException.class)
    @ResponseBody
    public ResponseEntity<ExceptionDetails> handleUserNotLogged(UserNotLoggedException e) {
        return new ResponseEntity<>(new ExceptionDetails(ZonedDateTime.now(),
                CONFLICT, e.getMessage()), CONFLICT);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseBody
    public ResponseEntity<ExceptionDetails> handleEmailAlreadyExists(EmailAlreadyExistsException e) {
        log.info("Email duplicate" + e);
        return new ResponseEntity<>(new ExceptionDetails(ZonedDateTime.now(),
                CONFLICT, e.getMessage()), CONFLICT);
    }
}
