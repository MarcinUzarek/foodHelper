package com.example.foodhelper.model.dto;

import com.example.foodhelper.model.Intolerance;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserShowDTO {

    private String name;
    private String email;
    private List<Intolerance> intolerances;
}
