package com.example.foodhelper.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
class ExceptionDetails {

    private final String message;
    private final HttpStatus status;
    private final ZonedDateTime timestamp;

    ExceptionDetails(String message, HttpStatus status, ZonedDateTime timestamp) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
    }
}
