package com.example.foodhelper.exception.custom;

public class IntoleranceNotFoundException extends RuntimeException {

    public IntoleranceNotFoundException(Long id) {
        super("No Intolerance with such id: " + id);
    }
}
