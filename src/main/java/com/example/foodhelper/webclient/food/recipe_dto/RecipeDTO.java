package com.example.foodhelper.webclient.food.recipe_dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecipeDTO {

    private String spoonacularSourceUrl;
    private String image;
}
