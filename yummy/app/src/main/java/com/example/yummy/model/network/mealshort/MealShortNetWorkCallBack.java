package com.example.yummy.model.network.mealshort;

public interface MealShortNetWorkCallBack {

    public void onCatSuccessResult(MealShortResponse mealShortResponse);
    public void onCatFailureResult(String error);

    public void onIngSuccessResult(MealShortResponse mealShortResponse);
    public void onIngFailureResult(String error);


    public void onAreaSuccessResult(MealShortResponse mealShortResponse);
    public void onAreaFailureResult(String error);


}
