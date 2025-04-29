package com.example.yummy.details.presenter;

import com.example.yummy.details.DetailedContract;
import com.example.yummy.model.meal.Meal;
import com.example.yummy.model.meal.MealRepository;
import com.example.yummy.model.network.meal.MealNetWorkCallBack;
import com.example.yummy.model.network.meal.MealResponse;
import com.google.gson.Gson;

public class DetailedPresenter implements DetailedContract.Presenter , MealNetWorkCallBack {

    private final DetailedContract.View view;
    private final MealRepository repository;

    public DetailedPresenter(DetailedContract.View view, MealRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void getMealDetails(String mealId) {
        repository.getByID(mealId,this);
    }

    @Override
    public void addToFavorites(Meal meal) {
        repository.insertMealLocal(meal);
        view.updateFavoriteState(true);
    }

    @Override
    public void removeFromFavorites(Meal meal) {
        repository.deleteMealLocal(meal);
        view.updateFavoriteState(false);
    }

    @Override
    public void checkIfFavorite(String mealId) {
        Meal localMeal = repository.getMealByIDLocal(mealId);
        boolean isFavorite = localMeal != null;
        view.updateFavoriteState(isFavorite);
    }

    @Override
    public void loadMealFromIntent(String mealJson) {
        Meal meal = new Gson().fromJson(mealJson, Meal.class);
        view.showMealDetails(meal);
        checkIfFavorite(meal.getIdMeal());

    }

    @Override
    public void onDaySuccessResult(MealResponse mealResponse) {

    }

    @Override
    public void onDayFailureResult(String error) {

    }

    @Override
    public void onNameSuccessResult(MealResponse mealResponse) {

    }

    @Override
    public void onNameFailureResult(String error) {

    }

    @Override
    public void onIDSuccessResult(MealResponse mealResponse) {
        view.showMealDetails(mealResponse.meals.get(0));

    }

    @Override
    public void onIDFailureResult(String error) {
        view.showError(error);

    }

    @Override
    public void onLetterSuccessResult(MealResponse mealResponse) {

    }

    @Override
    public void onLetterFailureResult(String error) {

    }
}
