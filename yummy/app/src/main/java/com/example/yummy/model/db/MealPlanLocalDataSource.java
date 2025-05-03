package com.example.yummy.model.db;

import androidx.lifecycle.LiveData;

import com.example.yummy.model.meal.Meal;

import java.util.List;

public interface MealPlanLocalDataSource {


    void addMealToPlan(String mealId, long date);


    void removeMealFromPlan(String mealId, long date);


    LiveData<List<Meal>> getPlannedMealsForDate(long date);

    void removeAllPlansForMeal(String mealId);

    int countPlansForMeal(String mealId);
}