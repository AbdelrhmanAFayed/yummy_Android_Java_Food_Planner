package com.example.yummy.model.network.meal;

public interface MealNetWorkCallBack {

    public void onDaySuccessResult(MealResponse mealResponse);
    public void onDayFailureResult(String error);


    public void onNameSuccessResult(MealResponse mealResponse);
    public void onNameFailureResult(String error);


    public void onIDSuccessResult(MealResponse mealResponse);
    public void onIDFailureResult(String error);


    public void onLetterSuccessResult(MealResponse mealResponse);
    public void onLetterFailureResult(String error);



}
