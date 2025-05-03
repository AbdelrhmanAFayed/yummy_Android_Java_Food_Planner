package com.example.yummy.model.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.yummy.model.meal.Meal;

import java.util.List;

public class MealPlanLocalDataSourceImp implements MealPlanLocalDataSource {
    private final MealPlanDao planDao;

    private static MealPlanLocalDataSourceImp instance;

    private MealPlanLocalDataSourceImp(Context context) {
        AppDataBase db = AppDataBase.getInstance(context);
        this.planDao = db.getMealPlanDao();
    }

    public static synchronized MealPlanLocalDataSourceImp getInstance(Context context) {
        if (instance == null) {
            instance = new MealPlanLocalDataSourceImp(context);
        }
        return instance;
    }

    @Override
    public void addMealToPlan(String mealId, long date) {
        new Thread(() -> planDao.addPlan(new MealPlan(mealId, date))).start();
    }

    @Override
    public void removeMealFromPlan(String mealId, long date) {
        new Thread(() -> planDao.removePlan(mealId, date)).start();
    }

    @Override
    public LiveData<List<Meal>> getPlannedMealsForDate(long date) {
        return planDao.getMealsForDate(date);
    }

    @Override
    public void removeAllPlansForMeal(String mealId) {
        planDao.removeAllPlansForMeal(mealId);
    }

    @Override
    public int countPlansForMeal(String mealId) {
        return planDao.countPlansForMeal(mealId);
    }
}
