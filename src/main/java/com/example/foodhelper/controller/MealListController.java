package com.example.foodhelper.controller;

import com.example.foodhelper.service.RecipeService;
import com.example.foodhelper.webclient.food.complex_search_dto.ComplexSearchDTO;
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
    String fillPreferences(Model model,
                           @RequestParam(required = false) String cuisine,
                           @RequestParam(required = false) String diet,
                           @RequestParam(required = false) String type,
                           @RequestParam Integer maxReadyTime) {
        var results = recipeService.complexSearch(
                cuisine, diet, "",
                type, maxReadyTime);
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
