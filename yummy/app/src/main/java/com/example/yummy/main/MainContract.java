package com.example.yummy.main;

import com.example.yummy.model.meal.Meal;
import com.example.yummy.model.network.category.CategoryResponse;
import com.example.yummy.model.network.ingredient.IngredientResponse;

public interface MainContract {




    interface HomeView {
        void showRandomMeal(Meal meal);
        void showIngredients(IngredientResponse ingredientResponse);
        void showCategories(CategoryResponse categoryResponse);
        void showHomeError(String message);
        void openMealDetails(Meal meal);
    }

    interface HomePresenter {
        void getRandomMeal();
        void getIngredients();
        void getCategories();
        void saveMealToPreferences(Meal meal);
        Meal loadMealFromPreferences();
        void onMealImageClicked();
    }




}
