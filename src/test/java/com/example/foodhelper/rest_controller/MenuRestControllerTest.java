package com.example.foodhelper.rest_controller;

import com.example.foodhelper.model.dto.PreferencesDTO;
import com.example.foodhelper.service.RecipeService;
import com.example.foodhelper.user_details.UserDetailsServiceImpl;
import com.example.foodhelper.webclient.food.complex_search_dto.ComplexSearchDTO;
import com.example.foodhelper.webclient.food.complex_search_dto.ComplexSearchResultDTO;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

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
                .statusCode(403)
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
                .statusCode(200)
                .body("results[0].title", is("spaghetti bolognese"))
                .body("results[1].title", is("pizza margherita"))
                .body("results.size()", is(2));
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


}