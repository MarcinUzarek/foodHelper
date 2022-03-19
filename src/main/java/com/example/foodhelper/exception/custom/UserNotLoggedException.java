package com.example.foodhelper.exception.custom;

public class UserNotLoggedException extends RuntimeException {
    public UserNotLoggedException() {
        super("You have to log into the system");
    }
}
