package com.example.foodhelper.exception;

public class WrongCredentialsException extends RuntimeException{

    public WrongCredentialsException(String message) {
        super(message);
    }
}
