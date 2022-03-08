package com.example.foodhelper.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;


@ControllerAdvice()
public class GlobalExceptionHandler {

    @ExceptionHandler(ItemDuplicateException.class)
    public String handleItemDuplicateException() {
        return "redirect:/my-account";
    }

    @ExceptionHandler(DifferentPasswordsException.class)
    public String handleDifferentPasswordException() {
        return "redirect:/new-pass";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralException() {
        return "error";
    }


}
