package com.example.foodhelper.controller;

import com.example.foodhelper.service.RecipeService;
import com.example.foodhelper.webclient.food.mealPlannerDTO.MealPlanDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/meals")
public class MealPlannerController {

    private final RecipeService recipeService;

    public MealPlannerController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    String getMealPlan(Model model) {
        MealPlanDTO mealPlan = new MealPlanDTO();
        model.addAttribute("mealPlan", mealPlan);
        return "meal-plan";
    }

    @PostMapping
    String generateMealPlan(Model model,
                            @RequestParam String diet,
                            @RequestParam Integer targetCalories) {
        var mealPlan = recipeService.getMealPlan(targetCalories, diet);
        model.addAttribute("mealPlan", mealPlan);

        return "meal-plan";
    }
}
