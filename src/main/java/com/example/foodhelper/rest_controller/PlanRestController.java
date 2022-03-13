package com.example.foodhelper.rest_controller;

import com.example.foodhelper.model.dto.PlanPreferencesDTO;
import com.example.foodhelper.service.RecipeService;
import com.example.foodhelper.webclient.food.mealPlannerDTO.MealPlanDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/plans")
public class PlanRestController {

    private final RecipeService recipeService;

    public PlanRestController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public ResponseEntity<MealPlanDTO> getMealPlan(@RequestBody PlanPreferencesDTO plan) {
        var mealPlan = recipeService.getMealPlan(plan);
        return ResponseEntity.ok(mealPlan);
    }
}
