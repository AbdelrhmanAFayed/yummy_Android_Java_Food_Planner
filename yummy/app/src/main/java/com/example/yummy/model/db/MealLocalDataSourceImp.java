package com.example.yummy.model.db;

import androidx.lifecycle.LiveData;

import com.example.yummy.model.meal.Meal;

import java.util.List;

public class MealLocalDataSourceImp implements MealLocalDataSource{

    private final MealDAO mealDAO;

    public MealLocalDataSourceImp(MealDAO mealDAO) {
        this.mealDAO = mealDAO;
    }



    @Override
    public LiveData<List<Meal>> getAllMeals() {
        return mealDAO.getAllMeals();
    }

    @Override
    public LiveData<List<Meal>> getMealsByName(String name) {
        return mealDAO.getMealsByName(name);    }

    @Override
    public Meal getMealByID(String iD) {
        return mealDAO.getMealById(iD);    }

    @Override
    public void insertMeal(Meal meal) {
        new Thread() {
            @Override
            public void run() {
                mealDAO.insert(meal);
            }
        }.start();

    }

    @Override
    public void deleteMeal(Meal meal) {
        new Thread() {
            @Override
            public void run() {
                mealDAO.delete(meal);
            }
        }.start();

    }
}
