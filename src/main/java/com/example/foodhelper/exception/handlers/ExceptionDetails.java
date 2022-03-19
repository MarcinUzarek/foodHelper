package com.example.foodhelper.exception.handlers;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
class ExceptionDetails {

    private final ZonedDateTime timestamp;
    private final HttpStatus status;
    private final String message;

    public ExceptionDetails(ZonedDateTime timestamp, HttpStatus status, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
    }
}
