package com.example.foodhelper.model.dto;

import com.example.foodhelper.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class ManagementDTO extends RepresentationModel<ManagementDTO> {

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

