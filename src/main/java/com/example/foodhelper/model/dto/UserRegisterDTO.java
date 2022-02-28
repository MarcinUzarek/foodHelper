package com.example.foodhelper.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRegisterDTO {

    private String name;
    private String email;
    private String password;
}
