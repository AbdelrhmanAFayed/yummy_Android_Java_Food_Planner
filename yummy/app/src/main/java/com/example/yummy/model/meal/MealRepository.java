package com.example.yummy.model.meal;

import androidx.lifecycle.LiveData;

import com.example.yummy.model.network.meal.MealNetWorkCallBack;

import java.util.List;

public interface MealRepository {


    public void getRandom(MealNetWorkCallBack callBack);

    public void getByName(String name , MealNetWorkCallBack callBack);

    public void getByID(String ID , MealNetWorkCallBack callBack);

    public void getByFirstLetter(String letter , MealNetWorkCallBack callBack);



    LiveData<List<Meal>> getAllMealsLocal();
    LiveData<List<Meal>> getMealsByNameLocal(String name);
    Meal getMealByIDLocal(String ID);
    void insertMealLocal(Meal meal);
    void deleteMealLocal(Meal meal);


}
