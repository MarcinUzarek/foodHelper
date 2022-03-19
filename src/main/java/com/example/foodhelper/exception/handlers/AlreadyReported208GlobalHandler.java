package com.example.foodhelper.exception.handlers;

import com.example.foodhelper.exception.custom.EmailAlreadyExistsException;
import com.example.foodhelper.exception.custom.ItemDuplicateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.ZonedDateTime;

@ControllerAdvice
@Slf4j
public class AlreadyReported208GlobalHandler {

    private final HttpStatus ALREADY_REPORTED = HttpStatus.ALREADY_REPORTED;

    @ExceptionHandler({ItemDuplicateException.class,
            EmailAlreadyExistsException.class})
    @ResponseBody
    public ResponseEntity<ExceptionDetails> handleDuplicateInCollection(Exception e) {
        log.info("Resource Duplicate: " + e);
        return new ResponseEntity<>(new ExceptionDetails(ZonedDateTime.now(),
                ALREADY_REPORTED, e.getMessage()), ALREADY_REPORTED);
    }


}
