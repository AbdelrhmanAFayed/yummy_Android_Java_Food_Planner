package com.example.yummy.model.meal;

import androidx.lifecycle.LiveData;

import com.example.yummy.model.db.MealPlan;
import com.example.yummy.model.network.meal.MealNetWorkCallBack;

import java.util.List;

public interface MealRepository {


    void getRandom(MealNetWorkCallBack callBack);

    void getMealOfTheDay(MealNetWorkCallBack cb);

    void getByName(String name , MealNetWorkCallBack callBack);

    void getByID(String ID , MealNetWorkCallBack callBack);

    void getByFirstLetter(String letter , MealNetWorkCallBack callBack);



    LiveData<List<Meal>> getAllMealsLocal();
    LiveData<List<Meal>> getMealsByNameLocal(String name);
    LiveData<Meal> getMealByIDLocal(String ID);
    void insertMealLocal(Meal meal);
    void deleteMealLocal(Meal meal);

    void addMealToPlan(Meal meal, long date);
    void removeMealFromPlan(Meal meal, long date);
    LiveData<List<Meal>> getMealsForDate(long date);

    void restoreMealsFromFirebase();


}
