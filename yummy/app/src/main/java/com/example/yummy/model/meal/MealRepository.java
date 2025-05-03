package com.example.yummy.model.meal;

import androidx.lifecycle.LiveData;

import com.example.yummy.model.db.MealPlan;
import com.example.yummy.model.network.meal.MealNetWorkCallBack;

import java.util.List;

public interface MealRepository {


    public void getRandom(MealNetWorkCallBack callBack);

    public void getMealOfTheDay(MealNetWorkCallBack cb);

    public void getByName(String name , MealNetWorkCallBack callBack);

    public void getByID(String ID , MealNetWorkCallBack callBack);

    public void getByFirstLetter(String letter , MealNetWorkCallBack callBack);



    LiveData<List<Meal>> getAllMealsLocal();
    LiveData<List<Meal>> getMealsByNameLocal(String name);
    LiveData<Meal> getMealByIDLocal(String ID);
    void insertMealLocal(Meal meal);
    void deleteMealLocal(Meal meal);

    void addMealToPlan(Meal meal, long date);
    void removeMealFromPlan(Meal meal, long date);
    LiveData<List<Meal>> getMealsForDate(long date);


}
