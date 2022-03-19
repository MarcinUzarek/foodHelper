package com.example.foodhelper.exception.custom;

public class WrongCredentialsException extends RuntimeException{

    public WrongCredentialsException() {
        super("Wrong Credentials");
    }
}
