package com.example.foodhelper.webclient.food.mealPlannerDTO;

import lombok.*;


@Data
@NoArgsConstructor
public class MealInfoDTO {

    public MealInfoDTO(int id, String title) {
        this.id = id;
        this.title = title;
    }

    private int id;
    private String title;
    private int readyInMinutes;
    private int servings;
    private String sourceUrl;
    private String image;

}
