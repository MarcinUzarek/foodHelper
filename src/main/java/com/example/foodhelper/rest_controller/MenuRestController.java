package com.example.foodhelper.rest_controller;

import com.example.foodhelper.model.dto.PlanPreferencesDTO;
import com.example.foodhelper.model.dto.PreferencesDTO;
import com.example.foodhelper.service.RecipeService;
import com.example.foodhelper.webclient.food.complex_search_dto.ComplexSearchDTO;
import com.example.foodhelper.webclient.food.mealPlannerDTO.MealPlanDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/menu")
@PreAuthorize("hasAuthority('USER')")
public class MenuRestController {

    private final RecipeService recipeService;

    public MenuRestController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipes")
    public ResponseEntity<ComplexSearchDTO> getRecipes(@RequestBody(required = false) PreferencesDTO preferences) {
        var complexSearchDTO = recipeService.complexSearch(preferences);
        return ResponseEntity.ok(complexSearchDTO);
    }

    @GetMapping("/plans")
    public ResponseEntity<MealPlanDTO> getMealPlan(@RequestBody PlanPreferencesDTO plan) {
        var mealPlan = recipeService.getMealPlan(plan);
        return ResponseEntity.ok(mealPlan);
    }
}
