package com.example.foodhelper.service;

import com.example.foodhelper.webclient.food.RecipeClient;
import com.example.foodhelper.webclient.food.complex_search_dto.ComplexSearchDTO;
import com.example.foodhelper.webclient.food.complex_search_dto.PreferencesDTO;
import com.example.foodhelper.webclient.food.mealPlannerDTO.MealPlanDTO;
import com.example.foodhelper.webclient.food.mealPlannerDTO.MealInfoDTO;
import com.example.foodhelper.webclient.food.mealPlannerDTO.PlanPreferencesDTO;
import com.example.foodhelper.webclient.food.recipe_dto.RecipeDTO;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {

    private final RecipeClient recipeClient;

    public RecipeService(RecipeClient recipeClient) {
        this.recipeClient = recipeClient;
    }

    public ComplexSearchDTO complexSearch(PreferencesDTO preferencesDto) {
        return recipeClient.recipeComplexSearch(
                preferencesDto.getCuisine(),
                preferencesDto.getDiet(),
                "intolerances",
                preferencesDto.getType(),
                preferencesDto.getMaxReadyTime());
    }

    public RecipeDTO recipeById(Integer id) {
        return recipeClient.recipeById(id);
    }


    public MealPlanDTO getMealPlan(PlanPreferencesDTO planPreferencesDto) {

        var mealPlan = recipeClient.getMealPlan(
                planPreferencesDto.getTargetCalories(), planPreferencesDto.getDiet());

        setImgForMeals(mealPlan);
        return mealPlan;
    }

    private void setImgForMeals(MealPlanDTO mealPlan) {
        mealPlan.getMeals()
                .forEach(meal -> meal.setImage(recipeById(meal.getId()).getImage()));
    }
}

