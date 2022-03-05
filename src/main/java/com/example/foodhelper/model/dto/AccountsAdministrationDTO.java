package com.example.foodhelper.model.dto;

import com.example.foodhelper.model.Role;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

@Data
public class AccountsAdministrationDTO {

    private String name;
    private String email;
    private boolean isEnabled;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate creationTime;
    private Set<Role> roles;
    private String rolesString;
}

