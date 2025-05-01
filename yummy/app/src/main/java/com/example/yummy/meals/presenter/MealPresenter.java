package com.example.yummy.meals.presenter;

import android.content.Context;
import android.widget.Toast;

import com.example.yummy.meals.MealContract;
import com.example.yummy.model.mealshort.MealShort;
import com.example.yummy.model.mealshort.MealShortRepository;
import com.example.yummy.model.network.mealshort.MealShortNetWorkCallBack;
import com.example.yummy.model.network.mealshort.MealShortResponse;

import java.util.List;

public class MealPresenter implements MealContract.Presenter , MealShortNetWorkCallBack {

    private MealContract.View view;
    private MealShortRepository repository;

    private enum CurrentLoadType {
        CATEGORY, INGREDIENT, AREA
    }


    private CurrentLoadType currentLoadType;

    public MealPresenter(MealContract.View view, MealShortRepository repository) {
        this.view = view;
        this.repository = repository;
    }


    @Override
    public void loadMealsByIngredient(String ingredient) {
        if (view != null) {
            currentLoadType = CurrentLoadType.INGREDIENT;
            repository.getByIng(ingredient,this);
        }
    }

    @Override
    public void loadMealsByArea(String area) {
        if (view != null) {
            currentLoadType = CurrentLoadType.AREA;
           repository.getByCountry(area,this);
        }
    }


    @Override
    public void loadMealsByCategory(String category) {
        if (view != null) {
            currentLoadType = CurrentLoadType.CATEGORY;
            repository.getByCat(category,this);
        }
    }

    private void handleSuccess(MealShortResponse response) {
        if (view != null) {
            List<MealShort> meals = response.getMealShorts();
            if (meals != null ) {
                view.showMeals(meals);
            } else {
                view.showError("No meals found.");
            }
        }
    }

    private void handleFailure(String error) {
        if (view != null) {
            view.showError(error != null ? error : "An unknown error occurred.");
        }
    }

    @Override
    public void onCatSuccessResult(MealShortResponse mealShortResponse) {
        handleSuccess(mealShortResponse);

    }

    @Override
    public void onCatFailureResult(String error) {
        handleFailure(error);

    }

    @Override
    public void onIngSuccessResult(MealShortResponse mealShortResponse) {
        handleSuccess(mealShortResponse);

    }

    @Override
    public void onIngFailureResult(String error) {
        handleFailure(error);

    }

    @Override
    public void onAreaSuccessResult(MealShortResponse mealShortResponse) {
        handleSuccess(mealShortResponse);

    }

    @Override
    public void onAreaFailureResult(String error) {
        handleFailure(error);

    }
}
