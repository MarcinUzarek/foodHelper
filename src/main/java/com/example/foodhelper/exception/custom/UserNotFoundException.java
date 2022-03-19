package com.example.foodhelper.exception.custom;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(Long id) {
        super("User not found with such id: " + id);
    }
}
