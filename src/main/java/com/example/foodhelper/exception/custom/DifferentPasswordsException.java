package com.example.foodhelper.exception.custom;

public class DifferentPasswordsException extends RuntimeException {

    public DifferentPasswordsException() {
        super("Passwords are not the same");
    }
}
