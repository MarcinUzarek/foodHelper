package com.example.foodhelper.webclient.food;

import com.example.foodhelper.model.dto.PlanPreferencesDTO;
import com.example.foodhelper.model.dto.PreferencesDTO;
import com.example.foodhelper.utils.UriUtils;
import com.example.foodhelper.webclient.food.complex_search_dto.ComplexSearchDTO;
import com.example.foodhelper.webclient.food.mealPlannerDTO.MealPlanDTO;
import com.example.foodhelper.webclient.food.recipe_dto.RecipeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.example.foodhelper.utils.UriUtils.API_COMPLEX_URL;
import static com.example.foodhelper.utils.UriUtils.API_MEAL_PLAN;

@Component
@Slf4j
public class RecipeClient {

    private final RestTemplate restTemplate;

    private final String API_KEY_VALUE;

    public RecipeClient(RestTemplate restTemplate, @Value("${food-helper.spoonacularKeyValue}") String API_KEY_VALUE) {
        this.restTemplate = restTemplate;
        this.API_KEY_VALUE = API_KEY_VALUE;
    }

    public ComplexSearchDTO recipeComplexSearch(PreferencesDTO preferencesDTO) {
        return restTemplate.getForObject(API_COMPLEX_URL + getApiKey() +
                        "&cuisine={cuisine}&diet={dietType}&intolerances={intolerances}&type={dishType}&maxReadyTime={maxReadyTime}&number=99",
                ComplexSearchDTO.class, preferencesDTO.getCuisine(),
                preferencesDTO.getDiet(),
                preferencesDTO.getIntolerances(),
                preferencesDTO.getType(),
                preferencesDTO.getReadyTime());
    }

    public RecipeDTO recipeById(Integer id) {
        return restTemplate.getForObject(UriUtils.recipeById(id) + getApiKey(),
                RecipeDTO.class);
    }

    public MealPlanDTO getMealPlan(PlanPreferencesDTO planPreferences) {

        return restTemplate.getForObject(API_MEAL_PLAN + getApiKey() +
                        "&timeFrame=day&targetCalories={targetKcal}&diet={diet}",
                MealPlanDTO.class,
                planPreferences.getTargetCalories(),
                planPreferences.getDiet());
    }

    private String getApiKey() {
        return "?apiKey=" + API_KEY_VALUE;
    }


}
