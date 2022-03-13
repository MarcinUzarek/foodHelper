package com.example.foodhelper.model.dto;

import com.example.foodhelper.model.Intolerance;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserShowDTO {

    private String name;
    private String email;
    private Set<Intolerance> intolerances;
}
