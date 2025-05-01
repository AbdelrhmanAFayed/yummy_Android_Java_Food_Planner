package com.example.yummy.meals;

import com.example.yummy.model.mealshort.MealShort;

import java.util.List;

public interface MealContract {

    interface View {
        void showLoading();
        void hideLoading();
        void showMeals(List<MealShort> meals);
        void showError(String message);
    }

    interface Presenter {
        void loadMealsByIngredient(String ingredient);
        void loadMealsByArea(String area);
        void loadMealsByCategory(String category);
        void searchMeals(String query);
        void detachView(); // for cleanup
    }
}