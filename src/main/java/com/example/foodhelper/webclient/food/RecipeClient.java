package com.example.foodhelper.webclient.food;

import com.example.foodhelper.webclient.food.complex_search_dto.ComplexSearchDTO;
import com.example.foodhelper.webclient.food.mealPlannerDTO.MealPlanDTO;
import com.example.foodhelper.webclient.food.recipe_dto.RecipeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class RecipeClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String API_RECIPE_URL = "https://api.spoonacular.com/recipes/";
    private static final String API_URL = "https://api.spoonacular.com/";
    private static final String API_COMPLEX_URL = "complexSearch";
    private static final String API_KEY = "4a16bdbaf362495f91cebed8dec54f5b";

    public ComplexSearchDTO recipeComplexSearch(String cuisine, String diet, String intolerances,
                                                String dishType, int maxReadyTime) {
        return restTemplate.getForObject(API_RECIPE_URL + API_COMPLEX_URL + "?apiKey=" + API_KEY +
                        "&cuisine={cuisine}&diet={dietType}&intolerances={intolerances}&type={dishType}&maxReadyTime={maxReadyTime}&number=99",
                ComplexSearchDTO.class, cuisine, diet, intolerances, dishType, maxReadyTime);
    }

    public RecipeDTO recipeById(Integer id) {
        return restTemplate.getForObject(API_RECIPE_URL + id + "/information?apiKey=" + API_KEY,
                RecipeDTO.class);
    }

    public MealPlanDTO getMealPlan(int targetKcal, String diet) {

        return restTemplate.getForObject(API_URL + "mealplanner/generate?apiKey=" + API_KEY +
                "&timeFrame=day&targetCalories={targetKcal}&diet={diet}",
                MealPlanDTO.class, targetKcal, diet);
    }


}
