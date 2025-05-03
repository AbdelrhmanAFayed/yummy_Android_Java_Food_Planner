package com.example.yummy.details.presenter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.yummy.details.DetailedContract;
import com.example.yummy.model.meal.Meal;
import com.example.yummy.model.meal.MealRepository;
import com.example.yummy.model.network.meal.MealNetWorkCallBack;
import com.example.yummy.model.network.meal.MealResponse;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;

public class DetailedPresenter implements DetailedContract.Presenter , MealNetWorkCallBack {

    private final DetailedContract.View view;
    private final MealRepository repository;

    public DetailedPresenter(DetailedContract.View view, MealRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void getMealDetails(String mealId) {
        LiveData<Meal> localMeal = repository.getMealByIDLocal(mealId);

        localMeal.observeForever(new Observer<Meal>() {
            @Override
            public void onChanged(Meal meal) {
                localMeal.removeObserver(this);
                if (meal != null) {
                    view.showMealDetails(meal);
                    checkIfFavorite(mealId);
                } else {
                    repository.getByID(mealId, DetailedPresenter.this);
                }
            }
        });
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
        LiveData<Meal> localMealLiveData = repository.getMealByIDLocal(mealId);
        localMealLiveData.observeForever(new Observer<Meal>() {
            @Override
            public void onChanged(Meal meal) {
                boolean isFavorite = meal != null;
                view.updateFavoriteState(isFavorite);
            }
        });
    }

    @Override
    public void loadMealFromIntent(String mealJson) {
        Meal meal = new Gson().fromJson(mealJson, Meal.class);
        view.showMealDetails(meal);
       checkIfFavorite(meal.getIdMeal());

    }

    @Override
    public void planMeal(Meal meal, Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE,      0);
        cal.set(Calendar.SECOND,      0);
        cal.set(Calendar.MILLISECOND, 0);
        long day = cal.getTimeInMillis();

        repository.addMealToPlan(meal ,day);
        view.showPlanSuccess(date);

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
