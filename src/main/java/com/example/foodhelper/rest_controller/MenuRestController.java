package com.example.foodhelper.rest_controller;

import com.example.foodhelper.model.dto.PlanPreferencesDTO;
import com.example.foodhelper.model.dto.PreferencesDTO;
import com.example.foodhelper.service.RecipeService;
import com.example.foodhelper.webclient.food.complex_search_dto.ComplexSearchDTO;
import com.example.foodhelper.webclient.food.complex_search_dto.ComplexSearchResultDTO;
import com.example.foodhelper.webclient.food.mealPlannerDTO.MealPlanDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<MealPlanDTO> getMealPlan(@RequestBody PlanPreferencesDTO plan) {
        var mealPlan = recipeService.getMealPlan(plan);
        return ResponseEntity.ok(mealPlan);
    }

    public ResponseEntity<ComplexSearchDTO> emergencyRecipes(Exception e) {

        handleAccessDeniedException(e);

        var complexSearch = new ComplexSearchDTO();
        var results = new ArrayList<ComplexSearchResultDTO>();

        var first = new ComplexSearchResultDTO();
        first.setTitle("testTitle");
        first.setId(1);
        first.setImage("image");
        results.add(first);

        complexSearch.setResults(results);
        log.warn("Fallback method has been invoked");
        return ResponseEntity.ok(complexSearch);
    }

    public ResponseEntity<MealPlanDTO> emergencyMealPlan(Error e) {
        return null;
    }

    private void handleAccessDeniedException(Exception e) {
        if (e instanceof AccessDeniedException) {
            throw new AccessDeniedException("");
        }
    }
}
