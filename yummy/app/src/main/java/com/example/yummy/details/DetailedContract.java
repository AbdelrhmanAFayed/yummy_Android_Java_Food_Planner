package com.example.yummy.details;

import com.example.yummy.model.meal.Meal;

import java.util.Date;

public interface DetailedContract {

    interface View {
        void showMealDetails(Meal meal);
        void showError(String message);
        void updateFavoriteState(boolean isFav);
        void showPlanSuccess(Date date);
        void showPlanError(String message);
    }

    interface Presenter {
        void getMealDetails(String mealId);
        void addToFavorites(Meal meal);
        void removeFromFavorites(Meal meal);
        void checkIfFavorite(String mealId);
        void loadMealFromIntent(String mealJson);
        void planMeal(Meal meal, Date date);
    }
}
