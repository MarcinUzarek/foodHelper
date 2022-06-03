package com.example.foodhelper.exception.handlers;

import com.example.foodhelper.exception.custom.IntoleranceNotFoundException;
import com.example.foodhelper.exception.custom.RoleNotExistsException;
import com.example.foodhelper.exception.custom.UserNotFoundException;
import com.example.foodhelper.exception.custom.WrongCredentialsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

import java.time.ZonedDateTime;

@ControllerAdvice
@Slf4j
public class NotFound404GlobalHandler {

    private final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;

    @ExceptionHandler({UserNotFoundException.class,
            IntoleranceNotFoundException.class,
            RoleNotExistsException.class})
    @ResponseBody
    public ResponseEntity<ExceptionDetails> handleResourceNotFound(Exception e) {
        log.info("An error occurred: " + e.getMessage());
        return new ResponseEntity<>(new ExceptionDetails(ZonedDateTime.now(),
                NOT_FOUND, e.getMessage()), NOT_FOUND);
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    @ResponseBody
    public ResponseEntity<ExceptionDetails> handleClientNotFound(Exception e) {

        return new ResponseEntity<>(new ExceptionDetails(ZonedDateTime.now(),
                NOT_FOUND, "Recipe with this ID does not exist"), NOT_FOUND);
    }


}
