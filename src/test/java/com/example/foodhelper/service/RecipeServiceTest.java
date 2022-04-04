package com.example.foodhelper.service;

import com.example.foodhelper.model.Intolerance;
import com.example.foodhelper.model.User;
import com.example.foodhelper.model.dto.PlanPreferencesDTO;
import com.example.foodhelper.model.dto.PreferencesDTO;
import com.example.foodhelper.webclient.food.RecipeClient;
import com.example.foodhelper.webclient.food.mealPlannerDTO.MealInfoDTO;
import com.example.foodhelper.webclient.food.mealPlannerDTO.MealPlanDTO;
import com.example.foodhelper.webclient.food.recipe_dto.RecipeDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @InjectMocks
    private RecipeService recipeService;
    @Mock
    private RecipeClient recipeClient;
    @Mock
    private UserService userService;
    @Captor
    private ArgumentCaptor<PreferencesDTO> preferencesCaptor;

    @ParameterizedTest
    @CsvSource(value = {"yes:Chocolate,Dairy", "no:''"}, delimiter = ':')
    void should_set_intolerances_based_on_answer_yes_or_no_from_user(
            String shouldIncludeIntolerances, String expectedIntolerances) {

        PreferencesDTO preferencesDTO = new PreferencesDTO(
                "italian", "omnivore", "main course",
                90, shouldIncludeIntolerances
        );

        var user = new User("User", "user@gmail.com", "password");
        user.setIntolerances(new TreeSet<>(
                Set.of(new Intolerance("Dairy"), new Intolerance("Chocolate"))));

        given(userService.getLoggedUser())
                .willReturn(user);

        //when
        recipeService.complexSearch(preferencesDTO);

        //then
        verify(recipeClient).recipeComplexSearch(preferencesCaptor.capture());
        assertThat(preferencesCaptor.getValue().getIntolerances(),
                is(expectedIntolerances));
    }

    @Test
    void should_get_meal_plan() {
        //given
        var meal1 = new MealInfoDTO(1, "Chicken");
        var meal2 = new MealInfoDTO(2, "Spaghetti");
        var meal3 = new MealInfoDTO(3, "Lasagne");

        MealPlanDTO mealPlanDTO = new MealPlanDTO();
        mealPlanDTO.setMeals(new ArrayList<>(List.of(
                meal1, meal2, meal3
        )));
        given(recipeClient.getMealPlan(new PlanPreferencesDTO()))
                .willReturn(mealPlanDTO);
        given(recipeClient.recipeById(1))
                .willReturn(new RecipeDTO("ChickenPhoto"));
        given(recipeClient.recipeById(2))
                .willReturn(new RecipeDTO("SpaghettiPhoto"));
        given(recipeClient.recipeById(3))
                .willReturn(new RecipeDTO("LasagnePhoto"));

        //when
        var mealPlan = recipeService.getMealPlan(new PlanPreferencesDTO());

        //then
        assertThat(mealPlan.getMeals().get(0).getTitle(), is("Chicken"));
        assertThat(mealPlan.getMeals().get(0).getImage(), is("ChickenPhoto"));
        assertThat(mealPlan.getMeals().get(2).getTitle(), is("Lasagne"));
        assertThat(mealPlan.getMeals().get(2).getImage(), is("LasagnePhoto"));
    }


}