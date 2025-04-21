package com.example.yummy.model.network.meal;

public interface MealNetWorkCallBack {

    public void onSuccessResult(MealResponse mealResponse);
    public void onFailureResult(String error);

}
