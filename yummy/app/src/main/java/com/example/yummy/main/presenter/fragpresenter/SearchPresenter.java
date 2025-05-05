package com.example.yummy.main.presenter.fragpresenter;

import com.example.yummy.main.MainContract;
import com.example.yummy.model.area.Area;
import com.example.yummy.model.area.AreaRepository;
import com.example.yummy.model.category.Category;
import com.example.yummy.model.category.CategoryRepository;
import com.example.yummy.model.ingredient.Ingredient;
import com.example.yummy.model.ingredient.IngredientRepository;
import com.example.yummy.model.meal.MealRepository;
import com.example.yummy.model.network.area.AreaNetWorkCallBack;
import com.example.yummy.model.network.area.AreaResponse;
import com.example.yummy.model.network.category.CatNetWorkCallBack;
import com.example.yummy.model.network.category.CategoryResponse;
import com.example.yummy.model.network.ingredient.IngNetWorkCallBack;
import com.example.yummy.model.network.ingredient.IngredientResponse;
import com.example.yummy.model.network.meal.MealNetWorkCallBack;
import com.example.yummy.model.network.meal.MealResponse;

import java.util.ArrayList;
import java.util.List;

public class SearchPresenter implements MainContract.SearchPresenter,
        AreaNetWorkCallBack, CatNetWorkCallBack, IngNetWorkCallBack, MealNetWorkCallBack {

    private boolean dialog = false ;


    private final MainContract.SearchView view;
    private final MealRepository mealRepo;
    private final AreaRepository areaRepo;
    private final CategoryRepository categoryRepo;
    private final IngredientRepository ingredientRepo;

    private String currentQuery = "";
    private final List<Area> areas = new ArrayList<>();
    private final List<Category> categories = new ArrayList<>();
    private final List<Ingredient> ingredients = new ArrayList<>();

    public SearchPresenter(MainContract.SearchView view,
                           MealRepository mealRepo,
                           AreaRepository areaRepo,
                           CategoryRepository categoryRepo,
                           IngredientRepository ingredientRepo) {
        this.view = view;
        this.mealRepo = mealRepo;
        this.areaRepo = areaRepo;
        this.categoryRepo = categoryRepo;
        this.ingredientRepo = ingredientRepo;
    }

    @Override
    public void searchMealsByName(String query) {
        currentQuery = query;
        mealRepo.getByName(query, this);
    }

    @Override
    public void searchCountries(String query) {
        currentQuery = query;
        if (areas.isEmpty()) {
            areaRepo.getAllAreas(this);
        } else {
            filterCountries();
        }
    }

    @Override
    public void searchCategories(String query) {
        currentQuery = query;
        if (categories.isEmpty()) {
            categoryRepo.getAll(this);
        } else {
            filterCategories();
        }
    }

    @Override
    public void searchIngredients(String query) {
        currentQuery = query;
        if (ingredients.isEmpty()) {
            ingredientRepo.getIngredients(this);
        } else {
            filterIngredients();
        }
    }

    @Override
    public void clearFlag() {
        dialog = false ;
    }

    private void filterCountries() {
        List<Area> filtered = new ArrayList<>();
        for (Area area : areas) {
            if (area.getStrArea().toLowerCase().contains(currentQuery.toLowerCase())) {
                filtered.add(area);
            }
        }
        view.showCountrySearchResults(filtered);
    }

    private void filterCategories() {
        List<Category> filtered = new ArrayList<>();
        for (Category cat : categories) {
            if (cat.getStrCategory().toLowerCase().contains(currentQuery.toLowerCase())) {
                filtered.add(cat);
            }
        }
        view.showCategorySearchResults(filtered);
    }

    private void filterIngredients() {
        List<Ingredient> filtered = new ArrayList<>();
        for (Ingredient ing : ingredients) {
            if (ing.getStrIngredient().toLowerCase().contains(currentQuery.toLowerCase())) {
                filtered.add(ing);
            }
        }
        view.showIngredientSearchResults(filtered);
    }

    @Override
    public void onSuccessResult(AreaResponse response) {
        areas.clear();
        areas.addAll(response.getAreas());
        filterCountries();
    }

    @Override
    public void onSuccessResult(CategoryResponse response) {
        categories.clear();
        categories.addAll(response.getCategories());
        filterCategories();
    }

    @Override
    public void onSuccessResult(IngredientResponse response) {
        ingredients.clear();
        ingredients.addAll(response.getIngredients());
        filterIngredients();
    }

    @Override
    public void onFailureResult(String error) {
        if (!dialog) {
            view.showErrorPopupWithNavigation();
            dialog = true ;
        }    }

    @Override
    public void onDaySuccessResult(MealResponse response) {
    }

    @Override
    public void onNameSuccessResult(MealResponse response) {
        view.showMealSearchResults(response.getMeals());
    }

    @Override
    public void onLetterSuccessResult(MealResponse response) {
    }

    @Override
    public void onIDSuccessResult(MealResponse response) {
    }

    @Override
    public void onDayFailureResult(String error) {
        if (!dialog) {
            view.showErrorPopupWithNavigation();
            dialog = true ;
        }    }

    @Override
    public void onNameFailureResult(String error) {
        if (!dialog) {
            view.showErrorPopupWithNavigation();
            dialog = true ;
        }    }

    @Override
    public void onLetterFailureResult(String error) {
        if (!dialog) {
            view.showErrorPopupWithNavigation();
            dialog = true ;
        }    }

    @Override
    public void onIDFailureResult(String error) {
        if (!dialog) {
            view.showErrorPopupWithNavigation();
            dialog = true ;
        }    }
}