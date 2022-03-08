package com.example.foodhelper.exception;

public class ItemDuplicateException extends RuntimeException{

    public ItemDuplicateException(String message) {
        super(message);
    }
}
