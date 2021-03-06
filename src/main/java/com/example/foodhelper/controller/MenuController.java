package com.example.foodhelper.controller;

import com.example.foodhelper.service.RecipeService;
import com.example.foodhelper.model.dto.PreferencesDTO;
import com.example.foodhelper.model.dto.PlanPreferencesDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
@PreAuthorize("hasAuthority('USER')")
public class MenuController {

    private final RecipeService recipeService;

    public MenuController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping()
    String getMenuPage(Model model) {
        model.addAttribute("preferencesDTO", new PreferencesDTO());
        model.addAttribute("planPreferencesDTO", new PlanPreferencesDTO());
        return "index";
    }

    @PostMapping("meal-list")
    String findRecipe(@Valid @ModelAttribute("preferencesDTO") PreferencesDTO preferencesDto,
                      BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "index";
        }

        var results = recipeService.complexSearch(
                preferencesDto);
        model.addAttribute("meals", results);

        if (results.getResults().isEmpty()) {
            return "meal-list-empty";
        }
        return "meal-list";
    }

    @PostMapping("/meals")
    String generateMealPlan(@Valid @ModelAttribute("planPreferencesDTO") PlanPreferencesDTO preferencesDto,
                            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "index";
        }
        var mealPlan = recipeService.getMealPlan(preferencesDto);
        model.addAttribute("mealPlan", mealPlan);

        return "meal-plan";
    }

    @GetMapping(path = "searchById/{id}")
    String findById(@PathVariable Integer id) {
        var recipe = recipeService.recipeById(id);
        var recipeLink = recipe.getSpoonacularSourceUrl();

        return "redirect:" + recipeLink;
    }
}
