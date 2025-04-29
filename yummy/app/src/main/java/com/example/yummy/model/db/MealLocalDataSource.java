package com.example.yummy.model.db;

import androidx.lifecycle.LiveData;

import com.example.yummy.model.meal.Meal;

import java.util.List;

public interface MealLocalDataSource {

    LiveData<List<Meal>> getAllMeals();

    LiveData<List<Meal>> getMealsByName(String name);

    LiveData<Meal> getMealByID(String iD);

    void insertMeal( Meal meal);

    void deleteMeal(Meal meal);
}
