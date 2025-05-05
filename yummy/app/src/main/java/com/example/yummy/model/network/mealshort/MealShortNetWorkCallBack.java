package com.example.yummy.model.network.mealshort;

public interface MealShortNetWorkCallBack {

    void onCatSuccessResult(MealShortResponse mealShortResponse);
    void onCatFailureResult(String error);

    void onIngSuccessResult(MealShortResponse mealShortResponse);
    void onIngFailureResult(String error);


    void onAreaSuccessResult(MealShortResponse mealShortResponse);
    void onAreaFailureResult(String error);


}
