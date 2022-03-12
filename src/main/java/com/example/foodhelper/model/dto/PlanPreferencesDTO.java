package com.example.foodhelper.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
public class PlanPreferencesDTO {

    private String diet;
    @Min(value = 1, message = "Target Kcal should be higher")
    private Integer targetCalories;

}
