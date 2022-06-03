package com.example.foodhelper.utils;

import com.example.foodhelper.model.dto.PlanPreferencesDTO;
import com.example.foodhelper.webclient.food.complex_search_dto.ComplexSearchDTO;
import com.example.foodhelper.webclient.food.complex_search_dto.ComplexSearchResultDTO;
import com.example.foodhelper.webclient.food.mealPlannerDTO.MealInfoDTO;
import com.example.foodhelper.webclient.food.mealPlannerDTO.MealNutrientsDTO;
import com.example.foodhelper.webclient.food.mealPlannerDTO.MealPlanDTO;
import com.example.foodhelper.webclient.food.recipe_dto.RecipeDTO;

import java.util.ArrayList;

public final class FoodUtils {

    private FoodUtils() throws InstantiationException {
        throw new InstantiationException();
    }

    public static ComplexSearchDTO getEmergencyRecipes() {
        var complexSearch = new ComplexSearchDTO();
        var results = new ArrayList<ComplexSearchResultDTO>();

        var meal_0 = new ComplexSearchResultDTO();
        meal_0.setId(715769);
        meal_0.setTitle("Broccolini Quinoa Pilaf");
        meal_0.setImage("https://spoonacular.com/recipeImages/715769-312x231.jpg");

        var meal_1 = new ComplexSearchResultDTO();
        meal_1.setId(663150);
        meal_1.setTitle("Thai Savory Brown Fried Rice");
        meal_1.setImage("https://spoonacular.com/recipeImages/663150-312x231.jpg");

        var meal_2 = new ComplexSearchResultDTO();
        meal_2.setId(646425);
        meal_2.setTitle("Healthier Pork Fried Rice");
        meal_2.setImage("https://spoonacular.com/recipeImages/646425-312x231.jpg");

        var meal_3 = new ComplexSearchResultDTO();
        meal_3.setId(659037);
        meal_3.setTitle("Salmon and Broccoli Crepes");
        meal_3.setImage("https://spoonacular.com/recipeImages/659037-312x231.jpg");

        var meal_4 = new ComplexSearchResultDTO();
        meal_4.setId(1096205);
        meal_4.setTitle("Zucchini Feta Roulade");
        meal_4.setImage("https://spoonacular.com/recipeImages/1096205-312x231.jpg");

        var meal_5 = new ComplexSearchResultDTO();
        meal_5.setId(664429);
        meal_5.setTitle("Vegan Dirty Chai Pudding");
        meal_5.setImage("https://spoonacular.com/recipeImages/664429-312x231.jpg");

        var meal_6 = new ComplexSearchResultDTO();
        meal_6.setId(1096280);
        meal_6.setTitle("Strawberry Lime Basil Sherbet");
        meal_6.setImage("https://spoonacular.com/recipeImages/1096280-312x231.jpg");

        var meal_7 = new ComplexSearchResultDTO();
        meal_7.setId(715497);
        meal_7.setTitle("Berry Banana Breakfast Smoothie");
        meal_7.setImage("https://spoonacular.com/recipeImages/715497-312x231.jpg");

        var meal_8 = new ComplexSearchResultDTO();
        meal_8.setId(640965);
        meal_8.setTitle("Crushed Lentil Soup- Granola Style");
        meal_8.setImage("https://spoonacular.com/recipeImages/640965-312x231.jpg");

        var meal_9 = new ComplexSearchResultDTO();
        meal_9.setId(650377);
        meal_9.setTitle("Low Carb Brunch Burger");
        meal_9.setImage("https://spoonacular.com/recipeImages/650377-312x231.jpg");

        results.add(meal_0);
        results.add(meal_1);
        results.add(meal_2);
        results.add(meal_3);
        results.add(meal_4);
        results.add(meal_5);
        results.add(meal_6);
        results.add(meal_7);
        results.add(meal_8);
        results.add(meal_9);

        complexSearch.setResults(results);
        return complexSearch;
    }

    public static RecipeDTO getEmergencyRecipe() {
        var recipe = new RecipeDTO();
        recipe.setSpoonacularSourceUrl("https://spoonacular.com/");
        return recipe;
    }

    public static MealPlanDTO getEmergencyMealPlan(PlanPreferencesDTO preferences) {
        if (preferences.getTargetCalories() > 3250) {
            return getFifthPlan();
        }
        if (preferences.getTargetCalories() > 2750) {
            return getFourthPlan();
        }
        if (preferences.getTargetCalories() > 2250) {
            return getThirdPlan();
        }
        if (preferences.getTargetCalories() > 1750) {
            return getSecondPlan();
        }
        return getFirstPlan();
    }

    private static MealPlanDTO getFirstPlan() {
        var mealPlan = new MealPlanDTO();
        var meals = new ArrayList<MealInfoDTO>();
        var nutrients = new MealNutrientsDTO();
        var first_meal = new MealInfoDTO();
        var second_meal = new MealInfoDTO();
        var third_meal = new MealInfoDTO();

        first_meal.setId(70427);
        first_meal.setTitle("Apple Pie Granita");
        first_meal.setReadyInMinutes(15);
        first_meal.setServings(8);
        first_meal.setSourceUrl("http://www.foodandwine.com/recipes/apple-pie-granita");
        first_meal.setImage("https://spoonacular.com/recipeImages/70427-556x370.jpg");

        second_meal.setId(227954);
        second_meal.setTitle("Coconut & Mango Yoghurt Fool with Chocolate Dipped Banana");
        second_meal.setReadyInMinutes(15);
        second_meal.setServings(2);
        second_meal.setSourceUrl("http://www.tinnedtomatoes.com/2013/05/coconut-mango-yoghurt-fool-with.html");
        second_meal.setImage("https://spoonacular.com/recipeImages/227954-556x370.jpg");

        third_meal.setId(188052);
        third_meal.setTitle("Nasturtium Pizza");
        third_meal.setReadyInMinutes(45);
        third_meal.setServings(4);
        third_meal.setSourceUrl("http://www.epicurious.com/recipes/food/views/Nasturtium-Pizza-51176230");
        third_meal.setImage("https://spoonacular.com/recipeImages/188052-556x370.jpg");

        nutrients.setCalories(1500);
        nutrients.setProtein(44.77f);
        nutrients.setFat(59.93f);
        nutrients.setCarbohydrates(195.8f);

        setMealPlanDetails(mealPlan, meals, nutrients, first_meal, second_meal, third_meal);
        return mealPlan;
    }

    private static MealPlanDTO getSecondPlan() {
        var mealPlan = new MealPlanDTO();
        var meals = new ArrayList<MealInfoDTO>();
        var nutrients = new MealNutrientsDTO();
        var first_meal = new MealInfoDTO();
        var second_meal = new MealInfoDTO();
        var third_meal = new MealInfoDTO();

        first_meal.setId(1024483);
        first_meal.setTitle("Foil Packs with Sausage, Corn, Zucchini and Potatoes");
        first_meal.setReadyInMinutes(35);
        first_meal.setServings(4);
        first_meal.setSourceUrl("https://www.simplyrecipes.com/recipes/foil_packs_with_sausage_corn_zucchini_and_potatoes/");
        first_meal.setImage("https://spoonacular.com/recipeImages/1024483-556x370.jpg");

        second_meal.setId(113759);
        second_meal.setTitle("Elegant Pork Chops");
        second_meal.setReadyInMinutes(30);
        second_meal.setServings(2);
        second_meal.setSourceUrl("http://www.tasteofhome.com/recipes/elegant-pork-chops");
        second_meal.setImage("https://spoonacular.com/recipeImages/113759-556x370.png");

        third_meal.setId(622551);
        third_meal.setTitle("Caramel Walnut Sticky Buns");
        third_meal.setReadyInMinutes(45);
        third_meal.setServings(6);
        third_meal.setSourceUrl("http://www.thelittleepicurean.com/2014/12/caramel-walnut-sticky-buns.html");
        third_meal.setImage("https://spoonacular.com/recipeImages/622551-556x370.jpg");

        nutrients.setCalories(2001.25f);
        nutrients.setProtein(74.87f);
        nutrients.setFat(109.41f);
        nutrients.setCarbohydrates(185.01f);

        setMealPlanDetails(mealPlan, meals, nutrients, first_meal, second_meal, third_meal);
        return mealPlan;
    }

    private static MealPlanDTO getThirdPlan() {
        var mealPlan = new MealPlanDTO();
        var meals = new ArrayList<MealInfoDTO>();
        var nutrients = new MealNutrientsDTO();
        var first_meal = new MealInfoDTO();
        var second_meal = new MealInfoDTO();
        var third_meal = new MealInfoDTO();

        first_meal.setId(1697737);
        first_meal.setTitle("My First French Crêpe");
        first_meal.setReadyInMinutes(20);
        first_meal.setServings(2);
        first_meal.setSourceUrl("https://spoonacular.com/my-first-french-crpe-1697737");
        first_meal.setImage("https://spoonacular.com/recipeImages/1697737-556x370.jpg");

        second_meal.setId(378265);
        second_meal.setTitle("Quick Black Bean Quesadillas");
        second_meal.setReadyInMinutes(25);
        second_meal.setServings(4);
        second_meal.setSourceUrl("http://www.tasteofhome.com/Recipes/quick-black-bean-quesadillas");
        second_meal.setImage("https://spoonacular.com/recipeImages/378265-556x370.jpg");

        third_meal.setId(917119);
        third_meal.setTitle("Creamy Crockpot Macaroni and Cheese");
        third_meal.setReadyInMinutes(125);
        third_meal.setServings(6);
        third_meal.setSourceUrl("https://amandascookin.com/crockpot-macaroni-and-cheese/");
        third_meal.setImage("https://spoonacular.com/recipeImages/917119-556x370.jpg");

        nutrients.setCalories(2500.63f);
        nutrients.setProtein(79.43f);
        nutrients.setFat(143.23f);
        nutrients.setCarbohydrates(224.79f);

        setMealPlanDetails(mealPlan, meals, nutrients, first_meal, second_meal, third_meal);
        return mealPlan;
    }

    private static MealPlanDTO getFourthPlan() {
        var mealPlan = new MealPlanDTO();
        var meals = new ArrayList<MealInfoDTO>();
        var nutrients = new MealNutrientsDTO();
        var first_meal = new MealInfoDTO();
        var second_meal = new MealInfoDTO();
        var third_meal = new MealInfoDTO();

        first_meal.setId(1697557);
        first_meal.setTitle("Cast Iron Shrimp Pizza with Pecan Basil Pesto");
        first_meal.setReadyInMinutes(30);
        first_meal.setServings(2);
        first_meal.setSourceUrl("https://spoonacular.com/cast-iron-shrimp-pizza-with-pecan-basil-pesto-1697557");
        first_meal.setImage("https://spoonacular.com/recipeImages/1697557-556x370.jpg");

        second_meal.setId(8591);
        second_meal.setTitle("Pancetta-Arugula-Turkey Sandwiches");
        second_meal.setReadyInMinutes(15);
        second_meal.setServings(6);
        second_meal.setSourceUrl("http://www.myrecipes.com/recipe/pancetta-arugula-turkey-sandwiches-50400000117077/");
        second_meal.setImage("https://spoonacular.com/recipeImages/8591-556x370.jpg");

        third_meal.setId(1109905);
        third_meal.setTitle("Gyro Meatloaf Pita Sandwich");
        third_meal.setReadyInMinutes(80);
        third_meal.setServings(8);
        third_meal.setSourceUrl("http://www.nutritiouseats.com/gyro-meatloaf-pita-sandwich/");
        third_meal.setImage("https://spoonacular.com/recipeImages/1109905-556x370.jpg");

        nutrients.setCalories(3065.8f);
        nutrients.setProtein(131.95f);
        nutrients.setFat(154.56f);
        nutrients.setCarbohydrates(286.14f);

        setMealPlanDetails(mealPlan, meals, nutrients, first_meal, second_meal, third_meal);
        return mealPlan;
    }

    private static MealPlanDTO getFifthPlan() {
        var mealPlan = new MealPlanDTO();
        var meals = new ArrayList<MealInfoDTO>();
        var nutrients = new MealNutrientsDTO();
        var first_meal = new MealInfoDTO();
        var second_meal = new MealInfoDTO();
        var third_meal = new MealInfoDTO();

        first_meal.setId(410081);
        first_meal.setTitle("Party-Sized Sausage Calzones");
        first_meal.setReadyInMinutes(60);
        first_meal.setServings(4);
        first_meal.setSourceUrl("http://www.tasteofhome.com/Recipes/party-sized-sausage-calzones");
        first_meal.setImage("https://spoonacular.com/recipeImages/410081-556x370.jpg");

        second_meal.setId(1159955);
        second_meal.setTitle("Scallops in Maple Cream Sauce");
        second_meal.setReadyInMinutes(25);
        second_meal.setServings(2);
        second_meal.setSourceUrl("https://www.framedcooks.com/2019/12/scallops-in-maple-cream-sauce.html");
        second_meal.setImage("https://spoonacular.com/recipeImages/1159955-556x370.jpg");

        third_meal.setId(1697557);
        third_meal.setTitle("Cast Iron Shrimp Pizza with Pecan Basil Pesto");
        third_meal.setReadyInMinutes(30);
        third_meal.setServings(2);
        third_meal.setSourceUrl("https://spoonacular.com/cast-iron-shrimp-pizza-with-pecan-basil-pesto-1697557");
        third_meal.setImage("https://spoonacular.com/recipeImages/1697557-556x370.jpg");

        nutrients.setCalories(3567.71f);
        nutrients.setProtein(122.98f);
        nutrients.setFat(203.9f);
        nutrients.setCarbohydrates(305.96f);

        setMealPlanDetails(mealPlan, meals, nutrients, first_meal, second_meal, third_meal);
        return mealPlan;
    }



    private static void setMealPlanDetails(MealPlanDTO mealPlan,
                                           ArrayList<MealInfoDTO> meals,
                                           MealNutrientsDTO nutrients,
                                           MealInfoDTO first_meal,
                                           MealInfoDTO second_meal,
                                           MealInfoDTO third_meal) {
        meals.add(first_meal);
        meals.add(second_meal);
        meals.add(third_meal);
        mealPlan.setMeals(meals);
        mealPlan.setNutrients(nutrients);
    }


}








