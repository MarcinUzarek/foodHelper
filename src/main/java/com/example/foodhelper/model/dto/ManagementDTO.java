package com.example.foodhelper.model.dto;

import com.example.foodhelper.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

@Data
public class ManagementDTO {

    private Long id;
    private String name;
    private String email;
    private boolean isEnabled;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate creationTime;
    private Set<Role> roles;
    @JsonIgnore
    private String rolesString;
}

