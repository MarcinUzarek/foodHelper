package com.example.foodhelper.webclient.food.mealPlannerDTO;

import lombok.Getter;

import java.util.List;

@Getter
public class MealPlanDTO {

    private List<MealInfoDTO> meals;
    private MealNutrientsDTO nutrients;
}
