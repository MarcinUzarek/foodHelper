package com.example.foodhelper.exception.custom;

public class ItemDuplicateException extends RuntimeException{

    public ItemDuplicateException(String product) {
        super("You already have this item on your list: " + product);
    }
}
