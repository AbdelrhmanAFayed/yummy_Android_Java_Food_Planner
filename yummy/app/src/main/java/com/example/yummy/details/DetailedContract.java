package com.example.yummy.details;

import com.example.yummy.model.meal.Meal;

public interface DetailedContract {

    interface View {
        void showMealDetails(Meal meal);
        void showError(String message);
        void updateFavoriteState(boolean isFav);
    }

    interface Presenter {
        void getMealDetails(String mealId);
        void addToFavorites(Meal meal);
        void removeFromFavorites(Meal meal);
        void checkIfFavorite(String mealId);
        void loadMealFromIntent(String mealJson);
    }
}
