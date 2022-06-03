package com.example.foodhelper.webclient.food;

import com.example.foodhelper.model.dto.PlanPreferencesDTO;
import com.example.foodhelper.model.dto.PreferencesDTO;
import com.example.foodhelper.webclient.food.complex_search_dto.ComplexSearchDTO;
import com.example.foodhelper.webclient.food.mealPlannerDTO.MealPlanDTO;
import com.example.foodhelper.webclient.food.recipe_dto.RecipeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class RecipeClient {

    private static final String API_RECIPE_URL = "https://api.spoonacular.com/recipes/";
    private static final String API_URL = "https://api.spoonacular.com/";
    private static final String API_COMPLEX_URL = "complexSearch";
    private static final String API_KEY = "b704f2d913414eda8c6e67cf34f7001a";
    private final RestTemplate restTemplate;

    public RecipeClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ComplexSearchDTO recipeComplexSearch(PreferencesDTO preferencesDTO) {
        return restTemplate.getForObject(API_RECIPE_URL + API_COMPLEX_URL + "?apiKey=" + API_KEY +
                        "&cuisine={cuisine}&diet={dietType}&intolerances={intolerances}&type={dishType}&maxReadyTime={maxReadyTime}&number=99",
                ComplexSearchDTO.class, preferencesDTO.getCuisine(),
                preferencesDTO.getDiet(),
                preferencesDTO.getIntolerances(),
                preferencesDTO.getType(),
                preferencesDTO.getReadyTime());
    }

    public RecipeDTO recipeById(Integer id) {
        return restTemplate.getForObject(API_RECIPE_URL + id + "/information?apiKey=" + API_KEY,
                RecipeDTO.class);
    }

    public MealPlanDTO getMealPlan(PlanPreferencesDTO planPreferences) {

        return restTemplate.getForObject(API_URL + "mealplanner/generate?apiKey=" + API_KEY +
                        "&timeFrame=day&targetCalories={targetKcal}&diet={diet}",
                MealPlanDTO.class,
                planPreferences.getTargetCalories(),
                planPreferences.getDiet());
    }


}
