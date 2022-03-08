package com.example.foodhelper.model.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class ResetPasswordDTO {

    @Size(min = 3, max = 80, message = "Password should be from 3 to 80 characters long")
    private String password;
    private String confirmPassword;
    private String token;


}
