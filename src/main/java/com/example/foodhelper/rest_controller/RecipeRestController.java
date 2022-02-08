package com.example.foodhelper.rest_controller;

import com.example.foodhelper.service.RecipeService;
import com.example.foodhelper.webclient.food.complex_search_dto.ComplexSearchDTO;
import com.example.foodhelper.webclient.food.mealPlannerDTO.MealPlannerDTO;
import com.example.foodhelper.webclient.food.recipe_dto.RecipeDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/recipes")
public class RecipeRestController {

    private final RecipeService recipeService;

    public RecipeRestController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/complexSearch")
    public ComplexSearchDTO complexSearch() {
         return recipeService.complexSearch(
                 "italian", "vegetarian", "sugar,salt", "dinner", 50);
    }

    @GetMapping("/recipeById")
    public RecipeDTO recipeById() {
        return recipeService.recipeById(1697791);
    }

    @GetMapping("/mealPlanner")
    public MealPlannerDTO getMealPlan() {
        return recipeService.getMealPlan(3000, "vegetarian");
    }
}
