package com.example.foodhelper.exception.handlers;

import com.example.foodhelper.exception.custom.UserNotLoggedException;
import com.example.foodhelper.exception.custom.WrongCredentialsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.ZonedDateTime;


@ControllerAdvice
@Slf4j
public class Forbidden403GlobalHandler {

    private final HttpStatus FORBIDDEN = HttpStatus.FORBIDDEN;

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ResponseEntity<ExceptionDetails> handleAccessDenied(AccessDeniedException e) {
        var exc = new ExceptionDetails(ZonedDateTime.now(),
                FORBIDDEN, "Api access denied");

        log.warn("Someone was trying to log without permission: " + e);
        return new ResponseEntity<>(exc, FORBIDDEN);
    }

    @ExceptionHandler({UserNotLoggedException.class,
            WrongCredentialsException.class})
    @ResponseBody
    public ResponseEntity<ExceptionDetails> handleAuthenticationProblems(Exception e) {
        return new ResponseEntity<>(new ExceptionDetails(ZonedDateTime.now(),
                FORBIDDEN, e.getMessage()), FORBIDDEN);
    }
}
