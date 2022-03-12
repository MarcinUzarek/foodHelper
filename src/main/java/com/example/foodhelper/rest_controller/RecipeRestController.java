package com.example.foodhelper.rest_controller;

import com.example.foodhelper.service.RecipeService;
import com.example.foodhelper.webclient.food.complex_search_dto.ComplexSearchDTO;
import com.example.foodhelper.model.dto.PreferencesDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recipes")
public class RecipeRestController {

    private final RecipeService recipeService;

    public RecipeRestController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

        @GetMapping
        public ResponseEntity<ComplexSearchDTO> getRecipes(@RequestBody PreferencesDTO preferencesDto) {
            var complexSearchDTO = recipeService.complexSearch(preferencesDto);
            return ResponseEntity.ok(complexSearchDTO);
        }
}
