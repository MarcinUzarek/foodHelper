package com.example.foodhelper.rest_controller;

import com.example.foodhelper.model.dto.PlanPreferencesDTO;
import com.example.foodhelper.model.dto.PreferencesDTO;
import com.example.foodhelper.service.RecipeService;
import com.example.foodhelper.utils.FoodUtils;
import com.example.foodhelper.webclient.food.complex_search_dto.ComplexSearchDTO;
import com.example.foodhelper.webclient.food.mealPlannerDTO.MealPlanDTO;
import com.example.foodhelper.webclient.food.recipe_dto.RecipeDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/menu")
@PreAuthorize("hasAuthority('USER')")
@Slf4j
public class MenuRestController {

    private final RecipeService recipeService;
    private final String apiErrorCircuitBreaker = "apiError";

    public MenuRestController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipes")
    @CircuitBreaker(name = apiErrorCircuitBreaker, fallbackMethod = "fallbackRecipes")
    public ResponseEntity<ComplexSearchDTO> getRecipes(@RequestBody(required = false) PreferencesDTO preferences) {
        var complexSearchDTO = recipeService.complexSearch(preferences);
        return ResponseEntity.ok(complexSearchDTO);
    }

    @GetMapping("/recipes/{id}")
    @CircuitBreaker(name = apiErrorCircuitBreaker, fallbackMethod = "fallbackRecipe")
    public ResponseEntity<RecipeDTO> getRecipeById(@PathVariable int id) {
        var recipe = recipeService.recipeById(id);
        return ResponseEntity.ok(recipe);
    }

    @GetMapping("/plans")
    @CircuitBreaker(name = apiErrorCircuitBreaker, fallbackMethod = "fallbackMealPlan")
    public ResponseEntity<MealPlanDTO> getMealPlan(@RequestBody PlanPreferencesDTO plan) {
        var mealPlan = recipeService.getMealPlan(plan);
        return ResponseEntity.ok(mealPlan);
    }

    public ResponseEntity<ComplexSearchDTO> fallbackRecipes(Exception e) {
        handleErrors(e);
        return ResponseEntity.ok(FoodUtils.getEmergencyRecipes());
    }

    public ResponseEntity<RecipeDTO> fallbackRecipe(Exception e) {
        handleErrors(e);
        return ResponseEntity.ok(FoodUtils.getEmergencyRecipe());
    }

    public ResponseEntity<MealPlanDTO> fallbackMealPlan(PlanPreferencesDTO preferences, Exception e) {
        handleErrors(e);
        return ResponseEntity.ok(FoodUtils.getEmergencyMealPlan(preferences));
    }

    private void handleErrors(Exception e) {
        if (e instanceof AccessDeniedException) {
            throw new AccessDeniedException("");
        }
        log.warn("Fallback method has been invoked, " + e);
    }
}
