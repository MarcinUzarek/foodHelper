package com.example.foodhelper.webclient.food.mealPlannerDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MealInfoDTO {

    private int id;
    private String title;
    private int readyInMinutes;
    private int servings;
    private String sourceUrl;
    private String image;

}
