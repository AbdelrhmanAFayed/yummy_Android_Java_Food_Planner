package com.example.yummy.model.network.ingredient;

public interface IngNetWorkCallBack {

    void onSuccessResult(IngredientResponse ingredientResponse);
    void onFailureResult(String error);

}
