package com.example.foodhelper.exception.custom;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException() {
        super("Account with this email already exists");
    }
}
