package com.example.foodhelper.service;

import com.example.foodhelper.webclient.food.RecipeClient;
import com.example.foodhelper.webclient.food.complex_search_dto.ComplexSearchDTO;
import com.example.foodhelper.webclient.food.mealPlannerDTO.MealPlanDTO;
import com.example.foodhelper.webclient.food.mealPlannerDTO.MealInfoDTO;
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


    public MealPlanDTO getMealPlan(int targetKcal, String diet) {

        var mealPlan = recipeClient.getMealPlan(targetKcal, diet);

        for (MealInfoDTO meal : mealPlan.getMeals()) {
           meal.setImage(recipeById(meal.getId()).getImage());
        }
        return mealPlan;
    }
}

