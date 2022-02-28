package com.example.foodhelper.webclient.food.mealPlannerDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlanPreferencesDTO {

    private String diet;
    private Integer targetCalories;
}
