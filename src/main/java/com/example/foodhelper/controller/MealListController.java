package com.example.foodhelper.controller;

import com.example.foodhelper.service.RecipeService;
import com.example.foodhelper.webclient.food.complex_search_dto.ComplexSearchDTO;
import com.example.foodhelper.webclient.food.complex_search_dto.PreferencesDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
class MealListController {

    private final RecipeService recipeService;

    MealListController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("meal-list")
    String getComplexRecipe(Model model) {
        ComplexSearchDTO results = new ComplexSearchDTO();
        model.addAttribute("meals", results);

        return "meal-list";
    }

    @PostMapping("meal-list")
    String fillPreferences(Model model, PreferencesDTO preferencesDto) {
        var results = recipeService.complexSearch(
                preferencesDto);
        model.addAttribute("meals", results);

        if (results.getResults().isEmpty()) {
            return "meal-list-empty";
        }
        return "meal-list";
    }

    @GetMapping(path = "searchById/{id}")
    String findById(@PathVariable Integer id) {
        var recipe = recipeService.recipeById(id);
        var recipeLink = recipe.getSpoonacularSourceUrl();

        return "redirect:" + recipeLink;
    }

}
