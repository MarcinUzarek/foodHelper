package com.example.foodhelper.rest_controller;

import com.example.foodhelper.model.dto.PlanPreferencesDTO;
import com.example.foodhelper.model.dto.PreferencesDTO;
import com.example.foodhelper.service.RecipeService;
import com.example.foodhelper.webclient.food.complex_search_dto.ComplexSearchDTO;
import com.example.foodhelper.webclient.food.complex_search_dto.ComplexSearchResultDTO;
import com.example.foodhelper.webclient.food.mealPlannerDTO.MealInfoDTO;
import com.example.foodhelper.webclient.food.mealPlannerDTO.MealNutrientsDTO;
import com.example.foodhelper.webclient.food.mealPlannerDTO.MealPlanDTO;
import com.example.foodhelper.webclient.food.recipe_dto.RecipeDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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
    @CircuitBreaker(name = apiErrorCircuitBreaker, fallbackMethod = "emergencyRecipes")
    public ResponseEntity<ComplexSearchDTO> getRecipes(@RequestBody(required = false) PreferencesDTO preferences) {
        var complexSearchDTO = recipeService.complexSearch(preferences);
        return ResponseEntity.ok(complexSearchDTO);
    }

    @GetMapping("/plans")
    @CircuitBreaker(name = apiErrorCircuitBreaker, fallbackMethod = "emergencyMealPlan")
    public ResponseEntity<MealPlanDTO> getMealPlan(@RequestBody PlanPreferencesDTO plan) {
        var mealPlan = recipeService.getMealPlan(plan);
        return ResponseEntity.ok(mealPlan);
    }

    @GetMapping("/recipes/{id}")
    public ResponseEntity<RecipeDTO> getRecipeById(@PathVariable int id) {
        var recipe = recipeService.recipeById(id);
        return ResponseEntity.ok(recipe);
    }

    public ResponseEntity<ComplexSearchDTO> emergencyRecipes(Exception e) {
        handleAccessDeniedException(e);

        var complexSearch = new ComplexSearchDTO();
        var results = new ArrayList<ComplexSearchResultDTO>();

        var first = new ComplexSearchResultDTO();
        first.setTitle("testTitle");
        first.setImage("image");
        results.add(first);

        complexSearch.setResults(results);
        log.warn("Fallback method has been invoked, " + e);
        return ResponseEntity.ok(complexSearch);
    }

    public ResponseEntity<MealPlanDTO> emergencyMealPlan(Exception e) {
        handleAccessDeniedException(e);

        MealPlanDTO mealPlan = new MealPlanDTO();

        var meals = new ArrayList<MealInfoDTO>();
        var first = new MealInfoDTO();
        var second = new MealInfoDTO();
        var third = new MealInfoDTO();
        first.setTitle("first meal");
        second.setTitle("second meal");
        third.setTitle("third meal");

        meals.add(first);
        meals.add(second);
        meals.add(third);

        var nutrients = new MealNutrientsDTO();
        nutrients.setCalories(2000);
        nutrients.setCarbohydrates(100);
        nutrients.setFat(50);
        nutrients.setProtein(100);

        mealPlan.setMeals(meals);
        mealPlan.setNutrients(nutrients);
        log.warn("Fallback method has been invoked, " + e);
        return ResponseEntity.ok(mealPlan);
    }

    private void handleAccessDeniedException(Exception e) {
        if (e instanceof AccessDeniedException) {
            throw new AccessDeniedException("");
        }
    }
}
