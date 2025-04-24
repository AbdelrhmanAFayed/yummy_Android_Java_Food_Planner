package com.example.yummy.model.meal;

import com.example.yummy.model.network.meal.MealNetWorkCallBack;

public interface MealRepository {


    public void getRandom(MealNetWorkCallBack callBack);

    public void getByName(String name , MealNetWorkCallBack callBack);

    public void getByID(String ID , MealNetWorkCallBack callBack);

    public void getByFirstLetter(String letter , MealNetWorkCallBack callBack);
}
