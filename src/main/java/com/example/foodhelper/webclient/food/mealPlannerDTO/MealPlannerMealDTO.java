package com.example.foodhelper.webclient.food.mealPlannerDTO;

import lombok.Getter;

@Getter
public class MealPlannerMealDTO {

    private int id;
    private String title;
    private int readyInMinutes;
    private int servings;
    private String sourceUrl;

}
