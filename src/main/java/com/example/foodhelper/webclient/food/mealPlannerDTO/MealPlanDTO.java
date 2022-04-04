package com.example.foodhelper.webclient.food.mealPlannerDTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class MealPlanDTO {

    private List<MealInfoDTO> meals;
    private MealNutrientsDTO nutrients;
}
