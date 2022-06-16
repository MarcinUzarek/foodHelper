package com.example.foodhelper.utils;

public class UriUtils {

    public static final String API_BASIC_URL = "https://api.spoonacular.com/";
    public static final String API_KEY = "?apiKey=4a16bdbaf362495f91cebed8dec54f5b";
    public static final String API_RECIPE_URL = API_BASIC_URL + "recipes/";
    public static final String API_COMPLEX_URL = API_RECIPE_URL + "complexSearch" + API_KEY;
    public static final String API_MEAL_PLAN = API_BASIC_URL + "mealplanner/generate" + API_KEY;

    public static String recipeById(int id) {
        return API_RECIPE_URL + id + "/information" + API_KEY;
    }

}
