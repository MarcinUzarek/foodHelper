package com.example.foodhelper.utils;

public class UriUtils {

    public static final String API_BASIC_URL = "https://api.spoonacular.com/";
    public static final String API_RECIPE_URL = API_BASIC_URL + "recipes/";
    public static final String API_COMPLEX_URL = API_RECIPE_URL + "complexSearch";
    public static final String API_MEAL_PLAN = API_BASIC_URL + "mealplanner/generate";

    public static String recipeById(int id) {
        return API_RECIPE_URL + id + "/information";
    }

}
