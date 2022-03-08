package com.example.foodhelper.exception;

public class DifferentPasswordsException extends RuntimeException {

    public DifferentPasswordsException(String message) {
        super(message);
    }
}
