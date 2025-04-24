package com.example.yummy.model.ingredient;

import com.example.yummy.model.network.ingredient.IngNetWorkCallBack;

public interface IngredientRepository {


    public void getIngredients(IngNetWorkCallBack callBack);
}
