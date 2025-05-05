package com.example.yummy.model.network.meal;

public interface MealNetWorkCallBack {

    void onDaySuccessResult(MealResponse mealResponse);
    void onDayFailureResult(String error);


    void onNameSuccessResult(MealResponse mealResponse);
    void onNameFailureResult(String error);


    void onIDSuccessResult(MealResponse mealResponse);
    void onIDFailureResult(String error);


    void onLetterSuccessResult(MealResponse mealResponse);
    void onLetterFailureResult(String error);



}
