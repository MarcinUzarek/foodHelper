package com.example.foodhelper.exception.custom;

public class RoleNotExistsException extends RuntimeException{

    public RoleNotExistsException(String role) {
        super("Such role doesn't exist: " + role);
    }
}
