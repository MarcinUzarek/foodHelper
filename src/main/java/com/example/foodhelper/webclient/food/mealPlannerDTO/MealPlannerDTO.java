package com.example.foodhelper.webclient.food.mealPlannerDTO;

import lombok.Getter;

import java.util.List;

@Getter
public class MealPlannerDTO {

    private List<MealPlannerMealDTO> meals;
    private MealPlannerNutrientsDTO nutrients;
}
