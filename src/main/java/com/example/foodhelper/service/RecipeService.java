package com.example.foodhelper.service;

import com.example.foodhelper.webclient.food.RecipeClient;
import com.example.foodhelper.webclient.food.complex_search_dto.ComplexSearchDTO;
import com.example.foodhelper.webclient.food.mealPlannerDTO.MealPlannerDTO;
import com.example.foodhelper.webclient.food.recipe_dto.RecipeDTO;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {

    private final RecipeClient recipeClient;

    public RecipeService(RecipeClient recipeClient) {
        this.recipeClient = recipeClient;
    }

    public ComplexSearchDTO complexSearch(String cuisine, String diet, String intolerances,
                                          String dishType, int maxReadyTime) {
        return recipeClient.recipeComplexSearch(cuisine, diet, intolerances, dishType, maxReadyTime);
    }

    public RecipeDTO recipeById(Integer id) {
        return recipeClient.recipeById(id);
    }


    public MealPlannerDTO getMealPlan(int targetKcal, String diet) {
       return recipeClient.getMealPlan(targetKcal, diet);

    }
}

