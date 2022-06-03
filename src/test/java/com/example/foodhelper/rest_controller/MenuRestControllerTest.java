package com.example.foodhelper.rest_controller;

import com.example.foodhelper.model.dto.PlanPreferencesDTO;
import com.example.foodhelper.model.dto.PreferencesDTO;
import com.example.foodhelper.service.RecipeService;
import com.example.foodhelper.user_details.UserDetailsServiceImpl;
import com.example.foodhelper.webclient.food.complex_search_dto.ComplexSearchDTO;
import com.example.foodhelper.webclient.food.complex_search_dto.ComplexSearchResultDTO;
import com.example.foodhelper.webclient.food.mealPlannerDTO.MealInfoDTO;
import com.example.foodhelper.webclient.food.mealPlannerDTO.MealNutrientsDTO;
import com.example.foodhelper.webclient.food.mealPlannerDTO.MealPlanDTO;
import com.example.foodhelper.webclient.food.recipe_dto.RecipeDTO;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpStatus.*;

@WebMvcTest(MenuRestController.class)
class MenuRestControllerTest {

    private final String basicUrl = "/api/menu";

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    RecipeService recipeService;
    @MockBean
    UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    @Test
    void should_throw_when_getting_recipes_without_user_role() {
        given()
                .auth().none()
        .when()
                .get(basicUrl + "/recipes")
        .then()
                .statusCode(FORBIDDEN.value())
                .body("status", equalTo("FORBIDDEN"));
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void should_return_recipes_with_given_preferences() {
        PreferencesDTO preferences = new PreferencesDTO();
        preferences.setCuisine("Italian");

        BDDMockito.given(recipeService.complexSearch(preferences))
                        .willReturn(getRecipes());

        given()
                .body(preferences)
                .contentType(ContentType.JSON)
        .when()
                .get(basicUrl + "/recipes")
        .then()
                .statusCode(OK.value())
                .body("results[0].title", is("spaghetti bolognese"))
                .body("results[1].title", is("pizza margherita"))
                .body("results.size()", is(2));
    }

    @Test
    void should_throw_when_trying_to_generate_meal_plan_without_user_role() {
        given()
                .auth().none()
                .body(new PlanPreferencesDTO())
                .contentType(ContentType.JSON)
        .when()
                .get(basicUrl + "/plans")
        .then()
                .statusCode(FORBIDDEN.value())
                .body("status", equalTo("FORBIDDEN"));
    }

    @Test
    void should_throw_when_no_request_body_while_getting_meal_plan() {
        given()
                .auth().none()
        .when()
                .get(basicUrl + "/plans")
        .then()
                .statusCode(BAD_REQUEST.value())
                .body("status", equalTo("BAD_REQUEST"));
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void should_generate_meal_plan() {
        var planPreferences = new PlanPreferencesDTO();
        planPreferences.setDiet("omnivore");
        planPreferences.setTargetCalories(2500);

        BDDMockito.given(recipeService.getMealPlan(planPreferences))
                .willReturn(getMealPlan());

        given()
                .body(planPreferences)
                .contentType(ContentType.JSON)
       .when()
                .get(basicUrl + "/plans")
       .then()
                .statusCode(OK.value())
                .body("meals[0].title", is("Scrambled Eggs"))
                .body("meals[2].title", is("Taco Rice Bowls"))
                .body("nutrients.calories", is(2500.0F));
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void should_throw_when_trying_to_get_recipe_which_does_not_exist() {
        BDDMockito.given(recipeService.recipeById(1))
                .willThrow(HttpClientErrorException.NotFound.class);

        when()
                .get(basicUrl + "/recipes/1")
                .then()
                .statusCode(NOT_FOUND.value())
                .body("status", is("NOT_FOUND"))
                .body("message", is("Recipe with this ID does not exist"));
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void should_return_recipe_with_given_id() {
        var recipe = new RecipeDTO();
        recipe.setImage("photo");
        recipe.setSpoonacularSourceUrl("recipeLink");

        BDDMockito.given(recipeService.recipeById(1))
                .willReturn(recipe);

        when()
                .get(basicUrl + "/recipes/1")
                .then()
                .statusCode(OK.value())
                .body("image", is("photo"))
                .body("spoonacularSourceUrl", is("recipeLink"));
    }

    @Test
    void should_throw_when_getting_recipe_without_user_role() {
        given()
                .auth().none()
                .when()
                .get(basicUrl + "/recipes/1")
                .then()
                .statusCode(FORBIDDEN.value())
                .body("status", is("FORBIDDEN"));
    }

    private ComplexSearchDTO getRecipes() {
        var complexSearchDTO = new ComplexSearchDTO();
        List<ComplexSearchResultDTO>  results = new ArrayList<>();

        ComplexSearchResultDTO first_meal = new ComplexSearchResultDTO();
        first_meal.setTitle("spaghetti bolognese");

        ComplexSearchResultDTO second_meal = new ComplexSearchResultDTO();
        second_meal.setTitle("pizza margherita");

        results.add(first_meal);
        results.add(second_meal);

        complexSearchDTO.setResults(results);
        return complexSearchDTO;
    }

    private MealPlanDTO getMealPlan() {
        MealPlanDTO mealPlanDTO = new MealPlanDTO();

        List<MealInfoDTO> meals = new ArrayList<>();

        meals.add(new MealInfoDTO(0, "Scrambled Eggs"));
        meals.add(new MealInfoDTO(1, "Pesto Chicken Bruschetta"));
        meals.add(new MealInfoDTO(2, "Taco Rice Bowls"));

        MealNutrientsDTO nutrients = new MealNutrientsDTO();
        nutrients.setCalories(2500);

        mealPlanDTO.setMeals(meals);
        mealPlanDTO.setNutrients(nutrients);
        return mealPlanDTO;
    }



}