package com.example.yummy.model.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.yummy.model.meal.Meal;

import java.util.List;

public class MealLocalDataSourceImp implements MealLocalDataSource{

    private final MealDAO mealDAO;

    private static MealLocalDataSourceImp localDataSourceImp = null ;

    private MealLocalDataSourceImp(Context context) {
        this.mealDAO = AppDataBase.getInstance(context).getMealDao();
    }

    public static MealLocalDataSourceImp getInstance(Context context) {
        if(localDataSourceImp == null)
        {
            localDataSourceImp = new MealLocalDataSourceImp(context);
        }
        return localDataSourceImp;
    }



    @Override
    public LiveData<List<Meal>> getAllMeals() {
        return mealDAO.getAllMeals();
    }

    @Override
    public LiveData<List<Meal>> getMealsByName(String name) {
        return mealDAO.getMealsByName(name);    }

    @Override
    public LiveData<Meal> getMealByID(String iD) {
        return mealDAO.getMealById(iD);    }

    @Override
    public void insertMeal(Meal meal) {
        new Thread(() -> mealDAO.insert(meal)).start();

    }

    @Override
    public void deleteMeal(Meal meal) {
        new Thread(() -> mealDAO.delete(meal)).start();

    }
}
