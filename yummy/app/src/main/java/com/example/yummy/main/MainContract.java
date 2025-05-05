package com.example.yummy.main;

import com.example.yummy.model.area.Area;
import com.example.yummy.model.category.Category;
import com.example.yummy.model.ingredient.Ingredient;
import com.example.yummy.model.meal.Meal;
import com.example.yummy.model.network.area.AreaResponse;
import com.example.yummy.model.network.category.CategoryResponse;
import com.example.yummy.model.network.ingredient.IngredientResponse;

import java.util.Date;
import java.util.List;

public interface MainContract {

    interface HomeView {
        void showRandomMeal(Meal meal);
        void showIngredients(IngredientResponse ingredientResponse);
        void showCategories(CategoryResponse categoryResponse);
        void showHomeError(String message);
        void openMealDetails(Meal meal);
        void saveMeal(Meal meal);
        void showErrorPopupWithNavigation();
        void showCountries(AreaResponse areaResponse);
        }

    interface HomePresenter {
        void getMealOfDay();
        void getRandomMeal();
        void getIngredients();
        void getCategories();
        void saveMealToPreferences(Meal meal);
        void onMealImageClicked();
        void getCountries();
        void getMealsByCountry(String country);
        void clearFlag();
    }

    interface SearchView {
        void showMealSearchResults(List<Meal> meals);
        void showCountrySearchResults(List<Area> filteredAreas);
        void showCategorySearchResults(List<Category> filteredCategories);
        void showIngredientSearchResults(List<Ingredient> filteredIngredients);
        void showSearchError(String message);
        void showErrorPopupWithNavigation();
    }

    interface SearchPresenter {
        void searchMealsByName(String query);
        void searchCountries(String query);
        void searchCategories(String query);
        void searchIngredients(String query);
        void clearFlag();

    }

    interface FavoritesView {
        void showFavorites(List<Meal> favorites);
        void showFavoritesError(String message);
    }

    interface FavoritesPresenter {
        void loadAllFavorites();
        void removeFavorite(Meal meal);
     }

    interface CalendarView {
        void showMealsForDay(List<Meal> meals);
        void showCalendarError(String message);
    }

    interface CalendarPresenter {
        void loadMealsForDay(Date date);
        void removeMealFromDay(Meal meal , long day);
    }

}
