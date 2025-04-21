package com.example.yummy.model.network.mealshort;

import com.example.yummy.model.MealShort;

public interface MealShortNetWorkCallBack {

    public void onSuccessResult(MealShortResponse mealShortResponse);
    public void onFailureResult(String error);

}
