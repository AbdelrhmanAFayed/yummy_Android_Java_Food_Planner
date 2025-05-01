package com.example.yummy.main;

import com.example.yummy.model.area.Area;
import com.example.yummy.model.category.Category;
import com.example.yummy.model.ingredient.Ingredient;
import com.example.yummy.model.meal.Meal;
import com.example.yummy.model.network.area.AreaResponse;
import com.example.yummy.model.network.category.CategoryResponse;
import com.example.yummy.model.network.ingredient.IngredientResponse;
import com.example.yummy.model.network.mealshort.MealShortResponse;

import java.util.List;

public interface MainContract {




    interface HomeView {
        void showRandomMeal(Meal meal);
        void showIngredients(IngredientResponse ingredientResponse);
        void showCategories(CategoryResponse categoryResponse);
        void showHomeError(String message);
        void openMealDetails(Meal meal);
        void saveMeal(Meal meal);
        Meal loadMeal();

        void showCountries(AreaResponse areaResponse);
        }

    interface HomePresenter {
        void getRandomMeal();
        void getIngredients();
        void getCategories();
        void saveMealToPreferences(Meal meal);
        Meal loadMealFromPreferences();
        void onMealImageClicked();
        public void getCountries();
        public void getMealsByCountry(String country);
    }

    interface SearchView {
        void showMealSearchResults(List<Meal> meals);
        void showCountrySearchResults(List<Area> filteredAreas);
        void showCategorySearchResults(List<Category> filteredCategories);
        void showIngredientSearchResults(List<Ingredient> filteredIngredients);
        void showSearchError(String message);
    }

    interface SearchPresenter {
        void searchMealsByName(String query);
        void searchCountries(String query);
        void searchCategories(String query);
        void searchIngredients(String query);
    }



}
