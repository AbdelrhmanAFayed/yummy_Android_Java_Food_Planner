package com.example.yummy.model.network.ingredient;

public interface IngNetWorkCallBack {

    public void onSuccessResult(IngredientResponse ingredientResponse);
    public void onFailureResult(String error);

}
