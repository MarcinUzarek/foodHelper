package com.example.foodhelper.service;

import com.example.foodhelper.model.Intolerance;
import com.example.foodhelper.webclient.food.RecipeClient;
import com.example.foodhelper.webclient.food.complex_search_dto.ComplexSearchDTO;
import com.example.foodhelper.webclient.food.complex_search_dto.PreferencesDTO;
import com.example.foodhelper.webclient.food.mealPlannerDTO.MealPlanDTO;
import com.example.foodhelper.webclient.food.mealPlannerDTO.PlanPreferencesDTO;
import com.example.foodhelper.webclient.food.recipe_dto.RecipeDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RecipeService {

    private final RecipeClient recipeClient;
    private final UserService userService;

    public RecipeService(RecipeClient recipeClient, UserService userService) {
        this.recipeClient = recipeClient;
        this.userService = userService;
    }

    public ComplexSearchDTO complexSearch(PreferencesDTO preferencesDto) {
        String intolerances = GetUserIntolerancesIfHeWants(preferencesDto);

        return recipeClient.recipeComplexSearch(
                preferencesDto.getCuisine(),
                preferencesDto.getDiet(),
                intolerances,
                preferencesDto.getType(),
                preferencesDto.getMaxReadyTime());
    }

    private String GetUserIntolerancesIfHeWants(PreferencesDTO preferencesDto) {
        String intolerances;
        var userIntolerances = userService.getLoggedUser().getIntolerances();

        if (preferencesDto.getShouldIncludeIntolerances().equals("yes") && !userIntolerances.isEmpty()) {
            intolerances = mapSetOfIntolerancesToStringWithComma();
        } else {
            intolerances = "";
        }
        return intolerances;
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

    private String mapSetOfIntolerancesToStringWithComma() {
        StringBuffer stringBuffer = new StringBuffer("");
        var userIntolerances = userService.getLoggedUser().getIntolerances();
        userIntolerances.forEach(intolerance ->{
            stringBuffer.append(intolerance.getProduct()).append(",");
        });
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        return stringBuffer.toString();
    }

    private void setImgForMeals(MealPlanDTO mealPlan) {
        mealPlan.getMeals()
                .forEach(meal -> meal.setImage(recipeById(meal.getId()).getImage()));
    }
}

