package com.example.foodhelper.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;


@ControllerAdvice()
public class GlobalExceptionHandler {

    @ExceptionHandler(ItemDuplicateException.class)
    public String handleException() {
        return "redirect:/my-account";
    }

}
