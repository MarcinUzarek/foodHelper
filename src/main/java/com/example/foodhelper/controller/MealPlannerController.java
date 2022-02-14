package com.example.foodhelper.controller;

import com.example.foodhelper.service.RecipeService;
import com.example.foodhelper.webclient.food.mealPlannerDTO.MealPlannerDTO;
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
        MealPlannerDTO mealPlan = new MealPlannerDTO();
        model.addAttribute("mealPlan", mealPlan);
        return "meal-planner";
    }

    @PostMapping
    String generateMealPlan(Model model,
                            @RequestParam String diet,
                            @RequestParam Integer targetCalories) {
        var mealPlan = recipeService.getMealPlan(targetCalories, diet);
        model.addAttribute("mealPlan", mealPlan);

        return "meal-planner";
    }
}
